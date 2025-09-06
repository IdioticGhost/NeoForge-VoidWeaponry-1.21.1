package net.idioticghost.voidweaponry.effect;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.particle.ModParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DragonfireEffect extends MobEffect {

    public DragonfireEffect() {
        super(MobEffectCategory.HARMFUL, 0xAA00FF); // purple flame color
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        int stacks = Math.min(amplifier + 1, 5);

        if (!entity.level().isClientSide) {
            float percentDamage = entity.getMaxHealth() * (0.006f * stacks);
            float finalDamage = Math.max(0.15f * stacks, percentDamage);
            entity.hurt(entity.damageSources().magic(), finalDamage);

            if (entity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ModParticles.DRAGONFIRE_PARTICLES.get(),
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
        return duration % 20 == 0; // once per second
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        super.onEffectAdded(entity, amplifier);

        // Convert Burning → Dragonfire
        var burningEffect = entity.getEffect(ModEffects.BURNING);
        if (burningEffect != null) {
            int burningStacks = burningEffect.getAmplifier(); // 0-based
            int dragonfireStacks = Math.min(burningStacks, 4);

            int duration = burningEffect.getDuration();

            entity.removeEffect(ModEffects.BURNING);
            entity.addEffect(new MobEffectInstance(
                    ModEffects.DRAGONFIRE,
                    duration,
                    dragonfireStacks,
                    burningEffect.isAmbient(),
                    burningEffect.isVisible(),
                    burningEffect.showIcon()
            ));
        }
    }
}