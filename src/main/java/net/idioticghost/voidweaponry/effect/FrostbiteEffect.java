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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Map;
import java.util.WeakHashMap;

@EventBusSubscriber(modid = VoidWeaponry.MOD_ID)
public class FrostbiteEffect extends MobEffect {

    public FrostbiteEffect() {
        super(MobEffectCategory.HARMFUL, 0x99DDEE); // icy blue color

        // Attribute modifier: movement speed reduction, amplified per stack
        this.addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "frostbite_move_speed"),
                -0.1, // 10% per stack, capped at 100%
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        // Ensure stacking is capped at 10
        int stacks = Math.min(amplifier + 1, 10);

        // Freeze entity if max stacks
        if (stacks >= 10) {
            Vec3 motion = entity.getDeltaMovement();
            entity.setDeltaMovement(0, motion.y, 0);
            entity.hasImpulse = false;
        }

        // Spawn particles on server, visible to all clients
        if (!entity.level().isClientSide) {
            if (entity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ModParticles.FROSTBITTEN_PARTICLES.get(),
                        entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(),
                        2,              // count
                        0.2, 0.2, 0.2,  // spread (x, y, z)
                        0.01            // speed
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

        MobEffectInstance frostbite = entity.getEffect(ModEffects.FROSTBITE);
        if (frostbite == null) return;

        int stacks = Math.min(frostbite.getAmplifier() + 1, 10);

        // Increase incoming damage by 2% per stack
        float newDamage = event.getAmount() * (1.0f + 0.02f * stacks);
        event.setAmount(newDamage);
    }
}