package net.idioticghost.voidweaponry.worldgen;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.worldgen.foliage.ShadowPineFoliagePlacer;
import net.idioticghost.voidweaponry.worldgen.trunk.ShadowPineTrunkPlacer;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;


import java.util.List;

import static net.idioticghost.voidweaponry.worldgen.ModPlacedFeatures.OBSIDIAN_SPIKE_PLACED_KEY;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> VOID_ORE_KEY = registerKey("void_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_PINE_KEY = registerKey("shadow_pine");

    public static final ResourceKey<ConfiguredFeature<?, ?>> VOID_KELP_KEY = registerKey("void_kelp");

    public static final ResourceKey<ConfiguredFeature<?, ?>> VOID_SEAGRASS_KEY = registerKey("void_seagrass");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OBSIDIAN_SPIKE_KEY = registerKey("obsidian_spike_key");

    public static final ResourceKey<ConfiguredFeature<?, ?>> NAUTILUS_SHELL_KEY = registerKey("nautilus_shell");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_CORAL_TREE_KEY = registerKey("dead_coral_tree");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_CORAL_CLAW_KEY = registerKey("dead_coral_claw");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_CORAL_MUSHROOM_KEY = registerKey("dead_coral_mushroom");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SMOOTH_ENDSTONE_PATCH_KEY = registerKey("smooth_endstone_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_CORAL_KEY = registerKey("random_coral");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_GRASS_KEY = registerKey("dead_grass");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ROTATED_SAND_KEY = registerKey("rotated_sand");

    public static final ResourceKey<ConfiguredFeature<?, ?>> STAR_BERRY_KEY = registerKey("star_berry_key");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ROTATED_DIRT_LEAF_KEY = registerKey("rotated_dirt_leaf");

    public static final ResourceKey<ConfiguredFeature<?, ?>> FIREFLY_GRASS_KEY = registerKey("firefly_grass_key");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ROTATED_HARDENED_DIRT_KEY = registerKey("rotated_hardened_dirt_key");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest voidReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> voidOres = List.of(
                OreConfiguration.target(voidReplaceables, ModBlocks.VOID_ORE.get().defaultBlockState()));

        List<Block> replaceableBlocks = List.of(
                ModBlocks.ENDSTONE_SAND_BLOCK.get(),
                ModBlocks.VOID_SEAGRASS.get()
        );

        register(context, VOID_ORE_KEY, Feature.ORE, new OreConfiguration(voidOres, 8));


        register(context, SHADOW_PINE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.SHADOW_PINE_LOG.get()),
                new ShadowPineTrunkPlacer(15, 1, 2),
                BlockStateProvider.simple(ModBlocks.SHADOW_PINE_LEAVES.get()),
                new ShadowPineFoliagePlacer(
                        ConstantInt.of(2), //RADIUS OF LEAVES
                        ConstantInt.of(0),
                        6),
                new TwoLayersFeatureSize(1, 0, 2)
        ).build());

        context.register(
                VOID_KELP_KEY,
                new ConfiguredFeature<>(ModFeatures.VOID_KELP.get(), NoneFeatureConfiguration.INSTANCE));

        context.register(
                VOID_SEAGRASS_KEY,
                new ConfiguredFeature<>(ModFeatures.VOID_SEAGRASS.get(), NoneFeatureConfiguration.INSTANCE));

        context.register(OBSIDIAN_SPIKE_KEY,
                new ConfiguredFeature<>(
                        ModFeatures.OBSIDIAN_SPIKE.get(),
                        new BlockStateConfiguration(Blocks.OBSIDIAN.defaultBlockState())
                )
        );

        register(context, NAUTILUS_SHELL_KEY, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(randomHorizontalFacing(ModBlocks.NAUTILUS_SHELL_BLOCK.get())),
                        List.of(ModBlocks.ENDSTONE_SAND_BLOCK.get())
                ));

        context.register(DEAD_CORAL_TREE_KEY,
                new ConfiguredFeature<>(
                        ModFeatures.DEAD_CORAL_TREE.get(),
                        NoneFeatureConfiguration.INSTANCE));

        context.register(DEAD_CORAL_CLAW_KEY,
                new ConfiguredFeature<>(
                        ModFeatures.DEAD_CORAL_CLAW.get(),
                        NoneFeatureConfiguration.INSTANCE));

        context.register(DEAD_CORAL_MUSHROOM_KEY,
                new ConfiguredFeature<>(
                        ModFeatures.DEAD_CORAL_MUSHROOM.get(),
                        NoneFeatureConfiguration.INSTANCE));


        context.register(
                SMOOTH_ENDSTONE_PATCH_KEY,
                new ConfiguredFeature<>(
                        ModFeatures.SMOOTH_ENDSTONE_PATCH.get(),
                        NoneFeatureConfiguration.INSTANCE
                )
        );

        context.register(RANDOM_CORAL_KEY,
                new ConfiguredFeature<>(ModFeatures.RANDOM_CORAL_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));

        context.register(
                DEAD_GRASS_KEY,
                new ConfiguredFeature<>(ModFeatures.DEAD_GRASS.get(), NoneFeatureConfiguration.INSTANCE));

        context.register(
                ROTATED_SAND_KEY,
                new ConfiguredFeature<>(
                        ModFeatures.SAND_ROTATION.get(),
                        NoneFeatureConfiguration.INSTANCE)
        );

        context.register(
                STAR_BERRY_KEY,
                new ConfiguredFeature<>(
                        ModFeatures.STAR_BERRY.get(),
                        NoneFeatureConfiguration.INSTANCE)
        );

        context.register(
                ROTATED_DIRT_LEAF_KEY,
                new ConfiguredFeature<>(
                        ModFeatures.ROTATED_DIRT_LEAF.get(),
                        NoneFeatureConfiguration.INSTANCE)
        );

        context.register(
                FIREFLY_GRASS_KEY,
                new ConfiguredFeature<>(
                        ModFeatures.FIREFLY_GRASS.get(),
                        NoneFeatureConfiguration.INSTANCE)
        );

        context.register(
                ROTATED_HARDENED_DIRT_KEY,
                new ConfiguredFeature<>(
                        ModFeatures.ROTATED_HARDENED_DIRT.get(),
                        NoneFeatureConfiguration.INSTANCE)
        );
    }


    private static BlockStateProvider randomHorizontalFacing(net.minecraft.world.level.block.Block block) {
        SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            builder.add(block.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, dir), 1);
        }
        return new WeightedStateProvider(builder.build());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}