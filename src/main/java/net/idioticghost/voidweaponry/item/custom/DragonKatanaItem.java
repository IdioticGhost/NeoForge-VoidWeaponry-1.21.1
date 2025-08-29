package net.idioticghost.voidweaponry.item.custom;

import net.idioticghost.voidweaponry.component.ModDataComponents;
import net.idioticghost.voidweaponry.effect.ModEffects;
import net.idioticghost.voidweaponry.entity.ModEntities;
import net.idioticghost.voidweaponry.entity.custom.DragonFireballProjectileEntity;
import net.idioticghost.voidweaponry.item.ModItems;
import net.idioticghost.voidweaponry.util.ItemComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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

        // Add static tooltip lines
        for (int i = 1; i <= 19; i++) {
            pTooltipComponents.add(Component.translatable(
                    "tooltip.voidweaponry.dragon_katana_" + i + ".tooltip"
            ));
        }

        // Example: get heat from your itemstack component
        int currentHeat = pStack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
        int maxHeat = 100; // or pull from config/constant if you have one

        // Add heat bar to tooltip
        pTooltipComponents.add(ItemComponents.generateHeatBar(currentHeat, maxHeat));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide()) {
            double reach = 50.0;

            Vec3 eyePos = player.getEyePosition(1.0F);
            Vec3 lookVec = player.getViewVector(1.0F);
            Vec3 targetVec = eyePos.add(lookVec.scale(reach));

            HitResult result = world.clip(new ClipContext(
                    eyePos,
                    targetVec,
                    ClipContext.Block.OUTLINE,
                    ClipContext.Fluid.NONE,
                    player
            ));

            if (result.getType() == HitResult.Type.BLOCK) {
                Vec3 vec = result.getLocation();
                BlockPos hitPos = new BlockPos((int) Math.floor(vec.x),
                        (int) Math.floor(vec.y),
                        (int) Math.floor(vec.z));

                // Spawn position 20 blocks above
                // Fireball spawn 200 blocks above target
                BlockPos spawnPos = hitPos.above(200);
                if (world instanceof ServerLevel serverWorld) {
                    DragonFireballProjectileEntity fireball = new DragonFireballProjectileEntity(serverWorld, player);
                    fireball.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0, 0);

                    // Set downward velocity to fall 200 blocks in 10 seconds
                    fireball.setDeltaMovement(0, -1.0, 0);
                    fireball.fallDistance = 0;

                    serverWorld.addFreshEntity(fireball);
                }
            }

            player.swing(hand, true);
        }

        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        stack.set(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
        return stack;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof Player player)) {
            return super.hurtEnemy(stack, target, attacker);
        }

        ItemStack held = attacker.getMainHandItem();
        if (held.is(ModItems.DRAGON_KATANA.get())) {
            // Store previous heat BEFORE incrementing
            int prevHeat = held.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);

            // Increase heat
            int heat = Math.min(110, prevHeat + 7); // increment heat
            held.set(ModDataComponents.KATANA_HEAT_VALUE.get(), heat);

            player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("Katana Heat: " + heat), true
            );

            int prevTier = getHeatTier(prevHeat);
            int newTier = getHeatTier(heat);

            if (newTier > prevTier) {
                // Powering up
                playPowerUpSound(player);
            } else if (newTier < prevTier) {
                // Powering down
                playPowerDownSound(player);
            }


            if (heat >= 70) {

                int duration = 200;
                int amplifier = 0;

                var current = target.getEffect(ModEffects.DRAGONFIRE);
                if (current != null) {
                    amplifier = Math.min(current.getAmplifier() + 1, 5); // cap stacks
                    duration = Math.max(current.getDuration(), duration);
                }

                target.addEffect(new MobEffectInstance(ModEffects.DRAGONFIRE, duration, amplifier, true, true, true));
            } else if (heat >= 40) {
                // Burning effect
                int duration = 200;
                int amplifier = 0;

                var current = target.getEffect(ModEffects.BURNING);
                if (current != null) {
                    amplifier = Math.min(current.getAmplifier() + 1, 5);
                    duration = Math.max(current.getDuration(), duration);
                }

                target.addEffect(new MobEffectInstance(ModEffects.BURNING, duration, amplifier, true, true, true));
            }
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    private void playPowerUpSound(Player player) {
        if (player.level().isClientSide()) {
            player.playSound(SoundEvents.BLAZE_DEATH, 2.0f, 0.75f);
            player.playSound(SoundEvents.ENDER_DRAGON_FLAP, 2.0f, 0.75f);
        } else {
            player.level().playSound(null, player.blockPosition(), SoundEvents.BLAZE_DEATH, SoundSource.PLAYERS, 2.0f, 0.75f);
            player.level().playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 2.0f, 0.75f);
        }
    }

    // Helper: play powering down sound
    private void playPowerDownSound(Player player) {
        if (player.level().isClientSide()) {
            player.playSound(SoundEvents.BLAZE_DEATH, 2.0f, 1.25f);
            player.playSound(SoundEvents.ENDER_DRAGON_FLAP, 2.0f, 1.25f);
        } else {
            player.level().playSound(null, player.blockPosition(), SoundEvents.BLAZE_DEATH, SoundSource.PLAYERS, 2.0f, 1.25f);
            player.level().playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 2.0f, 1.25f);
        }
    }

    private int getHeatTier(int heat) {
        if (heat >= 70) return 2;
        if (heat >= 40) return 1;
        return 0;
    }
}
