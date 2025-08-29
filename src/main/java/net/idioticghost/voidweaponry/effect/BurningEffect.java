package net.idioticghost.voidweaponry.effect;

import net.idioticghost.voidweaponry.particle.ModParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;


public class BurningEffect extends MobEffect {

    public BurningEffect() {
        super(MobEffectCategory.HARMFUL, 0x00FF4500);
    }

    @Override
    public boolean isBeneficial() {
        return false; // harmful effect
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        int stacks = Math.min(amplifier + 1, 5);

        if (!entity.level().isClientSide) {
            float percentDamage = entity.getMaxHealth() * (0.004f * stacks);
            float finalDamage = Math.max(0.1f * stacks, percentDamage);
            entity.hurt(entity.damageSources().magic(), finalDamage);

            if (entity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ModParticles.BURNING_PARTICLES.get(),
                        entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(),
                        3,            // count
                        0.2, 0.2, 0.2, // spread (x, y, z)
                        0.01           // speed
                );
            }
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}