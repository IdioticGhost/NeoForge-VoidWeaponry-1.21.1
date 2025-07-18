package net.idioticghost.voidweaponry.item.client;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.item.custom.VoidCrafterItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class VoidCrafterBlockItemModel extends GeoModel<VoidCrafterItem> {

    @Override
    public ResourceLocation getModelResource(VoidCrafterItem voidCrafterItem) {
        return ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "geo/void_crafter.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VoidCrafterItem voidCrafterItem) {
        return ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "textures/block/void_crafter.png");
    }

    @Override
    public ResourceLocation getAnimationResource(VoidCrafterItem voidCrafterItem) {
        return ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "animations/void_crafter.animations.json");
    }
}

