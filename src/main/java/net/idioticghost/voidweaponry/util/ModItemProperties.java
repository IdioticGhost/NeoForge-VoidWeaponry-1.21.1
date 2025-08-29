package net.idioticghost.voidweaponry.util;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.component.ModDataComponents;
import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        makeCustomBow(ModItems.NETHERITE_BOW.get());
        makeCustomBow(ModItems.DEATH_BOW.get());

        ItemProperties.register(ModItems.BLACKHOLE_KNIFE.get(),
                ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "used"),
                (stack, level, entity, seed) ->
                        stack.getOrDefault(ModDataComponents.THREW_KNIFE.get(), Boolean.FALSE) ? 1.0F : 0.0F
        );

        ItemProperties.register(ModItems.BLACKHOLE_KNIFE.get(),
                ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "knife_version"),
                (stack, level, entity, seed) ->
                        stack.getOrDefault(ModDataComponents.KNIFE_VERSION.get(), Boolean.FALSE) ? 1.0F : 0.0F
        );

        ItemProperties.register(ModItems.DRAGON_KATANA.get(),
                ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "katana_heat_value"),
                (stack, level, entity, seed) -> {
                    int heat = stack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
                    return (float) heat;
                }
        );
    }

    private static void makeCustomBow(Item item) {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getUseItem() != itemStack ? 0.0F : (float)(itemStack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(
                item, ResourceLocation.withDefaultNamespace("pulling"), (itemStack, clientLevel, livingEntity, i)
                        -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F
        );
    }
}
