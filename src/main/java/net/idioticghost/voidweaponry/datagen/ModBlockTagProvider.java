package net.idioticghost.voidweaponry.datagen;


import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, VoidWeaponry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
         tag(BlockTags.MINEABLE_WITH_PICKAXE)
                 .add(ModBlocks.VOID_ORE.get());
         //To add more, dont add a comma, just copy paste the .add.


        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.VOID_ORE.get());

        tag(BlockTags.FENCES).add(ModBlocks.VOIDGROWTH_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.VOIDGROWTH_FENCE_GATE.get());

        tag(ModTags.Blocks.NEEDS_VOIDGOLD_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        this.tag(BlockTags.LOGS)
                .add(ModBlocks.SHADOW_PINE_LOG.get())
                .add(ModBlocks.SHADOW_PINE_WOOD.get())
                .add(ModBlocks.STRIPPED_SHADOW_PINE_WOOD.get())
                .add(ModBlocks.STRIPPED_SHADOW_PINE_LOG.get());
    }
}
