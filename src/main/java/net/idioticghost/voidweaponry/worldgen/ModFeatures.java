package net.idioticghost.voidweaponry.worldgen;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.worldgen.misc.ObsidianSpikeFeature;
import net.idioticghost.voidweaponry.worldgen.plant.VoidKelpFeature;
import net.idioticghost.voidweaponry.worldgen.plant.VoidSeagrassFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, VoidWeaponry.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> VOID_KELP =
            FEATURES.register("void_kelp", () -> new VoidKelpFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> VOID_SEAGRASS =
            FEATURES.register("void_seagrass", () -> new VoidSeagrassFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<BlockStateConfiguration>> OBSIDIAN_SPIKE =
            FEATURES.register("obsidian_spike", () -> new ObsidianSpikeFeature(BlockStateConfiguration.CODEC));
}