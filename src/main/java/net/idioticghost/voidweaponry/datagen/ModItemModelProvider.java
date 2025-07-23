package net.idioticghost.voidweaponry.datagen;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;


public class ModItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, net.idioticghost.voidweaponry.VoidWeaponry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.DRAGON_BONE.get());
        basicItem(ModItems.DRAGON_SCALE.get());
        basicItem(ModItems.VOID_SHARD.get());
        basicItem(ModItems.VOIDBOUND_GOLD.get());
        basicItem(ModItems.VOID_KELP.get());
        basicItem(ModItems.COOKED_VOID_KELP.get());
        basicItem(ModItems.VOID_CATALYST.get());
        basicItem(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get());
        basicItem(ModItems.VOID_SEAGRASS_HELD.get());

        trimmedArmorItem(ModItems.VOIDGOLD_HELMET);
        trimmedArmorItem(ModItems.VOIDGOLD_CHESTPLATE);
        trimmedArmorItem(ModItems.VOIDGOLD_LEGGINGS);
        trimmedArmorItem(ModItems.VOIDGOLD_BOOTS);

        buttonItem(ModBlocks.VOIDGROWTH_BUTTON, ModBlocks.VOIDGROWTH_PLANKS);
        fenceItem(ModBlocks.VOIDGROWTH_FENCE, ModBlocks.VOIDGROWTH_PLANKS);

        simpleBlockItem(ModBlocks.VOIDGROWTH_DOOR);

        handheldItem(ModItems.VOIDGOLD_SWORD);
        handheldItem(ModItems.VOIDGOLD_PICKAXE);
        handheldItem(ModItems.VOIDGOLD_AXE);
        handheldItem(ModItems.VOIDGOLD_SHOVEL);
        handheldItem(ModItems.VOIDGOLD_HOE);

        handheldItem(ModItems.VOID_MULTITOOL);

        saplingItem(ModBlocks.SHADOW_PINE_SAPLING);
        basicItem(ModItems.STAR_BERRIES.get());
    }

    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID,"block/" + item.getId().getPath()));
    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(DeferredItem<Item> itemDeferredItem) {
        final String MOD_ID = VoidWeaponry.MOD_ID; // Change this to your mod id

        if(itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemDeferredItem.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace()  + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                        "item/" + itemDeferredItem.getId().getPath()));
            });
        }
    }

    private ItemModelBuilder handheldItem(DeferredItem<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID,"item/" + item.getId().getPath()));
    }

    public void buttonItem(DeferredBlock<? extends Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID,
                        "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock.get()).getPath()));
    }

    public void fenceItem(DeferredBlock<? extends Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID,
                        "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(DeferredBlock<? extends Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID,
                        "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(DeferredBlock<? extends Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID,"item/" + item.getId().getPath()));
    }
}
