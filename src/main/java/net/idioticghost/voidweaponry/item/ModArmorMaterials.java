package net.idioticghost.voidweaponry.item;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

    public class ModArmorMaterials {
        public static final Holder<ArmorMaterial> VOIDGOLD_ARMOR_MATERIAL = register("voidgold", Util.make(new EnumMap<>(ArmorItem.Type.class),
                attribute -> {
                    attribute.put(ArmorItem.Type.BOOTS, 4);
                    attribute.put(ArmorItem.Type.LEGGINGS, 7);
                    attribute.put(ArmorItem.Type.CHESTPLATE, 9);
                    attribute.put(ArmorItem.Type.HELMET, 4);
                    attribute.put(ArmorItem.Type.BODY, 11);
                }), 22, 4f, 0.1f, () -> ModItems.VOIDBOUND_GOLD.get());

        private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> typeProtection,
                                                      int enchantability, float toughness, float knockbackResistance,
                                                      Supplier<Item> ingredientItem) {
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, name);
            Holder<SoundEvent> equipSound = SoundEvents.ARMOR_EQUIP_NETHERITE;
            Supplier<Ingredient> ingredient = () -> Ingredient.of(ingredientItem.get());
            List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(location));

            EnumMap<ArmorItem.Type, Integer> typeMap = new EnumMap<>(ArmorItem.Type.class);
            for (ArmorItem.Type type : ArmorItem.Type.values()) {
                typeMap.put(type, typeProtection.get(type));
            }

            return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, location,
                    new ArmorMaterial(typeProtection, enchantability, equipSound, ingredient, layers, toughness, knockbackResistance));
        }
    }
