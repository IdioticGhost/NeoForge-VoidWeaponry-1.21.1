package net.idioticghost.voidweaponry.entity.custom;

import net.idioticghost.voidweaponry.component.ModDataComponents;
import net.idioticghost.voidweaponry.effect.ModEffects;
import net.idioticghost.voidweaponry.entity.ModEntities;
import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.joml.Vector3f;

import java.util.List;


public class BlackholeKnifeProjectileEntity extends AbstractArrow {

    private ItemStack originalStack;
    private float rotation;
    public Vec2 groundedOffset;
    private int pickupCooldown = 20;
    private boolean selfDestructActive = false;

    private LivingEntity stuckIn;
    private boolean stuckInBlock;

    public void setOriginalStack(ItemStack stack) {
        this.originalStack = stack;
    }
    public ItemStack getOriginalStack() {
        return this.originalStack;
    }

    public BlackholeKnifeProjectileEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public BlackholeKnifeProjectileEntity(LivingEntity shooter, Level level) {
        super(ModEntities.BLACKHOLE_KNIFE.get(), shooter, level, new ItemStack(ModItems.BLACKHOLE_KNIFE.get()), null);
    }

    private boolean abilityActive = false;

    public void setAbilityActive(boolean active) {
        this.abilityActive = active;
    }


    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.BLACKHOLE_KNIFE.get());
    }

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(ModItems.BLACKHOLE_KNIFE.get());
    }

    public boolean isGrounded() {
        return inGround || stuckInBlock;
    }

    // Hit a living entity → lodge
    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity living) {
            living.hurt(this.damageSources().thrown(this, this.getOwner()), 4);

            if (!this.level().isClientSide) {
                this.stuckIn = living;
                this.setDeltaMovement(0, 0, 0);
                this.noPhysics = true; // stop colliding with anything else
            }
        }
    }

    private void resetKnifeBoolean(Player player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.BLACKHOLE_KNIFE.get())) {
                stack.set(ModDataComponents.THREW_KNIFE.get(), false);
                stack.set(ModDataComponents.KNIFE_VERSION.get(), false);
                break;
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        stuckInBlock = true;
        this.setDeltaMovement(0, 0, 0);
        this.noPhysics = true;

        // Optional: set groundedOffset for rendering
        Direction dir = result.getDirection();
        if (dir == Direction.SOUTH) groundedOffset = new Vec2(20f,180f);
        else if (dir == Direction.NORTH) groundedOffset = new Vec2(20f,0f);
        else if (dir == Direction.EAST) groundedOffset = new Vec2(20f,-90f);
        else if (dir == Direction.WEST) groundedOffset = new Vec2(20f,90f);
        else if (dir == Direction.DOWN) groundedOffset = new Vec2(115f,180f);
        else if (dir == Direction.UP) groundedOffset = new Vec2(20f,180f);
    }

    @Override
    protected boolean tryPickup(Player player) {
        return false; // prevent vanilla pickup
    }

    public float getRenderingRotation() {
        // Only spin when flying freely
        if (stuckIn != null || stuckInBlock) {
            return rotation; // keep current rotation
        }

        rotation += 0.5f;
        if (rotation >= 360) rotation = 0;
        return rotation;
    }

    @Override
    public void playerTouch(Player player) {
        super.playerTouch(player);

        if (!this.level().isClientSide && this.isAlive()) {
            if (selfDestructActive) return;

            if (this.getOwner() instanceof Player owner && player.getUUID().equals(owner.getUUID())) {
                if (pickupCooldown > 0) return;

                ItemStack stack = getOriginalStack();
                if (stack != null) {
                    stack.set(ModDataComponents.THREW_KNIFE.get(), false);
                    stack.set(ModDataComponents.KNIFE_VERSION.get(), false);
                }

                this.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.5f, 1.0f);

                this.discard();
            }
        }
    }

    private int selfDestructTicks = -1;
    private Runnable onSelfDestruct = null;

    public void startSelfDestruct(int ticks, Runnable callback) {
        this.selfDestructTicks = ticks;
        this.onSelfDestruct = callback;
        this.selfDestructActive = true; // activate flag
    }

    @Override
    public void tick() {
        super.tick();

        if (pickupCooldown > 0) pickupCooldown--;

        if (abilityActive) {
            if (this.level() instanceof ServerLevel serverLevel) {
                Vec3 center = this.position();
                int particleCount = 65; // smoothness
                double radiusMax = 6.0;
                double swirlSpeed = this.tickCount * 0.2; // spin speed

                for (int i = 0; i < particleCount; i++) {
                    double t = (double)i / particleCount;

                    // Spiral radius grows inward → outward visually reversed
                    double radius = radiusMax * Math.pow(t, 1.2);

                    // First spiral
                    double angle1 = 2 * Math.PI * 2 * t + swirlSpeed;
                    double x1 = center.x + radius * Math.cos(angle1);
                    double z1 = center.z + radius * Math.sin(angle1);
                    double y1 = center.y + 0.05 * t;

                    float red1 = (float)(0 + 0.5 * t);
                    float green1 = 0f;
                    float blue1 = (float)(0 + 0.5 * t);
                    float scale = 1f;

                    serverLevel.sendParticles(
                            new DustParticleOptions(new Vector3f(red1, green1, blue1), scale),
                            x1, y1, z1,
                            1, 0, 0, 0, 0
                    );

                    // Second spiral (opposite side)
                    double angle2 = angle1 + Math.PI;
                    double x2 = center.x + radius * Math.cos(angle2);
                    double z2 = center.z + radius * Math.sin(angle2);
                    double y2 = center.y + 0.05 * t;

                    serverLevel.sendParticles(
                            new DustParticleOptions(new Vector3f(red1, green1, blue1), scale),
                            x2, y2, z2,
                            1, 0, 0, 0, 0
                    );
                }
            }

            double radius = 6.0D;
            Vec3 center = this.position();

            List<Entity> nearbyEntities = this.level().getEntities(this, this.getBoundingBox().inflate(radius));

            for (Entity target : nearbyEntities) {
                if (target instanceof LivingEntity living) {
                    // Optional: check if inside radius
                    double distance = center.distanceTo(target.position());
                    if (distance <= radius) {
                        living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0, false, false));
                        living.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 0, false, false));
                    }
                }
            }

            for (Entity target : nearbyEntities) {
                if (!(target instanceof LivingEntity || target instanceof Player)) continue;
                AABB box = target.getBoundingBox();
                double x = Mth.clamp(center.x, box.minX, box.maxX);
                double y = Mth.clamp(center.y, box.minY, box.maxY);
                double z = Mth.clamp(center.z, box.minZ, box.maxZ);
                Vec3 closestPoint = new Vec3(x, y, z);

                double distance = center.distanceTo(closestPoint);
                if (distance > radius) continue; // skip outside

                Vec3 direction = center.subtract(target.position());

                double factor = distance / radius;
                double curve = Math.pow(factor, 0.6);
                double strength = 0.03 + 0.06 * curve;

                if (target instanceof Player) {
                    strength *= 0.45;
                } else {
                    strength *= 1.8;
                }

                Vec3 pull = direction.normalize().scale(strength);
                target.setDeltaMovement(target.getDeltaMovement().add(pull));
                target.hurtMarked = true;
            }

// Particle effect (optional)
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 5; i++) {
                    double offsetX = (serverLevel.random.nextDouble() - 0.5) * 1.0;
                    double offsetY = (serverLevel.random.nextDouble() - 0.5) * 1.0;
                    double offsetZ = (serverLevel.random.nextDouble() - 0.5) * 1.0;

                    serverLevel.sendParticles(
                            new DustParticleOptions(new Vector3f(0f, 0f, 0f), 2f),
                            center.x + offsetX,
                            center.y + offsetY,
                            center.z + offsetZ,
                            1, 0, 0, 0, 0
                    );
                }
            }
        }

        if (selfDestructTicks > 0) {
            selfDestructTicks--;
            if (selfDestructTicks == 0) {
                if (onSelfDestruct != null) onSelfDestruct.run();
                this.setAbilityActive(false);
                this.discard();
            }
        }

        // Decrease pickup cooldown
        if (pickupCooldown > 0) pickupCooldown--;

        // --- Stuck in entity ---
        if (stuckIn != null) {
            if (!stuckIn.isAlive()) {
                // Reset player's THREW_KNIFE boolean
                Entity owner = this.getOwner();
                if (owner instanceof Player player) {
                    resetKnifeBoolean(player);
                }

                this.discard();
                return;
            }

            // Follow the entity
            this.setPos(
                    stuckIn.getX(),
                    stuckIn.getY() + stuckIn.getBbHeight() * 0.5,
                    stuckIn.getZ()
            );

            // Stop all motion
            this.setDeltaMovement(0, 0, 0);
            this.noPhysics = true;
            return; // skip the rest, knife is stuck
        }


        if (!this.level().isClientSide) {
            Entity owner = this.getOwner();
            if (owner instanceof Player player) {
                double distanceSq = this.distanceToSqr(owner); // squared distance
                if (distanceSq > 100 * 100) { // 100 blocks
                    resetKnifeBoolean(player);

                    // Optional: message
                    player.sendSystemMessage(Component.literal("Your knife has been recalled. [RANGE]").withStyle(ChatFormatting.RED));

                    // Remove the entity
                    this.discard();
                    return;
                }
            }
        }

        // --- Stuck in block ---
        if (stuckInBlock) {
            this.setDeltaMovement(0, 0, 0);
            this.noPhysics = true;
            return;
        }

        // --- Safety check: lava / void ---
        if (!this.level().isClientSide && (this.isInLava() || this.getY() < this.level().getMinBuildHeight())) {
            Entity owner = this.getOwner();
            if (owner instanceof Player player) {
                player.sendSystemMessage(Component.literal("Your knife has been recalled. [WORLD]").withStyle(ChatFormatting.RED));
                this.level().playSound(player, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.6f, 1.0f);

                // Reset THREW_KNIFE for held knife
                resetKnifeBoolean(player);
            }
            this.discard();
        }
    }

    @Override
    public boolean isOnFire() { return false; }
    @Override
    public boolean fireImmune() { return true; }
}