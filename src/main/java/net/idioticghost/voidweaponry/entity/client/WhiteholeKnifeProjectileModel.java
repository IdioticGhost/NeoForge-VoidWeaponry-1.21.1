package net.idioticghost.voidweaponry.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.entity.custom.WhiteholeKnifeProjectileEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class WhiteholeKnifeProjectileModel extends EntityModel<WhiteholeKnifeProjectileEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "whitehole_knife"), "main");
    private final ModelPart dagger;

    public WhiteholeKnifeProjectileModel(ModelPart root) {
        this.dagger = root.getChild("dagger");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition dagger = partdefinition.addOrReplaceChild("dagger", CubeListBuilder.create().texOffs(22, 13).addBox(-7.4821F, -7.9107F, -0.5F, 1.5F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 25).addBox(-7.9821F, -7.9107F, -0.5F, 0.5F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 11).addBox(-5.9821F, -7.4107F, -0.5F, 1.0F, 2.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 20).addBox(-4.9821F, -6.9107F, -0.5F, 0.5F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 23).addBox(-6.9821F, -6.9107F, -0.5F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 16).addBox(-4.4821F, -6.4107F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-6.4821F, -6.4107F, -0.5F, 0.5F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-3.4821F, -5.9107F, -0.5F, 0.5F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 11).addBox(-2.9821F, -5.4107F, -0.5F, 0.5F, 4.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 6).addBox(-2.4821F, -4.9107F, -0.5F, 0.5F, 4.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 26).addBox(-5.4821F, -4.9107F, -0.5F, 0.5F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 10).addBox(-1.9821F, -4.4107F, -0.5F, 0.5F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-1.4821F, -3.9107F, -0.5F, 0.5F, 5.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.9821F, -3.4107F, -0.5F, 0.5F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 26).addBox(-3.9821F, -3.4107F, -0.5F, 0.5F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-0.4821F, -2.9107F, -0.5F, 0.5F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 23).addBox(0.0179F, -2.4107F, -0.5F, 0.5F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 17).addBox(0.5179F, -1.4107F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 5).addBox(1.5179F, -0.9107F, -0.5F, 0.5F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 10).addBox(2.0179F, -0.4107F, -0.5F, 0.5F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 12).addBox(2.5179F, 0.5893F, -0.5F, 0.5F, 4.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 22).addBox(1.5179F, 1.5893F, -0.5F, 0.5F, 2.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 19).addBox(-0.4821F, 1.5893F, -0.5F, 0.5F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 18).addBox(3.0179F, 2.0893F, -0.5F, 0.5F, 3.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 15).addBox(0.0179F, 2.0893F, -0.5F, 1.5F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 22).addBox(3.5179F, 3.0893F, -0.5F, 0.5F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 3).addBox(0.5179F, 3.0893F, -0.5F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 7).addBox(4.0179F, 3.5893F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 15).addBox(5.0179F, 4.0893F, -0.5F, 1.0F, 2.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 24).addBox(6.0179F, 4.5893F, -0.5F, 0.5F, 1.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(5.5179F, 4.5893F, -1.0F, 1.0F, 1.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 19).addBox(5.0179F, 5.0893F, -1.0F, 0.5F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(4.5179F, 5.5893F, -1.0F, 1.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 20).addBox(1.5179F, 1.5893F, -1.0F, 0.5F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 0).addBox(1.5179F, -0.4107F, -1.0F, 0.5F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 7).addBox(-0.4821F, -0.4107F, -1.0F, 0.5F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 10).addBox(-0.4821F, 1.5893F, -1.0F, 0.5F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 16).addBox(-0.9821F, 0.0893F, -1.0F, 0.5F, 1.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 3).addBox(2.0179F, 0.0893F, -1.0F, 0.5F, 1.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 7).addBox(0.0179F, -0.9107F, -1.0F, 1.5F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(0.0179F, 2.0893F, -1.0F, 1.5F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 3).addBox(4.5179F, 6.0893F, -1.0F, 1.5F, 0.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0179F, 16.4107F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(WhiteholeKnifeProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        dagger.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}