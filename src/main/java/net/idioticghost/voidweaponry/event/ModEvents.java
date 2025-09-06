package net.idioticghost.voidweaponry.event;


import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.attribute.ModAttributes;
import net.idioticghost.voidweaponry.component.ModDataComponents;
import net.idioticghost.voidweaponry.effect.ModEffects;
import net.idioticghost.voidweaponry.event.custom.DragonKatanaHeatHandler;
import net.idioticghost.voidweaponry.item.ModItems;
import net.idioticghost.voidweaponry.item.ModToolTiers;
import net.idioticghost.voidweaponry.item.custom.MaelstromItem;
import net.idioticghost.voidweaponry.particle.ModParticles;
import net.idioticghost.voidweaponry.util.ItemComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = VoidWeaponry.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) return;

        ItemStack stack = player.getMainHandItem();
        Item item = stack.getItem();

        // VOID_MULTITOOL gives Haste
        if (item == ModItems.VOID_MULTITOOL.get()) {
            if (!player.hasEffect(MobEffects.DIG_SPEED)) {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 2, 0, true, false));
            }
        }
        // VOIDGOLD_SWORD gives Strength whgen in the void
        else if (item == ModItems.VOIDGOLD_SWORD.get()) {
            if (!player.hasEffect(MobEffects.DAMAGE_BOOST)) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2, 0, true, false));
            }
        }
        // Any non-sword VOIDGOLD tier tool gives Haste in the void
        else if (item instanceof TieredItem tiered && tiered.getTier() == ModToolTiers.VOIDGOLD) {
            if (!player.hasEffect(MobEffects.DIG_SPEED)) {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 2, 0, true, false));
            }
        }
        MaelstromItem.handleHoldParticles(player);
        DragonKatanaHeatHandler.tick(player);
        DragonKatanaHeatHandler.DragonKatanaStrengthHandler.updateHeatStrength(player, 0);
        DragonKatanaHeatHandler.DragonKatanaSpeedHandler.updateHeatSpeed(player, 0);
    }

    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event) {
        LivingEntity entity = event.getEntity();
        float original = event.getAmount();

        if (entity.hasEffect(ModEffects.BURNING)) {
            int amp = entity.getEffect(ModEffects.BURNING).getAmplifier();
            double cut = (amp + 1) * 0.02;
            float reduced = (float) (original * (1.0 - cut));
            event.setAmount(reduced);

            System.out.println("[VoidWeaponry] Burning healed "
                    + entity.getName().getString()
                    + ": " + original + " -> " + reduced
                    + " (" + (int) (cut * 100) + "% cut)");
        } else if (entity.hasEffect(ModEffects.DRAGONFIRE)) {
            int amp = entity.getEffect(ModEffects.DRAGONFIRE).getAmplifier();
            double cut = (amp + 1) * 0.04;
            float reduced = (float) (original * (1.0 - cut));
            event.setAmount(reduced);

            System.out.println("[VoidWeaponry] Dragonfire healed "
                    + entity.getName().getString()
                    + ": " + original + " -> " + reduced
                    + " (" + (int) (cut * 100) + "% cut)");
        }
    }
}