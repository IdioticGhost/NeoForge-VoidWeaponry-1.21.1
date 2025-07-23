package net.idioticghost.voidweaponry.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;

public class FireflyParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    private final float[] startColor = {1.0f, 0.3f, 0.0f}; // Blood orange
    private final float[] endColor   = {1.0f, 1.0f, 1.0f}; // White

    public FireflyParticle(ClientLevel level, double x, double y, double z,
                           double xd, double yd, double zd, SpriteSet sprites) {
        super(level, x, y, z, xd, yd, zd);
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);

        this.lifetime = 120; // 6 seconds (20 ticks * 6)
        this.gravity = 0.0f;
        this.quadSize = 0.025f;
        this.xd = xd * 0.02;
        this.yd = yd * 0.02;
        this.zd = zd * 0.02;

        // Set initial color
        this.setColor(startColor[0], startColor[1], startColor[2]);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(sprites);

        float ageRatio = (float) this.age / (float) this.lifetime;
        float r = Mth.lerp(ageRatio, startColor[0], endColor[0]);
        float g = Mth.lerp(ageRatio, startColor[1], endColor[1]);
        float b = Mth.lerp(ageRatio, startColor[2], endColor[2]);
        this.setColor(r, g, b);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}