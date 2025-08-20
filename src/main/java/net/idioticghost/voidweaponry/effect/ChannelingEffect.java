package net.idioticghost.voidweaponry.effect;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Map;
import java.util.WeakHashMap;

@EventBusSubscriber(modid = VoidWeaponry.MOD_ID)
public class ChannelingEffect extends MobEffect {

    private static final Map<LivingEntity, Integer> originalDurations = new WeakHashMap<>();
    private static final Map<LivingEntity, Integer> tickCounters = new WeakHashMap<>();

    public ChannelingEffect(MobEffectCategory category, int color) {
        super(category, color);

        this.addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                ResourceLocation.withDefaultNamespace("channeling_move_speed"),
                -1.0D, // -100% movement
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        super.onEffectStarted(entity, amplifier);

        MobEffectInstance inst = entity.getEffect(ModEffects.CHANNELING);
        if (inst != null) {
            originalDurations.put(entity, inst.getDuration());
        }

        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(0, motion.y, 0);
        entity.hasImpulse = false;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        Vec3 motion = entity.getDeltaMovement();
        double newY = Math.min(0, motion.y);
        entity.setDeltaMovement(0, newY, 0);
        entity.hasImpulse = false;

        MobEffectInstance inst = entity.getEffect(ModEffects.CHANNELING);
        if (inst != null && entity.level() instanceof Level level) {
            spawnProgressRing(level, entity, inst);
            playBassProgress(entity, inst);
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    private void spawnProgressRing(Level level, LivingEntity entity, MobEffectInstance inst) {
        Integer totalDuration = originalDurations.get(entity);
        if (totalDuration == null) return;

        double radius = 1.5;
        int totalPoints = 40;

        int elapsed = totalDuration - inst.getDuration();
        double progressFraction = Math.min(1.0, (double) elapsed / totalDuration);
        int pointsToShow = (int) (progressFraction * totalPoints);
        double angleStep = (2 * Math.PI) / totalPoints;

        for (int i = 0; i < pointsToShow; i++) {
            double angle = i * angleStep;
            double px = entity.getX() + radius * Math.cos(angle);
            double py = entity.getY() + 0.1;
            double pz = entity.getZ() + radius * Math.sin(angle);

            if (level.isClientSide) {
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, px, py, pz, 0, 0, 0);
            } else if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, px, py, pz, 1, 0, 0, 0, 0);
            }
        }
    }

    private void playBassProgress(LivingEntity entity, MobEffectInstance inst) {
        Integer totalDuration = originalDurations.get(entity);
        if (totalDuration == null || !(entity instanceof Player player)) return;

        // Calculate fraction and pitch every tick
        int elapsed = totalDuration - inst.getDuration();
        double fraction = Math.min(1.0, (double) elapsed / totalDuration);

        // Map fraction 0 → 1 to pitch 0.5 → 2
        float pitch = (float) (0.5 + fraction * 1.5); // 0.5 + (fraction * (2-0.5))

        // Update tick counter
        int tick = tickCounters.getOrDefault(entity, 0);
        tick++;
        tickCounters.put(entity, tick);

        // Play sound only every 3 ticks (or on last tick)
        if (tick % 3 == 0 || inst.getDuration() <= 1) {
            if (!entity.level().isClientSide) {
                player.level().playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.NOTE_BLOCK_BASS,
                        SoundSource.PLAYERS,
                        1.0f,
                        pitch
                );
            }
        }

        // Clean up when effect ends
        if (inst.getDuration() <= 1) {
            tickCounters.remove(entity);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) return;
        if (!entity.hasEffect(ModEffects.CHANNELING)) return;

        // Remove Channeling effect immediately
        entity.removeEffect(ModEffects.CHANNELING);
    }
}