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
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = VoidWeaponry.MOD_ID)
public class ElectrocutionEffect extends MobEffect {

    public ElectrocutionEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFFF33); // icy blue color

        // Attribute modifier: movement speed reduction, amplified per stack
        this.addAttributeModifier(
                Attributes.ATTACK_SPEED,
                ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "electrocution_attack_speed"),
                -0.05, // 5% per stack, capped at 50%
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        this.addAttributeModifier(
                Attributes.MINING_EFFICIENCY,
                ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "electrocution_mining_speed"),
                -0.05, // 5% per stack, capped at 50%
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        this.addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "electrocution_attack_damage"),
                -0.025, // 2.5% per stack, capped at 25%
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );


    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        int stacks = Math.min(amplifier + 1, 10);

        if (!entity.level().isClientSide) {
            if (entity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ModParticles.ELECTROCUTED_PARTICLES.get(),
                        entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(),
                        2,              // count
                        0.2, 0.2, 0.2,  // spread (x, y, z)
                        0.02            // speed
                );
            }
        }

        return super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;

        MobEffectInstance electrocuted = entity.getEffect(ModEffects.ELECTROCUTION);
        if (electrocuted == null) return;

        int stacks = Math.min(electrocuted.getAmplifier() + 1, 10);

        // Increase incoming damage by 2% per stack
        float newDamage = event.getAmount() * (1.0f + 0.02f * stacks);
        event.setAmount(newDamage);
    }
}