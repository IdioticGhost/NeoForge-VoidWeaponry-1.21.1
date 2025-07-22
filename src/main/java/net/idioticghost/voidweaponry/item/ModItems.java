package net.idioticghost.voidweaponry.item;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.item.custom.*;
import net.idioticghost.voidweaponry.item.custom.ModFoodProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(VoidWeaponry.MOD_ID);

    public static final DeferredItem<Item> DRAGON_BONE = ITEMS.register("dragon_bone",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DRAGON_SCALE = ITEMS.register("dragon_scale",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> VOID_SHARD = ITEMS.register("void_shard",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> VOIDBOUND_GOLD = ITEMS.register("voidbound_gold",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> VOID_CATALYST = ITEMS.register("void_catalyst",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> VOIDGOLD_SMITHING_TEMPLATE = ITEMS.register("voidgold_smithing_template",
            () -> new Item(new Item.Properties()));

   public static final DeferredItem<Item> VOID_CRAFTER_ITEM = ITEMS.register("void_crafter",
            () -> new VoidCrafterItem(ModBlocks.VOID_CRAFTER.get(), new Item.Properties()));

    public static final DeferredItem<Item> VOID_KELP = ITEMS.register("void_kelp",
            () -> new ItemNameBlockItem(ModBlocks.VOID_KELP_TOP.get(), new Item.Properties()));

    public static final DeferredItem<Item> VOID_KELP_BLOCK_ITEM = ITEMS.register("void_kelp_block", () ->
            new BlockItem(ModBlocks.VOID_KELP_BLOCK.get(), new Item.Properties()) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return 200;
                }
            });

    public static final DeferredItem<Item> VOID_SEAGRASS_HELD = ITEMS.register("void_seagrass_held",
            () -> new ItemNameBlockItem(ModBlocks.VOID_SEAGRASS.get(), new Item.Properties()));

    public static final DeferredItem<Item> DEAD_GRASS_HELD = ITEMS.register("dead_grass_held",
            () -> new ItemNameBlockItem(ModBlocks.DEAD_GRASS.get(), new Item.Properties()));

    public static final DeferredItem<Item> SHORT_DEAD_GRASS_HELD = ITEMS.register("short_dead_grass_held",
            () -> new ItemNameBlockItem(ModBlocks.SHORT_DEAD_GRASS.get(), new Item.Properties()));

    //FOOD

    public static final DeferredItem<Item> COOKED_VOID_KELP = ITEMS.register("cooked_void_kelp",
            () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_VOID_KELP)));

    //FUEL

    //TOOLS

    public static final DeferredItem<Item> VOIDGOLD_SWORD = ITEMS.register("voidgold_sword",
            () -> new SwordItem(ModToolTiers.VOIDGOLD, new Item.Properties().
                    attributes(SwordItem.createAttributes(ModToolTiers.VOIDGOLD, 3, -2.4f))) {
                @Override
                public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_sword_1.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_sword_2.tooltip"));
                }});
    public static final DeferredItem<Item> VOIDGOLD_PICKAXE = ITEMS.register("voidgold_pickaxe",
            () -> new PickaxeItem(ModToolTiers.VOIDGOLD, new Item.Properties().
                    attributes(PickaxeItem.createAttributes(ModToolTiers.VOIDGOLD, 1, -2.8f))) {
                @Override
                public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_tools_1.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_tools_2.tooltip"));
                }});
    public static final DeferredItem<Item> VOIDGOLD_AXE = ITEMS.register("voidgold_axe",
            () -> new AxeItem(ModToolTiers.VOIDGOLD, new Item.Properties().
                    attributes(AxeItem.createAttributes(ModToolTiers.VOIDGOLD, 6, -3.2f))) {
                @Override
                public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_tools_1.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_tools_2.tooltip"));
                }});
    public static final DeferredItem<Item> VOIDGOLD_SHOVEL = ITEMS.register("voidgold_shovel",
            () -> new ShovelItem(ModToolTiers.VOIDGOLD, new Item.Properties().
                    attributes(ShovelItem.createAttributes(ModToolTiers.VOIDGOLD, 1.5f, -3.0f))) {
                @Override
                public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_tools_1.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_tools_2.tooltip"));
                }});
    public static final DeferredItem<Item> VOIDGOLD_HOE = ITEMS.register("voidgold_hoe",
            () -> new HoeItem(ModToolTiers.VOIDGOLD, new Item.Properties().
                    attributes(HoeItem.createAttributes(ModToolTiers.VOIDGOLD, 0, -3.0f))) {
                @Override
                public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_tools_1.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_tools_2.tooltip"));
                }});

    public static final DeferredItem<Item> VOID_MULTITOOL = ITEMS.register("void_multitool",
            () -> new VoidMultitool(ModToolTiers.VOIDGOLD, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> NETHERITE_BOW = ITEMS.register("netherite_bow",
            () -> new BowItem(new Item.Properties().durability(500)));

    //CUSTOM ITEMS

    public static final DeferredItem<Item> DEATH_BOW = ITEMS.register("death_bow",
            () -> new DeathBowItem(new Item.Properties().durability(500)) {
                @Override
                public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.death_bow_1.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.death_bow_2.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.death_bow_3.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.death_bow_4.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.death_bow_5.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.death_bow_6.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.death_bow_7.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.death_bow_8.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.death_bow_9.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.death_bow_10.tooltip"));
                    pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.death_bow_11.tooltip"));
                }});


    //ARMOR

    public static final DeferredItem<Item> VOIDGOLD_HELMET = ITEMS.register("voidgold_helmet",
            () -> new ModArmorItem(ModArmorMaterials.VOIDGOLD_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(40))));
    public static final DeferredItem<Item> VOIDGOLD_CHESTPLATE = ITEMS.register("voidgold_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.VOIDGOLD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(40))));
    public static final DeferredItem<Item> VOIDGOLD_LEGGINGS = ITEMS.register("voidgold_leggings",
            () -> new ModArmorItem(ModArmorMaterials.VOIDGOLD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(40))));
    public static final DeferredItem<Item> VOIDGOLD_BOOTS = ITEMS.register("voidgold_boots",
            () -> new ModArmorItem(ModArmorMaterials.VOIDGOLD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(40))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
