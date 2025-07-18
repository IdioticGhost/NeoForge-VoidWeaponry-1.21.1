package net.idioticghost.voidweaponry.enchantment;//package net.ghost.voidweaponry.enchantment;
//
//import net.ghost.voidweaponry.VoidWeaponry;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.data.worldgen.BootstrapContext;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.tags.EnchantmentTags;
//import net.minecraft.tags.ItemTags;
//import net.minecraft.world.entity.EquipmentSlotGroup;
//import net.minecraft.world.item.enchantment.Enchantment;
//import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
//import net.minecraft.world.item.enchantment.EnchantmentTarget;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.RegistryObject;
//
//public class ModEnchantments {
//    public static final ResourceKey<Enchantment> STRENGTH_RUNE = ResourceKey.create(
//            Registries.ENCHANTMENT,
//            ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "strength_rune")
//    );
//
//    public static void bootstrap(BootstrapContext<Enchantment> context) {
//        var items = context.lookup(Registries.ITEM);
//
//        context.register(STRENGTH_RUNE,
//                Enchantment.enchantment(
//                        Enchantment.definition(
//                                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
//                                items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
//                                1,  // max level
//                                2,  // rarity
//                                Enchantment.dynamicCost(5, 10),
//                                Enchantment.dynamicCost(25, 10),
//                                30,
//                                EquipmentSlotGroup.MAINHAND,
//                                EquipmentSlotGroup.ARMOR
//                        )
//                ).build(STRENGTH_RUNE.location()));
//    }
//}