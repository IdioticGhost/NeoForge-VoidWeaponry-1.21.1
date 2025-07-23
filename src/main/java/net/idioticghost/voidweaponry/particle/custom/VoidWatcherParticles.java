package net.idioticghost.voidweaponry.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import org.checkerframework.checker.nullness.qual.Nullable;

public class VoidWatcherParticles extends TextureSheetParticle {
    private final double targetX, targetY, targetZ;

    protected VoidWatcherParticles(ClientLevel level, double x, double y, double z,
                                   double targetX, double targetY, double targetZ,
                                   SpriteSet spriteSet) {
        super(level, x, y, z, 0, 0, 0);
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;

        this.quadSize = 0.05f;
        this.lifetime = 40;

        this.setSpriteFromAge(spriteSet);

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }

    @Override
    public void tick() {
        super.tick();

        double dx = targetX - this.x;
        double dy = targetY - this.y;
        double dz = targetZ - this.z;

        double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (dist < 0.1) {
            this.remove();
            return;
        }

        dx /= dist;
        dy /= dist;
        dz /= dist;

        double speed = 0.035;

        this.xd = dx * speed;
        this.yd = dy * speed;
        this.zd = dz * speed;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public VoidWatcherParticles createParticle(SimpleParticleType type, ClientLevel level,
                                                   double x, double y, double z,
                                                   double targetX, double targetY, double targetZ) {
            return new VoidWatcherParticles(level, x, y, z, targetX, targetY, targetZ, this.spriteSet);
        }
    }
}