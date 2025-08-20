package net.idioticghost.voidweaponry.effect;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.item.custom.TempoItem;
import net.idioticghost.voidweaponry.particle.ModParticles;
import net.idioticghost.voidweaponry.particle.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Random;


@EventBusSubscriber(modid = VoidWeaponry.MOD_ID)
public class ParryEffect extends MobEffect {

    private static final Random RANDOM = new Random();

    public ParryEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xE9B115); // gold color
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) return;
        if (!(entity instanceof Player player)) return;
        if (!entity.hasEffect(ModEffects.PARRY)) return;

        DamageSource source = event.getSource();
        boolean validSource = false;

        if (source.getEntity() instanceof Player) validSource = true;
        else if (source.getEntity() instanceof LivingEntity) validSource = true;
        else if (source.getMsgId().equals("arrow") || source.getMsgId().equals("trident") || source.getMsgId().equals("fireball") || source.getMsgId().equals("thrown")) {
            validSource = true;
        }

        if (!validSource) return;

        float damage = event.getAmount();
        if (damage > 0) {
            event.setCanceled(true);

            int duration = 90;
            int currentAmplifier = 0;

            MobEffectInstance existing = player.getEffect(ModEffects.TEMPO);
            if (existing != null) currentAmplifier = existing.getAmplifier() + 1;

            int newAmplifier = Math.min(currentAmplifier, 9); // 0-based, 9 + 1 = 10 max stacks
            player.addEffect(new MobEffectInstance(ModEffects.TEMPO, duration, newAmplifier,false, true, true));

            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof TempoItem) {
                player.getCooldowns().removeCooldown(stack.getItem());
            }


            float pitch = 1.6f + RANDOM.nextFloat() * 0.4f;

            player.level().playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.ANVIL_LAND,
                    SoundSource.PLAYERS,
                    1.0f,
                    pitch
            );
            player.displayClientMessage(
                    Component.literal("Successful parry! Cooldown refunded."), true
            );
            ParticleUtils.spawnDiagonalLine(player,
                    ModParticles.RED_PARTICLES.get(),
                    25,
                    0.75,
                    0.5
            );
        }

        player.removeEffect(ModEffects.PARRY); // This might need rebalancing. Do we want ALL instances of damage in an instant blocked? or just one
    }
}