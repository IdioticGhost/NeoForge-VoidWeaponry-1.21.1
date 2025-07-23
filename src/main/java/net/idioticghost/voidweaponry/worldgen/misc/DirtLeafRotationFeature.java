package net.idioticghost.voidweaponry.worldgen.misc;

import com.mojang.serialization.Codec;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.block.custom.DirtLeafBlock;
import net.idioticghost.voidweaponry.block.custom.EndStoneSand;
import net.idioticghost.voidweaponry.block.custom.HardenedDirtBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DirtLeafRotationFeature extends Feature<NoneFeatureConfiguration> {

    public DirtLeafRotationFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int radius = 8;
        int maxReplacements = 10;   // Limit replacements
        int replacements = 0;
        boolean placedAny = false;

        for (int dx = -radius; dx <= radius && replacements < maxReplacements; dx++) {
            for (int dy = -radius; dy <= radius && replacements < maxReplacements; dy++) {
                for (int dz = -radius; dz <= radius && replacements < maxReplacements; dz++) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);

                    // 5% chance to replace
                    if (state.is(ModBlocks.HARDENED_DIRT_BLOCK.get()) && random.nextFloat() < 0.05f) {
                        Direction randomFacing = Direction.Plane.HORIZONTAL.getRandomDirection(random);

                        BlockState rotated = ModBlocks.DIRT_LEAF_BLOCK.get()
                                .defaultBlockState()
                                .setValue(DirtLeafBlock.FACING, randomFacing);

                        level.setBlock(pos, rotated, 2);
                        replacements++;
                        placedAny = true;
                    }
                }
            }
        }

        return placedAny;
    }
}