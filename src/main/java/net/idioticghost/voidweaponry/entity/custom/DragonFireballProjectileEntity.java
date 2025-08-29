package net.idioticghost.voidweaponry.entity.custom;

import net.idioticghost.voidweaponry.entity.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class DragonFireballProjectileEntity extends AbstractHurtingProjectile {

    private float rotation;
    public boolean grounded = false;
    private LivingEntity stuckIn; // entity the knife is stuck in
    private boolean stuckInBlock;  // whether the knife is stuck in a block

    public DragonFireballProjectileEntity(EntityType<? extends DragonFireballProjectileEntity> type, Level level) {
        super(type, level);
    }

    public DragonFireballProjectileEntity(Level level, LivingEntity shooter) {
        this(ModEntities.DRAGON_FIREBALL.get(), level); // call main constructor
        this.setOwner(shooter);                          // set the shooter/owner
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
    }


    public float getRenderingRotation() {
        if (stuckIn != null || stuckInBlock) {
            return rotation;
        }

        rotation += 0.5f;
        if (rotation >= 360) rotation = 0;
        return rotation;
    }

    @Override
    public boolean isNoGravity() {
        return false; // ensure gravity is applied
    }

    @Override
    public boolean isOnFire() {
        return true; // prevents the fire effect
    }

    @Override
    public void tick() {
        super.tick();

        // Spin
        rotation += 0.5f;
        if (rotation >= 360) rotation = 0;

        // Flame particles
        if (level().isClientSide) {
            level().addParticle(ParticleTypes.FLAME, getX(), getY(), getZ(), 0, 0, 0);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (!level().isClientSide) {
            if (result.getType() == HitResult.Type.ENTITY) {
                System.out.println("Dragon fireball hit an entity!");
            } else if (result.getType() == HitResult.Type.BLOCK) {
                System.out.println("Dragon fireball hit a block!");
            }

            // remove after impact
            this.remove(RemovalReason.DISCARDED);
        }
    }
}