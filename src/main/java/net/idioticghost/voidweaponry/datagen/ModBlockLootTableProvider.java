package net.idioticghost.voidweaponry.datagen;

import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.ENDSTONE_SAND_BLOCK.get());
        dropSelf(ModBlocks.SMOOTH_ENDSTONE.get());
        dropSelf(ModBlocks.VOID_KELP_BLOCK.get());
        dropSelf(ModBlocks.VOIDGROWTH_PLANKS.get());
        dropSelf(ModBlocks.VOIDGROWTH_STAIRS.get());
        this.add(ModBlocks.VOIDGROWTH_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.VOIDGROWTH_SLAB.get()));
        dropSelf(ModBlocks.VOIDGROWTH_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.VOIDGROWTH_BUTTON.get());
        dropSelf(ModBlocks.VOIDGROWTH_TRAPDOOR.get());
        this.add(ModBlocks.VOIDGROWTH_DOOR.get(),
                block -> createDoorTable(ModBlocks.VOIDGROWTH_DOOR.get()));
        dropSelf(ModBlocks.VOIDGROWTH_FENCE.get());
        dropSelf(ModBlocks.VOIDGROWTH_FENCE_GATE.get());

        dropSelf(ModBlocks.VOID_LANTERN.get());

        dropSelf(ModBlocks.VOID_CRAFTER.get());

        this.add(ModBlocks.VOID_ORE.get(),
                block -> createOreDrop(ModBlocks.VOID_ORE.get(), ModItems.VOID_SHARD.get()));

        this.add(ModBlocks.VOID_KELP_CROP.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.VOID_KELP.get()))
                        )
        );


        this.add(ModBlocks.VOID_KELP_TOP.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.VOID_KELP.get()))
                        )
        );

        this.add(ModBlocks.VOID_SEAGRASS.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.VOID_SEAGRASS_HELD.get()))
                        )
        );

        this.add(ModBlocks.DEAD_GRASS.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModBlocks.DEAD_GRASS.get()))
                        )
        );

        this.add(ModBlocks.SHORT_DEAD_GRASS.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModBlocks.SHORT_DEAD_GRASS.get()))
                        )
        );

        this.dropSelf(ModBlocks.SHADOW_PINE_LOG.get());
        this.dropSelf(ModBlocks.SHADOW_PINE_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_SHADOW_PINE_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_SHADOW_PINE_WOOD.get());
        this.dropSelf(ModBlocks.SHADOW_PINE_SAPLING.get());
        this.dropSelf(ModBlocks.GNARLED_SHADOW_PINE.get());



        this.add(ModBlocks.SHADOW_PINE_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.SHADOW_PINE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.add(ModBlocks.PURPLE_SHADOW_PINE_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.SHADOW_PINE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.add(ModBlocks.NAUTILUS_SHELL_BLOCK.get(),
                createSingleItemTable(Items.NAUTILUS_SHELL));
    }

    //THIS IS FOR GENERATING COPPER-ESQUE LOOT TABLES

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock, this.applyExplosionDecay(
                        pBlock, LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
