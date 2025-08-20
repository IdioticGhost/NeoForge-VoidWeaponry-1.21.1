package net.idioticghost.voidweaponry.entity.renderer;

import net.idioticghost.voidweaponry.entity.custom.MaelstromRingEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MaelstromRingRenderer extends EntityRenderer<MaelstromRingEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("voidweaponry", "textures/entity/death_arrow.png");

    public MaelstromRingRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(MaelstromRingEntity MaelstromRingEntity) {
        return TEXTURE;
    }
}