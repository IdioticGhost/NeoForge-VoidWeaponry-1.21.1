package net.idioticghost.voidweaponry.item.custom;

import net.idioticghost.voidweaponry.component.ModDataComponents;
import net.idioticghost.voidweaponry.effect.ModEffects;
import net.idioticghost.voidweaponry.entity.ModEntities;
import net.idioticghost.voidweaponry.entity.custom.DragonFireballProjectileEntity;
import net.idioticghost.voidweaponry.event.custom.DragonKatanaHeatHandler;
import net.idioticghost.voidweaponry.item.ModItems;
import net.idioticghost.voidweaponry.util.ItemComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class DragonKatanaItem extends SwordItem {
    public DragonKatanaItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext,
                                List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        // Static tooltip
        for (int i = 1; i <= 17; i++) {
            pTooltipComponents.add(Component.translatable(
                    "tooltip.voidweaponry.dragon_katana_" + i + ".tooltip"
            ));
        }

        int currentHeat = pStack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
        int maxHeat = 100;

        // Heat bar
        pTooltipComponents.add(ItemComponents.generateHeatBarLore(currentHeat, maxHeat));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.getMainHandItem().is(ModItems.DRAGON_KATANA.get())) {
            DragonKatanaHeatHandler.DragonKatanaStrengthHandler.updateHeatStrength(player, 0);
            DragonKatanaHeatHandler.DragonKatanaSpeedHandler.updateHeatSpeed(player, 0);
        }

        int currentHeat = stack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);

        // Only allow if heat >= 100
        if (currentHeat >= 100) {
            stack.set(ModDataComponents.KATANA_HEAT_BOOST_TIMER.get(), 15);
            player.getCooldowns().addCooldown(this, 120);
            stack.set(ModDataComponents.KATANA_HEAT_VALUE.get(), currentHeat - 100);

            // Increase heat cap
            stack.set(ModDataComponents.KATANA_HEAT_CAP.get(), 150);

            return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
        } else {
            if (!world.isClientSide()) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.5f, 1.0f);
                player.sendSystemMessage(Component.literal("Your Temperament is not high enough.").withStyle(ChatFormatting.RED));
            }
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        stack.set(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
        stack.set(ModDataComponents.KATANA_HEAT_CAP.get(), 150);
        return stack;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof Player player)) {
            return super.hurtEnemy(stack, target, attacker);
        }

        if (stack.is(ModItems.DRAGON_KATANA.get())) {
            int heatCap = stack.getOrDefault(ModDataComponents.KATANA_HEAT_CAP.get(), 110);
            int prevHeat = stack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);

            int heatGain = (heatCap == 150) ? 10 : 7;
            int newHeat = Math.min(heatCap, prevHeat + heatGain);
            updateHeat(player, stack, newHeat);

            if (newHeat >= 70) {
                applyOrStackEffect(target, ModEffects.DRAGONFIRE, 200);
            } else if (newHeat >= 40) {
                applyOrStackEffect(target, ModEffects.BURNING, 200);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    public static void updateHeat(Player player, ItemStack stack, int newHeat) {
        int prevHeat = stack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
        newHeat = Math.max(0, newHeat);

        stack.set(ModDataComponents.KATANA_HEAT_VALUE.get(), newHeat);

        int prevTier = getHeatTier(prevHeat);
        int newTier = getHeatTier(newHeat);

        if (newTier > prevTier) {
            playPowerUpSound(player, newHeat / 100f);
            if (newHeat >= 100) {
                player.sendSystemMessage(
                        Component.literal("The blade begins to burn")
                                .withStyle(style -> style.withColor(0xFFC0CB))
                );
            } else if (newHeat >= 70) {
                player.sendSystemMessage(
                        Component.literal("The blade grows hot")
                                .withStyle(style -> style.withColor(0x800080))
                );
            } else if (newHeat >= 40) {
                player.sendSystemMessage(
                        Component.literal("The blade grows warm")
                                .withStyle(style -> style.withColor(0xFFA500))
                );
            }
        }
    }

    private static void applyOrStackEffect(LivingEntity target, Holder<MobEffect> effect, int baseDuration) {
        int duration = baseDuration;
        int amplifier = 0;

        var current = target.getEffect(effect);
        if (current != null) {
            amplifier = Math.min(current.getAmplifier() + 1, 5);
            duration = Math.max(current.getDuration(), duration);
        }

        target.addEffect(new MobEffectInstance(effect, duration, amplifier, true, true, true));
    }

    private static void playPowerUpSound(Player player, float pitch) {
        if (player.level().isClientSide()) {
            player.playSound(SoundEvents.BLAZE_SHOOT, 2.0f, 0.75f);
            player.playSound(SoundEvents.BLAZE_AMBIENT, 2.0f, pitch);
        } else {
            player.level().playSound(null, player.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 2.0f, 0.75f);
            player.level().playSound(null, player.blockPosition(), SoundEvents.BLAZE_AMBIENT, SoundSource.PLAYERS, 2.0f, pitch);
        }
    }

    private static int getHeatTier(int heat) {
        if (heat >= 111) return 3;
        if (heat >= 70) return 2;
        if (heat >= 40) return 1;
        return 0;
    }
}