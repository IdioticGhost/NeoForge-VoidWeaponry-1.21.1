package net.idioticghost.voidweaponry.item.client;

import net.idioticghost.voidweaponry.item.custom.VoidCrafterItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class VoidCrafterBlockItemRenderer extends GeoItemRenderer<VoidCrafterItem> {
    public VoidCrafterBlockItemRenderer() {
        super(new VoidCrafterBlockItemModel());
    }
}
