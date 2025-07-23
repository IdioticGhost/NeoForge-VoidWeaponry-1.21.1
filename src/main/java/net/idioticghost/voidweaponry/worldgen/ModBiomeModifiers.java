package net.idioticghost.voidweaponry.worldgen;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_VOID_ORE = registerKey("add_void_ore");

    public static final ResourceKey<BiomeModifier> ADD_SHADOW_PINE_TREE = registerKey("add_tree_shadow_pine");

    public static final ResourceKey<BiomeModifier> ADD_VOID_KELP = registerKey("add_void_kelp");

    public static final ResourceKey<BiomeModifier> ADD_VOID_SEAGRASS = registerKey("add_void_seagrass");

    public static final ResourceKey<BiomeModifier> ADD_NAUTILUS_SHELL = registerKey("add_nautilus_shell");

    public static final ResourceKey<BiomeModifier> ADD_DEAD_CORAL_TREE = registerKey("add_dead_coral_tree");

    public static final ResourceKey<BiomeModifier> ADD_DEAD_CORAL_CLAW = registerKey("add_dead_coral_claw");

    public static final ResourceKey<BiomeModifier> ADD_DEAD_CORAL_MUSHROOM = registerKey("add_dead_coral_mushroom");

    public static final ResourceKey<BiomeModifier> ADD_SMOOTH_ENDSTONE = registerKey("add_smooth_endstone");

    public static final ResourceKey<BiomeModifier> ADD_RANDOM_CORAL = registerKey("add_random_coral");

    public static final ResourceKey<BiomeModifier> ADD_DEAD_GRASS = registerKey("add_dead_grass");

    public static final ResourceKey<BiomeModifier> ADD_ROTATED_SAND = registerKey("add_rotated_sand");

    public static final ResourceKey<BiomeModifier> ADD_STAR_BERRY = registerKey("add_star_berry");

    public static final ResourceKey<BiomeModifier> ADD_DIRT_LEAF = registerKey("add_dirt_leaf");

    public static final ResourceKey<BiomeModifier> ADD_FIREFLY_GRASS = registerKey("add_firefly_grass");

    public static final ResourceKey<BiomeModifier> ADD_ROTATED_HARDENED_DIRT = registerKey("add_rotated_hardened_dirt");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_VOID_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.VOID_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_SHADOW_PINE_TREE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.SHROUDED_FOREST)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.SHADOW_PINE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_VOID_KELP, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.DRIED_DEPTHS)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.VOID_KELP_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_VOID_SEAGRASS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.DRIED_DEPTHS)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.VOID_SEAGRASS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_NAUTILUS_SHELL, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.DRIED_DEPTHS), biomes.getOrThrow(ModBiomes.CORAL_WASTES), biomes.getOrThrow(ModBiomes.FORGOTTEN_BEACH)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.NAUTILUS_SHELL_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_DEAD_CORAL_TREE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.CORAL_WASTES)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.DEAD_CORAL_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_DEAD_CORAL_CLAW, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.CORAL_WASTES)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.DEAD_CORAL_CLAW_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_DEAD_CORAL_MUSHROOM, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.CORAL_WASTES)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.DEAD_CORAL_MUSHROOM_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_SMOOTH_ENDSTONE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.CORAL_WASTES), biomes.getOrThrow(ModBiomes.DRIED_DEPTHS)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.SMOOTH_ENDSTONE_PLACED_KEY)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

        context.register(ADD_RANDOM_CORAL, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.CORAL_WASTES)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.RANDOM_CORAL_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_DEAD_GRASS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.CORAL_WASTES), biomes.getOrThrow(ModBiomes.FORGOTTEN_BEACH)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.DEAD_GRASS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_ROTATED_SAND, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.CORAL_WASTES), biomes.getOrThrow(ModBiomes.FORGOTTEN_BEACH), biomes.getOrThrow(ModBiomes.DRIED_DEPTHS)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.ROTATED_SAND_PLACED_KEY)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

        context.register(ADD_STAR_BERRY, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.SHROUDED_FOREST)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.STAR_BERRY_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_DIRT_LEAF, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.SHROUDED_FOREST)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.ROTATED_DIRT_LEAF_PLACED_KEY)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

        context.register(ADD_FIREFLY_GRASS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.SHROUDED_FOREST)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.FIREFLY_GRASS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_ROTATED_HARDENED_DIRT, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.SHROUDED_FOREST)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.ROTATED_HARDENED_DIRT_PLACED_KEY)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, name));
    }
}