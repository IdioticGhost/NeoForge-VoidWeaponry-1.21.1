package net.idioticghost.voidweaponry.entity.custom;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.particle.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeathArrowEntity extends AbstractArrow {

    public DeathArrowEntity(EntityType<? extends DeathArrowEntity> type, Level level) {
        super(type, level);
    }

    public DeathArrowEntity(EntityType<? extends DeathArrowEntity> type, Level level, LivingEntity shooter) {
        super(type, level);
        this.setOwner(shooter);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public boolean isCritArrow() {
        // Disable crit particles client-side but keep crit damage server-side
        return !this.level().isClientSide && super.isCritArrow();
    }

    @Override
    public void tick() {
        super.tick();

        // On CLIENT only: spawn gold particles IF arrow is critical
        if (this.level().isClientSide && super.isCritArrow()) {
            for (int i = 0; i < 4; i++) {
                this.level().addParticle(
                        ModParticles.GOLD_PARTICLES.get(),
                        this.getX() + this.getDeltaMovement().x * i / 4.0,
                        this.getY() + this.getDeltaMovement().y * i / 4.0,
                        this.getZ() + this.getDeltaMovement().z * i / 4.0,
                        0.0, 0.01, 0.0
                );
            }
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    private static class HitData {
        public int hits;
        public long lastHitTime;

        public HitData() {
            this.hits = 1;
            this.lastHitTime = System.currentTimeMillis();
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - lastHitTime > 7000;
        }

        public void refresh() {
            this.hits++;
            this.lastHitTime = System.currentTimeMillis();
        }
    }

    private float chargeLevel = 0.0f;

    public void setChargeLevel(float charge) {
        this.chargeLevel = charge;
    }

    public float getChargeLevel() {
        return this.chargeLevel;
    }

    public static void spawnMarkParticlesStatic(LivingEntity victim, int hitCount) {
        Level level = victim.level();
        if (!(level instanceof ClientLevel clientLevel)) return;

        double x = victim.getX();
        double y = victim.getY() + victim.getBbHeight() + 0.3;
        double z = victim.getZ();

        for (int i = 0; i < hitCount; i++) {
            double offsetX = (level.random.nextDouble() - 0.5) * 0.3;
            double offsetZ = (level.random.nextDouble() - 0.5) * 0.3;

            clientLevel.addParticle(
                    ModParticles.GOLD_PARTICLES.get(),
                    x + offsetX,
                    y + 0.1 * i,
                    z + offsetZ,
                    0, 0, 0
            );
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        Entity shooter = this.getOwner();
        Entity target = result.getEntity();

        if (!(shooter instanceof LivingEntity attacker) || !(target instanceof LivingEntity victim)) return;



        UUID attackerId = attacker.getUUID();
        UUID targetId = victim.getUUID();

        Map<UUID, DeathArrowEntityHitInfo> targetMap = VoidWeaponry.DeathArrowTracker.HIT_MAP.computeIfAbsent(attackerId, k -> new HashMap<>());
        DeathArrowEntityHitInfo info = targetMap.get(targetId);

        float trueDamage = 0.0f;
        float actualArrowDamage = (float)(this.getBaseDamage() * this.getDeltaMovement().length());


        if (info == null || info.isExpired()) {
            info = new DeathArrowEntityHitInfo();
            targetMap.put(targetId, info);
        } else {
            info.refreshHit();
        }

        if (info.hitCount == 1 && this.chargeLevel >= 0.9f) {
            attacker.level().playSound(
                    null,
                    attacker.blockPosition(),
                    SoundEvents.ARROW_HIT_PLAYER,
                    SoundSource.PLAYERS,
                    2.0f,
                    0.5f
            );
        } else if (info.hitCount == 2 && this.chargeLevel >= 0.9f) {
            attacker.level().playSound(
                    null,
                    attacker.blockPosition(),
                    SoundEvents.ARROW_HIT_PLAYER,
                    SoundSource.PLAYERS,
                    2.0f,
                    0.6f
            );
        } else if (info.hitCount == 3 && this.chargeLevel >= 0.9f) {
            attacker.level().playSound(
                    null,
                    attacker.blockPosition(),
                    SoundEvents.ARROW_HIT_PLAYER,
                    SoundSource.PLAYERS,
                    2.0f,
                    0.7f
            );
        }

        if (info.hitCount >= 3 && this.chargeLevel >= 0.9f) {
            trueDamage = victim.getMaxHealth() * 0.05f;
            victim.hurt(victim.damageSources().magic(), trueDamage);
            targetMap.remove(targetId);
            victim.level().playSound(
                    null,
                    victim.blockPosition(),
                    SoundEvents.ZOMBIE_VILLAGER_CURE,
                    SoundSource.PLAYERS,
                    1.0f,
                    1.0f
            );
        }

        System.out.printf(
                "[Death Arrow] Hit %s | Hits: %d | Arrow Damage: %.2f | True Damage: %.2f | Max HP: %.2f | Remaining HP: %.2f%n",
                victim.getName().getString(),
                info.hitCount,
                actualArrowDamage,
                trueDamage,
                victim.getMaxHealth(),
                victim.getHealth()
        );

        if (!level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) level();
            for (int i = 0; i < info.hitCount; i++) {
                double offsetX = (level().random.nextDouble() - 0.5) * 0.3;
                double offsetZ = (level().random.nextDouble() - 0.5) * 0.3;

                serverLevel.sendParticles(
                        ModParticles.GOLD_PARTICLES.get(),
                        victim.getX() + offsetX,
                        victim.getY() + victim.getBbHeight() + 0.3 + 0.1 * i,
                        victim.getZ() + offsetZ,
                        1, 0, 0, 0, 0
                );
            }
        }

        VoidWeaponry.DeathArrowTracker.HIT_MAP.getOrDefault(attackerId, new HashMap<>()).entrySet().removeIf(e -> e.getValue().isExpired());
    }
}