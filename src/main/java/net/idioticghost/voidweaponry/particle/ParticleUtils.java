package net.idioticghost.voidweaponry.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticleUtils {

    /**
     * Spawns a particle line relative to the player's view,
     * from bottom-left to top-right of the screen, about 1 block away.
     */
    public static void spawnDiagonalLine(Player player, ParticleOptions particleType, int steps, double width, double curveAmount) {
        if (player.level().isClientSide()) return; // only run on server

        ServerLevel serverLevel = (ServerLevel) player.level();

        Vec3 eyePos = player.getEyePosition(1.0f);
        Vec3 look = player.getLookAngle();
        Vec3 right = look.cross(new Vec3(0, 1, 0)).normalize();
        Vec3 up = right.cross(look).normalize();

        double distance = 1.0;

        Vec3 start = eyePos.add(look.scale(distance)).add(right.scale(-width)).add(up.scale(-width));
        Vec3 end = eyePos.add(look.scale(distance)).add(right.scale(width)).add(up.scale(width));

        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            double x = start.x + (end.x - start.x) * t;
            double y = start.y + (end.y - start.y) * t;
            double z = start.z + (end.z - start.z) * t;

            Vec3 curveOffset = look.scale(Math.sin(t * Math.PI) * curveAmount);

            serverLevel.sendParticles(
                    particleType,
                    x + curveOffset.x, y + curveOffset.y, z + curveOffset.z,
                    1, // count
                    0, 0, 0, // offset
                    0        // speed
            );
        }
    }
}