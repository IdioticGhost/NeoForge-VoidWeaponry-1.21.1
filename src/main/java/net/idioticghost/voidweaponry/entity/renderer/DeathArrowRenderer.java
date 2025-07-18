package net.idioticghost.voidweaponry.entity.renderer;

import net.idioticghost.voidweaponry.entity.custom.DeathArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DeathArrowRenderer extends ArrowRenderer<DeathArrowEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("voidweaponry", "textures/entity/death_arrow.png");

    public DeathArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(DeathArrowEntity entity) {
        return TEXTURE;
    }
}