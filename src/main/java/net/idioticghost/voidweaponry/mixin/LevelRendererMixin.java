package net.idioticghost.voidweaponry.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.worldgen.dimension.ModDimensions;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Unique
    private static final ResourceLocation VOIDMAW_SKY_LOCATION =
            ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "textures/environment/voidmaw_sky.png");

    @Unique
    private static final ResourceLocation VOIDMAW_OVERLAY_SKY_LOCATION =
            ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "textures/environment/voidmaw_sky_overlay.png");

    @Unique
    private static final ResourceLocation VOIDMAW_OVERLAY_2_SKY_LOCATION =
            ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "textures/environment/voidmaw_sky_overlay_2.png");

    @Unique
    private static final ResourceLocation VOIDMAW_OVERLAY_3_SKY_LOCATION =
            ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "textures/environment/voidmaw_sky_overlay_3.png");

    @Unique
    private static final ResourceLocation VOIDMAW_SUN_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "textures/environment/voidmaw_sun.png");

    @Unique
    private static final float[][] UVS = {
            {0.25f, 0.6666f, 0.5f, 1.0f},    // Top
            {0.25f, 0.0f,    0.5f, 0.3333f}, // Bottom
            {0.0f,  0.3333f, 0.25f, 0.6666f},// Left
            {0.25f, 0.3333f, 0.5f, 0.6666f}, // Front
            {0.5f,  0.3333f, 0.75f, 0.6666f},// Right
            {0.75f, 0.3333f, 1.0f, 0.6666f}  // Back
    };

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    private void onRenderSky(Matrix4f pose, Matrix4f matrix, float partialTick,
                             Camera cam, boolean bool, Runnable runnable, CallbackInfo ci) {

        if (cam.getEntity().level().dimension() != ModDimensions.VOID_MAW_LEVEL_KEY) return;

        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null) return;

        if (level.effects().renderSky(level, ((LevelRenderer) (Object) this).getTicks(),
                partialTick, matrix, cam, pose, bool, runnable)) {
            return;
        }

        runnable.run();

        if (level.dimension() == ModDimensions.VOID_MAW_LEVEL_KEY) {
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

            PoseStack poseStack = new PoseStack();
            poseStack.mulPose(pose);

            renderSkyboxCube(poseStack, VOIDMAW_SKY_LOCATION, 0f, 1f, false);

            int ticks = ((LevelRenderer) (Object) this).getTicks();
            float overlayRotation1 = (ticks + partialTick) * 0.015f % 360f;
            float overlayRotation2 = (ticks + partialTick) * -0.015f % 360f;

            renderSkyboxCube(poseStack, VOIDMAW_OVERLAY_SKY_LOCATION, overlayRotation1, 0.5f, true);
            renderSkyboxCube(poseStack, VOIDMAW_OVERLAY_2_SKY_LOCATION, overlayRotation2, 0.65f, true);
            renderSkyboxCube(poseStack, VOIDMAW_OVERLAY_3_SKY_LOCATION, 0f, 0.4f, true);

            // Draw custom rotating sun
            renderCustomSun(poseStack, 50.0f, 0.9f, ticks + partialTick);

            ci.cancel();
        }
    }

    @Unique
    private void renderCustomSun(PoseStack poseStack, float size, float alpha, float time) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShaderTexture(0, VOIDMAW_SUN_TEXTURE);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        poseStack.pushPose();

        // Rotate sun independently of world time
        float rotation = (time * 0.01f) % 360f;
        poseStack.mulPose(Axis.XP.rotationDegrees(rotation));

        // Place the sun "in the sky"
        poseStack.translate(0, 0, -100f);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        Matrix4f matrix4f = poseStack.last().pose();
        float half = size / 2.0f;

        bufferBuilder.addVertex(matrix4f, -half, -half, 0).setUv(0.0f, 1.0f);
        bufferBuilder.addVertex(matrix4f,  half, -half, 0).setUv(1.0f, 1.0f);
        bufferBuilder.addVertex(matrix4f,  half,  half, 0).setUv(1.0f, 0.0f);
        bufferBuilder.addVertex(matrix4f, -half,  half, 0).setUv(0.0f, 0.0f);

        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
        poseStack.popPose();

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    @Unique
    private void renderSkyboxCube(PoseStack poseStack, ResourceLocation texture,
                                  float rotationYDegrees, float alpha, boolean enableBlend) {
        if (enableBlend) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(false);
        } else {
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
        }

        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.disableCull();

        poseStack.pushPose();
        if (rotationYDegrees != 0f) {
            poseStack.mulPose(Axis.YP.rotationDegrees(rotationYDegrees));
        }

        Tesselator tesselator = Tesselator.getInstance();
        int colorAlpha = (int) (alpha * 255);

        for (int i = 0; i < 6; ++i) {
            poseStack.pushPose();

            switch (i) {
                case 1 -> poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));   // Top
                case 0 -> poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));  // Bottom
                case 2 -> poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));   // Left
                case 3 -> { /* Front (no rotation) */ }
                case 4 -> poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));  // Right
                case 5 -> poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));  // Back
            }

            float u0 = UVS[i][0];
            float v0 = UVS[i][1];
            float u1 = UVS[i][2];
            float v1 = UVS[i][3];

            Matrix4f matrix4f = poseStack.last().pose();
            BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

            bufferBuilder.addVertex(matrix4f, -100f, -100f, -100f).setUv(u0, v1).setColor(255, 255, 255, colorAlpha);
            bufferBuilder.addVertex(matrix4f, -100f,  100f, -100f).setUv(u0, v0).setColor(255, 255, 255, colorAlpha);
            bufferBuilder.addVertex(matrix4f,  100f,  100f, -100f).setUv(u1, v0).setColor(255, 255, 255, colorAlpha);
            bufferBuilder.addVertex(matrix4f,  100f, -100f, -100f).setUv(u1, v1).setColor(255, 255, 255, colorAlpha);

            BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
            poseStack.popPose();
        }

        poseStack.popPose();

        if (enableBlend) {
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
        }
        RenderSystem.enableCull();
    }
}