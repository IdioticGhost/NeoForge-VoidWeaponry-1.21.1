package net.idioticghost.voidweaponry.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class VoidMiasmaEffect extends MobEffect {
    public VoidMiasmaEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

        @Override
        public boolean applyEffectTick (LivingEntity pLivingEntity,int pAmplifier) {


        if(!pLivingEntity.hasEffect(ModEffects.MIASMA_SHIELDED))
            if (pLivingEntity.hasEffect(ModEffects.MIASMA_RESISTANCE)) {
                if (pLivingEntity.hasEffect(ModEffects.VOID_MIASMA)) {
                    pLivingEntity.hurt(pLivingEntity.damageSources().magic(), 0.5F);
                }
            } else {
                pLivingEntity.hurt(pLivingEntity.damageSources().magic(), 1F);
            }
        return super.applyEffectTick(pLivingEntity, pAmplifier);
        }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
