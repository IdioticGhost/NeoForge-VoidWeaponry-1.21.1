package net.idioticghost.voidweaponry.datagen;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture,
                              CompletableFuture<TagLookup<Block>> lookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, lookupCompletableFuture, VoidWeaponry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.VOIDGOLD_HELMET.get())
                .add(ModItems.VOIDGOLD_CHESTPLATE.get())
                .add(ModItems.VOIDGOLD_LEGGINGS.get())
                .add(ModItems.VOIDGOLD_BOOTS.get())
                ;

        tag(ItemTags.LOGS)
                .add(ModBlocks.SHADOW_PINE_LOG.get().asItem())
                .add(ModBlocks.SHADOW_PINE_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_SHADOW_PINE_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_SHADOW_PINE_WOOD.get().asItem())
                ;
        tag(ItemTags.PLANKS)
                .add(ModBlocks.VOIDGROWTH_PLANKS.get().asItem());
    }
}