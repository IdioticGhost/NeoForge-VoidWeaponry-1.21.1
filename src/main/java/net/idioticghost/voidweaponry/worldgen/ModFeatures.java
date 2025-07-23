package net.idioticghost.voidweaponry.worldgen;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.worldgen.foliage.ShadowPineFoliagePlacer;
import net.idioticghost.voidweaponry.worldgen.misc.*;
import net.idioticghost.voidweaponry.worldgen.plant.*;
import net.idioticghost.voidweaponry.worldgen.trunk.ShadowPineTrunkPlacer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;


public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, VoidWeaponry.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> VOID_KELP =
            FEATURES.register("void_kelp", () -> new VoidKelpFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> VOID_SEAGRASS =
            FEATURES.register("void_seagrass", () -> new VoidSeagrassFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<BlockStateConfiguration>> OBSIDIAN_SPIKE =
            FEATURES.register("obsidian_spike", () -> new ObsidianSpikeFeature(BlockStateConfiguration.CODEC));

    //TRUNK & LEAVES

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, VoidWeaponry.MOD_ID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<ShadowPineTrunkPlacer>> SHADOW_PINE_TRUNK_PLACER =
            TRUNK_PLACERS.register("shadow_pine_trunk_placer",
                    () -> new TrunkPlacerType<>(ShadowPineTrunkPlacer.CODEC));

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS =
            DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE, VoidWeaponry.MOD_ID);

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<ShadowPineFoliagePlacer>> SHADOW_PINE_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("shadow_pine_foliage_placer",
                    () -> new FoliagePlacerType<>(ShadowPineFoliagePlacer.CODEC));

    //CORAL

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> DEAD_CORAL_TREE =
            FEATURES.register("dead_coral_tree", () -> new DeadCoralTreeFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> DEAD_CORAL_CLAW =
            FEATURES.register("dead_coral_claw", () -> new DeadCoralClawFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> DEAD_CORAL_MUSHROOM =
            FEATURES.register("dead_coral_mushroom", () -> new DeadCoralMushroomFeature(NoneFeatureConfiguration.CODEC));


    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SMOOTH_ENDSTONE_PATCH =
            FEATURES.register("smooth_endstone_patch", () -> new SmoothEndstonePatchFeature(NoneFeatureConfiguration.CODEC,
                    ModBlocks.SMOOTH_ENDSTONE.get().defaultBlockState(),
                    ModBlocks.ENDSTONE_SAND_BLOCK.get().defaultBlockState()));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> RANDOM_CORAL_FEATURE =
            FEATURES.register("random_coral", () -> new RandomCoralFeature(NoneFeatureConfiguration.CODEC, List.of(
                    Blocks.DEAD_BRAIN_CORAL_FAN,
                    Blocks.DEAD_FIRE_CORAL_FAN,
                    Blocks.DEAD_BUBBLE_CORAL_FAN,
                    Blocks.DEAD_HORN_CORAL_FAN,
                    Blocks.DEAD_TUBE_CORAL_FAN,
                    Blocks.DEAD_BRAIN_CORAL_WALL_FAN,
                    Blocks.DEAD_FIRE_CORAL_WALL_FAN,
                    Blocks.DEAD_BUBBLE_CORAL_WALL_FAN,
                    Blocks.DEAD_HORN_CORAL_WALL_FAN,
                    Blocks.DEAD_TUBE_CORAL_WALL_FAN
            ), 10));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> DEAD_GRASS =
            FEATURES.register("dead_grass", () -> new DeadGrassFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SAND_ROTATION =
            FEATURES.register("sand_rotation", () -> new EndStoneSandRotationFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> STAR_BERRY =
            FEATURES.register("star_berry", () -> new StarBerryBushFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> ROTATED_DIRT_LEAF =
            FEATURES.register("rotated_dirt_leaf", () -> new DirtLeafRotationFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> FIREFLY_GRASS =
            FEATURES.register("firefly_grass", () -> new FireflyGrassFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> ROTATED_HARDENED_DIRT =
            FEATURES.register("rotated_hardened_dirt", () -> new HardenedDirtRotationFeature(NoneFeatureConfiguration.CODEC));

}