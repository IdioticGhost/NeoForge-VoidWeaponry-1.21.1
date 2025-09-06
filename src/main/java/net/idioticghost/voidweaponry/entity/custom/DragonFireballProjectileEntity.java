package net.idioticghost.voidweaponry.entity.custom;

import net.idioticghost.voidweaponry.entity.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class DragonFireballProjectileEntity extends AbstractHurtingProjectile {

    private boolean stuckInBlock;
    private float rotation;
    public boolean grounded = false;


    public DragonFireballProjectileEntity(EntityType<? extends DragonFireballProjectileEntity> type, Level level) {
        super(type, level);
    }

    public DragonFireballProjectileEntity(Level level, LivingEntity shooter) {
        this(ModEntities.DRAGON_FIREBALL.get(), level);
        this.setOwner(shooter);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
    }

    @Override
    public boolean isNoGravity() {
        return false; // Gravity applies
    }

    public float getRenderingRotation() {
        if (stuckInBlock) {
            return rotation;
        }

        rotation += 0.5f;
        if (rotation >= 360) rotation = 0;
        return rotation;
    }

    @Override
    public boolean isOnFire() {
        return true; // Fire effect
    }

    @Override
    public void tick() {
        super.tick();

        // Spin the fireball
        rotation += 0.5f;
        if (rotation >= 360) rotation = 0;

        // Optional: visual flames
        if (level().isClientSide) {
            level().addParticle(ParticleTypes.FLAME, getX(), getY(), getZ(), 0, 0, 0);
        }
    }

    @Override
    protected void onHitEntity(net.minecraft.world.phys.EntityHitResult entityResult) {
        // Do nothing — fireball passes through entities
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // Prevent projectile from taking damage
        return false;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (level().isClientSide) return;

        if (result.getType() == HitResult.Type.BLOCK) {

            // Explosion visual effect (no terrain damage)
            level().explode(this, getX(), getY(), getZ(), 7.0F, Level.ExplosionInteraction.NONE);

            // Explosion particles
            level().addParticle(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(), getZ(), 0, 0, 0);
            for (int i = 0; i < 100; i++) { // more particles for impact
                double dx = (random.nextDouble() - 0.5) * 7.0;
                double dy = random.nextDouble() * 3.0;
                double dz = (random.nextDouble() - 0.5) * 7.0;
                level().addParticle(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), dx, dy, dz);
            }

            // Damage nearby entities (linear falloff, bypass armor)
            double radius = 7.0D;
            float maxDamage = 14.0F; // 7 hearts
            float minDamage = 8.0F;  // 4 hearts

            for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(radius))) {
                if (entity == this.getOwner()) continue;

                double distance = entity.distanceToSqr(getX(), getY(), getZ());
                double radiusSqr = radius * radius;
                if (distance <= radiusSqr) {
                    double factor = 1.0 - (Math.sqrt(distance) / radius);
                    float damage = (float) (minDamage + factor * (maxDamage - minDamage));

                    // Apply damage that bypasses armor and effects
                    entity.hurt(damageSources().magic(), damage);


                    System.out.println("[DragonFireball] Dealt " + damage + " damage to " + entity.getName().getString());
                }
            }

            // Remove the fireball
            this.discard();
        }
    }
}