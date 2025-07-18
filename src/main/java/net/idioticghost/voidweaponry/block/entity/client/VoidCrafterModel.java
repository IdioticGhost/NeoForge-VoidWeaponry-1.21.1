package net.idioticghost.voidweaponry.block.entity.client;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.custom.VoidCrafterBlock;
import net.idioticghost.voidweaponry.block.entity.custom.VoidCrafterBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class VoidCrafterModel extends GeoModel<VoidCrafterBlockEntity> {
    @Override
    public ResourceLocation getModelResource(VoidCrafterBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "geo/void_crafter.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VoidCrafterBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "textures/block/void_crafter.png");
    }

    @Override
    public ResourceLocation getAnimationResource(VoidCrafterBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "animations/void_crafter.animations.json");
    }

    @Override
    public void setCustomAnimations(VoidCrafterBlockEntity animatable, long instanceId, AnimationState<VoidCrafterBlockEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        Direction facing = animatable.getBlockState().getValue(VoidCrafterBlock.FACING);
        float rotation = switch (facing) {
            case SOUTH -> 270f;   // flipped
            case WEST -> 180f;
            case EAST -> 0f;
            default -> 90f;    // NORTH
        };

        // Apply rotation to the root bone
        var rootBone = getAnimationProcessor().getBone("void_crafter");
        if (rootBone != null) {
            rootBone.setRotY((float) Math.toRadians(rotation));
        }
    }
}