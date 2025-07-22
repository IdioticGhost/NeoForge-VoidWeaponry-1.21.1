package net.idioticghost.voidweaponry.worldgen.misc;

import com.mojang.serialization.Codec;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.block.custom.EndStoneSand;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EndStoneSandRotationFeature extends Feature<NoneFeatureConfiguration> {

    public EndStoneSandRotationFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        // Scan a volume around origin to find your sand blocks
        int radius = 8; // or your preferred range
        boolean placedAny = false;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);
                    if (state.is(ModBlocks.ENDSTONE_SAND_BLOCK.get())) {
                        Direction randomFacing = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                        BlockState rotated = state.setValue(EndStoneSand.FACING, randomFacing);
                        level.setBlock(pos, rotated, 2);
                        placedAny = true;
                    }
                }
            }
        }

        return placedAny;
    }
}