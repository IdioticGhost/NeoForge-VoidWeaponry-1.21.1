package net.idioticghost.voidweaponry.event.custom;

import net.idioticghost.voidweaponry.entity.custom.MaelstromRingEntity;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = "voidweaponry")
public class MaelstromRingHandler {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide) return;

        LivingEntity attacked = event.getEntity();
        DamageSource source = event.getSource();

        if (!(source.getEntity() instanceof Player player)) return;

        Level level = player.level();
        if (!(level instanceof ServerLevel serverLevel)) return;

        level.getEntitiesOfClass(
                MaelstromRingEntity.class,
                player.getBoundingBox().inflate(50),
                ring -> ring.getOwner() != null && player.getUUID().equals(ring.getOwner().getUUID())
        ).forEach(ring -> {
            double radius = ring.getRadius();
            AABB area = new AABB(
                    ring.getX() - radius, ring.getY() - 1, ring.getZ() - radius,
                    ring.getX() + radius, ring.getY() + 9, ring.getZ() + radius
            );

            level.getEntitiesOfClass(
                    LivingEntity.class,
                    area,
                    e -> e != player && e.isAlive()
            ).forEach(target -> {
                ring.queuedHits.add(target);
                spawnSpike(serverLevel, target); // trigger impale effect
            });
        });
    }

    public static void spawnSpike(ServerLevel level, LivingEntity target) {
        RandomSource random = level.random;

        Vec3 pos = target.position();
        double baseX = pos.x;
        double baseY = target.getBoundingBox().minY;
        double baseZ = pos.z;

        // scale with entity size
        double entityHeight = target.getBbHeight();
        int segments = Math.max(6, (int) (entityHeight * 8)); // smoother scaling

        for (int i = 0; i < segments; i++) {
            double y = baseY + (i / (double) segments) * entityHeight;

            level.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, Blocks.ICE.defaultBlockState()), // ice block particles
                    baseX,        // X position
                    y,        // Y position
                    baseZ,        // Z position
                    1,        // number of particles
                    0.1,      // x offset
                    0.1,      // y offset
                    0.1,      // z offset
                    0.05      // speed
            );

            if (random.nextFloat() < 0.15F) {
                level.sendParticles(
                        ParticleTypes.SNOWFLAKE,
                        baseX, y, baseZ,
                        1, 0.01, 0.04, 0.01, 0.0
                );
            }
        }

        level.playSound(
                null, baseX, baseY, baseZ,
                SoundEvents.GLASS_BREAK, // sharp ice-shattering sound
                SoundSource.PLAYERS,
                0.5F,
                0.8F + random.nextFloat() * 0.3F
        );
    }
}