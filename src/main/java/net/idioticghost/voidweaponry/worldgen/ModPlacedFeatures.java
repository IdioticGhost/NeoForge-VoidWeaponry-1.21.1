package net.idioticghost.voidweaponry.worldgen;


import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static net.idioticghost.voidweaponry.worldgen.ModConfiguredFeatures.OBSIDIAN_SPIKE_KEY;
import static net.idioticghost.voidweaponry.worldgen.ModConfiguredFeatures.ROTATED_HARDENED_DIRT_KEY;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> VOID_ORE_PLACED_KEY = registerKey("void_ore_placed");

    public static final ResourceKey<PlacedFeature> SHADOW_PINE_PLACED_KEY = registerKey("shadow_pine_placed");

    public static final ResourceKey<PlacedFeature> VOID_KELP_PLACED_KEY = registerKey("void_kelp_placed");

    public static final ResourceKey<PlacedFeature> VOID_SEAGRASS_PLACED_KEY = registerKey("void_seagrass_placed");

    public static final ResourceKey<PlacedFeature> OBSIDIAN_SPIKE_PLACED_KEY = registerKey("obsidian_spike_placed");

    public static final ResourceKey<PlacedFeature> NAUTILUS_SHELL_PLACED_KEY = registerKey("nautilus_shell_placed");

    public static final ResourceKey<PlacedFeature> DEAD_CORAL_TREE_PLACED_KEY = registerKey("dead_coral_tree_placed");

    public static final ResourceKey<PlacedFeature> DEAD_CORAL_CLAW_PLACED_KEY = registerKey("dead_coral_claw_placed");

    public static final ResourceKey<PlacedFeature> DEAD_CORAL_MUSHROOM_PLACED_KEY = registerKey("dead_coral_mushroom_placed");

    public static final ResourceKey<PlacedFeature> SMOOTH_ENDSTONE_PLACED_KEY = registerKey("smooth_endstone_placed");

    public static final ResourceKey<PlacedFeature> RANDOM_CORAL_PLACED_KEY = registerKey("random_coral_placed");

    public static final ResourceKey<PlacedFeature> DEAD_GRASS_PLACED_KEY = registerKey("dead_grass_placed");

    public static final ResourceKey<PlacedFeature> ROTATED_SAND_PLACED_KEY = registerKey("rotated_sand_placed");

    public static final ResourceKey<PlacedFeature> STAR_BERRY_PLACED_KEY = registerKey("star_berry_placed");

    public static final ResourceKey<PlacedFeature> ROTATED_DIRT_LEAF_PLACED_KEY = registerKey("rotated_dirt_leaf_placed");

    public static final ResourceKey<PlacedFeature> FIREFLY_GRASS_PLACED_KEY = registerKey("firefly_grass_placed");

    public static final ResourceKey<PlacedFeature> ROTATED_HARDENED_DIRT_PLACED_KEY = registerKey("rotated_hardened_dirt_key");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, VOID_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.VOID_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(14))));

        register(context, SHADOW_PINE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SHADOW_PINE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1f, 1),
                        ModBlocks.SHADOW_PINE_SAPLING.get()));


        register(context, VOID_KELP_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.VOID_KELP_KEY),
                List.of(
                        CountPlacement.of(7),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                ));

        register(context, VOID_SEAGRASS_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.VOID_SEAGRASS_KEY),
                List.of(
                        CountPlacement.of(14),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                ));

        context.register(OBSIDIAN_SPIKE_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.OBSIDIAN_SPIKE_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(1),  // Appears every ~2 chunks
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                                BiomeFilter.biome()
                        )
                )
        );

        register(context, NAUTILUS_SHELL_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.NAUTILUS_SHELL_KEY),
                List.of(
                        CountPlacement.of(1),
                        RarityFilter.onAverageOnceEvery(7),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                ));

        context.register(DEAD_CORAL_TREE_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.DEAD_CORAL_TREE_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(1),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                ));

        context.register(DEAD_CORAL_CLAW_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.DEAD_CORAL_CLAW_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(1),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                ));

        context.register(DEAD_CORAL_MUSHROOM_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.DEAD_CORAL_MUSHROOM_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(1),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                ));

        context.register(SMOOTH_ENDSTONE_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.SMOOTH_ENDSTONE_PATCH_KEY),
                        List.of(RarityFilter.onAverageOnceEvery(3),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                ));

        context.register(RANDOM_CORAL_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.RANDOM_CORAL_KEY),
                        List.of(
                                CountPlacement.of(10),            // Number per chunk
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                ));

        context.register(DEAD_GRASS_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.DEAD_GRASS_KEY),
                        List.of(
                                CountPlacement.of(12),            // Number per chunk
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                ));

        register(context, ROTATED_SAND_PLACED_KEY,
                context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModConfiguredFeatures.ROTATED_SAND_KEY),
                List.of(
                        CountPlacement.of(10),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );

        register(context, STAR_BERRY_PLACED_KEY,
                context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModConfiguredFeatures.STAR_BERRY_KEY),
                List.of(
                        CountPlacement.of(9),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );

        register(context, ROTATED_DIRT_LEAF_PLACED_KEY,
                context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModConfiguredFeatures.ROTATED_DIRT_LEAF_KEY),
                List.of(
                        CountPlacement.of(1),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );

        register(context, FIREFLY_GRASS_PLACED_KEY,
                context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModConfiguredFeatures.FIREFLY_GRASS_KEY),
                List.of(
                        CountPlacement.of(6),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );

        register(context, ROTATED_HARDENED_DIRT_PLACED_KEY,
                context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModConfiguredFeatures.ROTATED_HARDENED_DIRT_KEY),
                List.of(
                        CountPlacement.of(6),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );
    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}