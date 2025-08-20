package net.idioticghost.voidweaponry.entity.custom;

import net.idioticghost.voidweaponry.effect.ModEffects;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MaelstromRingEntity extends Entity {

    private static final Random RANDOM = new Random();

    private float particleRotation = 0f;
    private double radius;
    private Player owner;

    // Track entities that should be hit this tick
    public final Set<LivingEntity> queuedHits = new HashSet<>();

    public MaelstromRingEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public MaelstromRingEntity(EntityType<?> type, Level level, Player owner, double radius) {
        this(type, level);
        this.owner = owner;
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        radius = tag.getDouble("Radius");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putDouble("Radius", radius);
    }

    @Override
    public void tick() {
        super.tick();
        Level world = getCommandSenderWorld();
        if (world.isClientSide) return;

        ServerLevel server = (ServerLevel) world;

        // Apply queued hits: mirrors the player's attack on all targets
        if (!queuedHits.isEmpty() && owner != null) {
            Set<LivingEntity> hitsThisTick = new HashSet<>(queuedHits);
            queuedHits.clear();

            for (LivingEntity target : hitsThisTick) {
                if (target.isAlive()) owner.attack(target);
            }
        }

        // Play final sound at end of lifespan
        if (tickCount == 415 && owner != null) {
            Level level = owner.level();
            level.playSound(
                    null,
                    owner.getX(),
                    owner.getY(),
                    owner.getZ(),
                    SoundEvents.LAVA_EXTINGUISH,
                    SoundSource.PLAYERS,
                    1.0f,
                    0.5f
            );
        }

        if (tickCount > 420) discard();

        // --- Spiral + cyclone effect ---
        double centerX = getX();
        double centerZ = getZ();
        double baseY = getY() + 0.2;

        int arms = 5;                // number of spiral arms
        double spiralSpeed = 0.25;   // rotation speed
        double ringLifetime = 420;   // total lifetime
        double ringCurrentRadius = getRadius(); // target radius

        // Spiral radius grows slowly, reaching ring radius at ring lifetime
        double spiralRadius = ringCurrentRadius * Math.min(1.0, tickCount / ringLifetime);

        for (int arm = 0; arm < arms; arm++) {
            // rotate whole spiral over time
            double rotationOffset = tickCount * 0.025; // whole spiral rotation
            double angle = (tickCount * spiralSpeed) + (arm * (2 * Math.PI / arms)) + rotationOffset;

            int particlesPerArm = 20;
            for (int i = 0; i < particlesPerArm; i++) {
                double factor = i / (double) particlesPerArm;
                double curvedFactor = factor * factor; // slight curvature
                double innerRadius = spiralRadius * factor;

                // base spiral position
                double x = centerX + innerRadius * Math.cos(angle + curvedFactor);
                double z = centerZ + innerRadius * Math.sin(angle + curvedFactor);

                // subtle z-offset for outer particles (adds depth)
                if (factor > 0.8) {
                    z += 0.05 * (factor - 0.8) / 0.2;
                }

                // gradient from center (bluish) to tip (white)
                float t = (float) factor;
                Vector3f color = new Vector3f(
                        0.3f + 0.7f * t,  // R
                        0.5f + 0.5f * t,  // G
                        1.0f               // B
                );

                server.sendParticles(
                        new DustParticleOptions(color, 0.65f),
                        x, baseY, z,
                        1, 0, 0, 0, 0
                );

                // occasional sharper streaks
                if (RANDOM.nextFloat() < 0.025F) {
                    double vx = -0.02 * Math.sin(angle);
                    double vz = 0.02 * Math.cos(angle);
                    server.sendParticles(
                            ParticleTypes.ENCHANT,
                            x, baseY, z,
                            1, vx * factor, 0, vz * factor, 0.0
                    );
                }
            }
        }
    }
}