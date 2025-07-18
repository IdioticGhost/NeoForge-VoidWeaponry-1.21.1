package net.idioticghost.voidweaponry.block.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.idioticghost.voidweaponry.block.custom.VoidCrafterBlock;
import net.idioticghost.voidweaponry.block.entity.custom.VoidCrafterBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class VoidCrafterRenderer extends GeoBlockRenderer<VoidCrafterBlockEntity> {

    public VoidCrafterRenderer(BlockEntityRendererProvider.Context context) {
        super(new VoidCrafterModel());
    }

    @Override
    public void render(VoidCrafterBlockEntity entity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        super.render(entity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);

        if (entity.getLevel() == null) {
            return; // no world loaded, skip rendering
        }

        var blockPos = entity.getBlockPos();
        var state = entity.getLevel().getBlockState(blockPos);

        // Only continue if the block at the position is our custom block
        if (!(state.getBlock() instanceof VoidCrafterBlock)) {
            return;
        }

        float baseX = 0.5f;
        float baseY = 1.5f;
        float baseZ = 0.5f;

        double ticks = entity.getTick(entity) + partialTick;
        double bobbing = Math.sin(ticks * 0.087266) * 0.075;

        // Safe to get facing now
        Direction facing = state.getValue(VoidCrafterBlock.FACING);
        float rotationYaw = switch (facing) {
            case SOUTH -> 180f;
            case WEST -> 270f;
            case EAST -> 90f;
            default -> 0f; // NORTH
        };

        if (entity.isCrafting()) {
            for (int i = 0; i < 9; i++) {
                ItemStack stack = entity.itemHandler.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    poseStack.pushPose();

                    int row = i / 3;
                    int col = i % 3;
                    float xOffset = (col - 1) * 0.25f;
                    float zOffset = (row - 1) * 0.25f;

                    poseStack.translate(baseX, baseY + bobbing, baseZ);
                    poseStack.mulPose(Axis.YP.rotationDegrees(rotationYaw));
                    poseStack.translate(xOffset, 0, zOffset);

                    poseStack.scale(0.25f, 0.25f, 0.25f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(entity.getRenderingRotation()));

                    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED,
                            packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, null, 1);
                    poseStack.popPose();
                }
            }
        } else {
            ItemStack outputStack = entity.itemHandler.getStackInSlot(9);
            if (!outputStack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(baseX, baseY + bobbing, baseZ);
                poseStack.mulPose(Axis.YP.rotationDegrees(rotationYaw));
                poseStack.scale(0.35f, 0.35f, 0.35f);
                poseStack.mulPose(Axis.YP.rotationDegrees(entity.getRenderingRotation()));

                Minecraft.getInstance().getItemRenderer().renderStatic(outputStack, ItemDisplayContext.FIXED,
                        packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, null, 1);
                poseStack.popPose();
            }
        }
    }
}