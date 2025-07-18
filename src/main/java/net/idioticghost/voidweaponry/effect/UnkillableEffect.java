package net.idioticghost.voidweaponry.effect;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = VoidWeaponry.MOD_ID)
public class UnkillableEffect extends MobEffect {

    public UnkillableEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xE9B115);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) return;
        if (!entity.hasEffect(ModEffects.UNKILLABLE)) return;

        float maxHealth = entity.getMaxHealth();
        float currentHealth = entity.getHealth();
        float incomingDamage = event.getAmount();
        float resultingHealth = currentHealth - incomingDamage;
        float threshold = maxHealth * 0.10f;

        if (resultingHealth < threshold) {
            float safeDamage = Math.max(0, currentHealth - threshold);
            event.setAmount(safeDamage);
        }
    }
}