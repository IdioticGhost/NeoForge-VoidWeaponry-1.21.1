package net.idioticghost.voidweaponry.worldgen.misc;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

import java.util.function.BiConsumer;

public class AshenTrunkFeature extends Feature<BlockStateConfiguration> {

    public AshenTrunkFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        BlockState state = context.config().state;


        while (level.isEmptyBlock(origin) && origin.getY() > level.getMinBuildHeight() + 1) {
            origin = origin.below();
        }

        if (!level.getBlockState(origin).isSolid()) {
            return false;
        }

        int height = 6 + random.nextInt(7);
        int baseWidth = 1 + height / 5;

        for (int y = 0; y < height; y++) {
            int layerRadius = Math.max(0, (int) ((1.0 - (y / (float) height)) * baseWidth));
            buildLayer(level, origin.above(y), state, layerRadius, random);
        }

        return true;
    }

    private void buildLayer(WorldGenLevel level, BlockPos center, BlockState state, int radius, RandomSource random) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (Math.abs(dx) + Math.abs(dz) <= radius) {
                    BlockPos pos = center.offset(dx, 0, dz);
                    if (level.isEmptyBlock(pos) && level.getBlockState(pos.below()).isSolid()) {
                        level.setBlock(pos, state, 2);

                        if (random.nextFloat() < 0.1f && radius > 0 && random.nextBoolean()) {
                            BlockPos top = pos.above();
                            if (level.isEmptyBlock(top)) {
                                level.setBlock(top, state, 2);
                            }
                        }
                    }
                }
            }
        }
    }
}