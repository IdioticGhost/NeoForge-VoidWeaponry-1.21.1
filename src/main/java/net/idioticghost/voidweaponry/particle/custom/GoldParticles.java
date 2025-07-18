package net.idioticghost.voidweaponry.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class GoldParticles extends TextureSheetParticle {

    protected GoldParticles(ClientLevel level, double x, double y, double z,
                            double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);

        this.gravity = 0.02f;
        this.lifetime = 20 + random.nextInt(10);
        this.quadSize = 0.15f + (random.nextFloat() * 0.25f);
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.hasPhysics = false;
    }

    @Override
    public void tick() {
        super.tick();

        this.alpha = 1.0f - ((float) this.age / (float) this.lifetime);

        this.yd += 0.001;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public GoldParticles createParticle(SimpleParticleType type, ClientLevel level,
                                            double x, double y, double z,
                                            double xSpeed, double ySpeed, double zSpeed) {
            GoldParticles particle = new GoldParticles(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}