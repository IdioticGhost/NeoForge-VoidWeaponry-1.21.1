package net.idioticghost.voidweaponry.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.entity.client.BlackholeKnifeProjectileModel;
import net.idioticghost.voidweaponry.entity.client.DragonFireballProjectileModel;
import net.idioticghost.voidweaponry.entity.custom.BlackholeKnifeProjectileEntity;
import net.idioticghost.voidweaponry.entity.custom.DragonFireballProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DragonFireballProjectileRenderer extends EntityRenderer<DragonFireballProjectileEntity> {
    private DragonFireballProjectileModel model;

    public DragonFireballProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new DragonFireballProjectileModel(context.bakeLayer(DragonFireballProjectileModel.LAYER_LOCATION));
    }

    @Override
    public void render(DragonFireballProjectileEntity pEntity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.scale(3.0f, 3.0f, 3.0f);

        if (!pEntity.grounded) {
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, pEntity.yRotO, pEntity.getYRot())));
            poseStack.mulPose(Axis.XP.rotationDegrees(pEntity.getRenderingRotation() * 5f));
            poseStack.mulPose(Axis.YP.rotationDegrees(pEntity.getRenderingRotation() * 5f));

            poseStack.translate(0, -1.2f, 0);
        }

        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(
                buffer, this.model.renderType(this.getTextureLocation(pEntity)),false, false);
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(pEntity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(DragonFireballProjectileEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "textures/entity/dragon_fireball.png");
    }
}