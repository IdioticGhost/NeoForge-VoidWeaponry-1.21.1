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

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> VOID_ORE_PLACED_KEY = registerKey("void_ore_placed");

    public static final ResourceKey<PlacedFeature> SHADOW_PINE_PLACED_KEY = registerKey("shadow_pine_placed");

    public static final ResourceKey<PlacedFeature> VOID_KELP_PLACED_KEY = registerKey("void_kelp_placed");

    public static final ResourceKey<PlacedFeature> VOID_SEAGRASS_PLACED_KEY = registerKey("void_seagrass_placed");

    public static final ResourceKey<PlacedFeature> OBSIDIAN_SPIKE_PLACED_KEY = registerKey("obsidian_spike_placed");

    public static final ResourceKey<PlacedFeature> NAUTILUS_SHELL_PLACED_KEY = registerKey("nautilus_shell_placed");

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
                        RarityFilter.onAverageOnceEvery(10),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                ));

    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}