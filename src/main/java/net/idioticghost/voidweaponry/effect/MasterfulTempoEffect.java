package net.idioticghost.voidweaponry.effect;

import jdk.dynalink.Operation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MasterfulTempoEffect extends MobEffect {
    public MasterfulTempoEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
                this.addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                ResourceLocation.withDefaultNamespace("masterful_tempo"),
                2,
                AttributeModifier.Operation.ADD_VALUE
        );
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        return super.applyEffectTick(pLivingEntity, pAmplifier);
    }


    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
