package net.idioticghost.voidweaponry.entity.custom;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.particle.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
        return !this.level().isClientSide && super.isCritArrow();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide && super.isCritArrow()) {
            double dx = this.getDeltaMovement().x;
            double dy = this.getDeltaMovement().y;
            double dz = this.getDeltaMovement().z;

            for (int i = 0; i < 2; i++) {
                this.level().addParticle(
                        ModParticles.GOLD_PARTICLES.get(),
                        this.getX() + dx * i / 4.0,
                        this.getY() + dy * i / 4.0 - 0.1,
                        this.getZ() + dz * i / 4.0,
                        0, 0.01, 0
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


    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        Entity shooter = this.getOwner();
        Entity target = result.getEntity();

        if (!(shooter instanceof LivingEntity attacker) || !(target instanceof LivingEntity victim)) return;

        MobEffectInstance currentMarked = victim.getEffect(MobEffects.GLOWING);
        int newAmplifier = 0;

        if (this.chargeLevel >= 0.9f) {
            if (currentMarked != null) {
                int currentAmp = currentMarked.getAmplifier();
                newAmplifier = Math.min(currentAmp + 1, 2);
            }


            int durationTicks = (newAmplifier == 2) ? 20 : 140;

            MobEffectInstance newMarkedEffect = new MobEffectInstance(MobEffects.GLOWING, durationTicks, newAmplifier, false, false);

            victim.addEffect(newMarkedEffect);
        }

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

        if (info.hitCount == 3 && this.chargeLevel >= 0.9f) {
            attacker.level().playSound(
                    null,
                    attacker.blockPosition(),
                    SoundEvents.ARROW_HIT_PLAYER,
                    SoundSource.PLAYERS,
                    2.0f,
                    0.7f
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
        } else if (info.hitCount == 1 && this.chargeLevel >= 0.9f) {
            attacker.level().playSound(
                    null,
                    attacker.blockPosition(),
                    SoundEvents.ARROW_HIT_PLAYER,
                    SoundSource.PLAYERS,
                    2.0f,
                    0.4f
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

        VoidWeaponry.DeathArrowTracker.HIT_MAP.getOrDefault(attackerId, new HashMap<>()).entrySet().removeIf(e -> e.getValue().isExpired());
    }
}