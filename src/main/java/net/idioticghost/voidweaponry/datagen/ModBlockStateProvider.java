package net.idioticghost.voidweaponry.datagen;


import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.block.custom.VoidLantern;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, VoidWeaponry.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //blockWithItem(ModBlocks.ENDSTONE_SAND_BLOCK);
        blockWithItem(ModBlocks.SMOOTH_ENDSTONE);
        blockWithItem(ModBlocks.VOID_ORE);
        blockWithItem(ModBlocks.VOID_KELP_BLOCK);
        blockWithItem(ModBlocks.VOIDGROWTH_PLANKS);


        stairsBlock(ModBlocks.VOIDGROWTH_STAIRS.get(), blockTexture(ModBlocks.VOIDGROWTH_PLANKS.get()));
        slabBlock(ModBlocks.VOIDGROWTH_SLAB.get(), blockTexture(ModBlocks.VOIDGROWTH_PLANKS.get()), blockTexture(ModBlocks.VOIDGROWTH_PLANKS.get()));

        buttonBlock(ModBlocks.VOIDGROWTH_BUTTON.get(), blockTexture(ModBlocks.VOIDGROWTH_PLANKS.get()));
        pressurePlateBlock(ModBlocks.VOIDGROWTH_PRESSURE_PLATE.get(), blockTexture(ModBlocks.VOIDGROWTH_PLANKS.get()));

        fenceBlock(ModBlocks.VOIDGROWTH_FENCE.get(), blockTexture(ModBlocks.VOIDGROWTH_PLANKS.get()));
        fenceGateBlock(ModBlocks.VOIDGROWTH_FENCE_GATE.get(), blockTexture(ModBlocks.VOIDGROWTH_PLANKS.get()));

        doorBlockWithRenderType(ModBlocks.VOIDGROWTH_DOOR.get(), modLoc("block/voidgrowth_door_bottom"), modLoc("block/voidgrowth_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.VOIDGROWTH_TRAPDOOR.get(), modLoc("block/voidgrowth_trapdoor"), true, "cutout");

        blockItem(ModBlocks.VOIDGROWTH_STAIRS);
        blockItem(ModBlocks.VOIDGROWTH_SLAB);
        blockItem(ModBlocks.VOIDGROWTH_PRESSURE_PLATE);
        blockItem(ModBlocks.VOIDGROWTH_FENCE_GATE);
        blockItem(ModBlocks.VOIDGROWTH_TRAPDOOR, "_bottom");

        customLamp();

        logBlock(ModBlocks.SHADOW_PINE_LOG.get());
        axisBlock(ModBlocks.SHADOW_PINE_WOOD.get(), blockTexture(ModBlocks.SHADOW_PINE_LOG.get()), blockTexture(ModBlocks.SHADOW_PINE_LOG.get()));
        logBlock(ModBlocks.STRIPPED_SHADOW_PINE_LOG.get());
        axisBlock(ModBlocks.STRIPPED_SHADOW_PINE_WOOD.get(), blockTexture(ModBlocks.STRIPPED_SHADOW_PINE_LOG.get()), blockTexture(ModBlocks.STRIPPED_SHADOW_PINE_LOG.get()));

        blockItem(ModBlocks.SHADOW_PINE_LOG);
        blockItem(ModBlocks.SHADOW_PINE_WOOD);
        blockItem(ModBlocks.STRIPPED_SHADOW_PINE_LOG);
        blockItem(ModBlocks.STRIPPED_SHADOW_PINE_WOOD);

        blockWithItem(ModBlocks.GNARLED_SHADOW_PINE);

        leavesBlock(ModBlocks.SHADOW_PINE_LEAVES);
        leavesBlock(ModBlocks.PURPLE_SHADOW_PINE_LEAVES);
        saplingBlock(ModBlocks.SHADOW_PINE_SAPLING);
    }

    private void saplingBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void leavesBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }


    private void customLamp() {
        ModelFile offModel = models()
                .withExistingParent("void_lantern_off", mcLoc("block/lantern"))
                .texture("lantern", modLoc("block/void_lantern_off"));

        ModelFile onModel = models()
                .withExistingParent("void_lantern_on", mcLoc("block/lantern"))
                .texture("lantern", modLoc("block/void_lantern_on"));

        getVariantBuilder(ModBlocks.VOID_LANTERN.get()).forAllStates(state -> {
            boolean clicked = state.getValue(VoidLantern.CLICKED);
            return new ConfiguredModel[]{new ConfiguredModel(clicked ? onModel : offModel)};
        });

        // Make sure your item uses the ON model for better visibility
        itemModels().withExistingParent("void_lantern", "item/generated")
                .texture("layer0", modLoc("item/void_lantern"));
    }


    private void blockWithItem(DeferredBlock<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockItem(DeferredBlock<? extends Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("voidweaponry:block/" +
                BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockItem(DeferredBlock<? extends Block> blockRegistryObject, String appendix) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("voidweaponry:block/" +
                BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath() + appendix));
    }

}
