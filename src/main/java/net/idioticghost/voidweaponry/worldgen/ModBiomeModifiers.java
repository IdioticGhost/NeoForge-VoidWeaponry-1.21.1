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

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_VOID_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.VOID_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_SHADOW_PINE_TREE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS)),
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
                HolderSet.direct(biomes.getOrThrow(ModBiomes.DRIED_DEPTHS)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.NAUTILUS_SHELL_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, name));
    }
}