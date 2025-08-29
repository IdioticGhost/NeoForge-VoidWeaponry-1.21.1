package net.idioticghost.voidweaponry.entity.custom;

import net.idioticghost.voidweaponry.component.ModDataComponents;
import net.idioticghost.voidweaponry.entity.ModEntities;
import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;


public class WhiteholeKnifeProjectileEntity extends AbstractArrow {

    private float rotation;
    public Vec2 groundedOffset;
    private int pickupCooldown = 20;

    private LivingEntity stuckIn; // entity the knife is stuck in
    private boolean stuckInBlock;  // whether the knife is stuck in a block

    private boolean abilityActive = false;

    public void setAbilityActive(boolean active) {
        this.abilityActive = true;
    }

    public WhiteholeKnifeProjectileEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public WhiteholeKnifeProjectileEntity(LivingEntity shooter, Level level) {
        super(ModEntities.WHITEHOLE_KNIFE.get(), shooter, level, new ItemStack(ModItems.BLACKHOLE_KNIFE.get()), null);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
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
                stack.set(ModDataComponents.KNIFE_VERSION.get(), true);
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
            // Only the thrower can pick it up
            if (this.getOwner() instanceof Player owner && player.getUUID().equals(owner.getUUID())) {
                // Prevent immediate pickup right after throw
                if (pickupCooldown > 0) return;

                // Reset the boolean on the thrower's knife
                resetKnifeBoolean(player);

                // Optional: pickup sound
                this.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.5f, 1.0f);

                // Remove the entity
                this.discard();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

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