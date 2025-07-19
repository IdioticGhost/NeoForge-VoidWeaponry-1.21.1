package net.idioticghost.voidweaponry.worldgen.plant;

import com.mojang.serialization.Codec;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VoidKelpFeature extends Feature<NoneFeatureConfiguration> {

    private static final int MIN_HEIGHT = 2;
    private static final int MAX_HEIGHT = 6;

    public VoidKelpFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        // Find the ground position (like bamboo does)
        BlockPos ground = findGround(level, origin);
        if (ground == null) {
            return false;
        }

        // Check if block above ground is free to start placing kelp
        BlockPos start = ground.above();
        if (!level.isEmptyBlock(start)) {
            return false;
        }

        // Random height between MIN and MAX
        int height = MIN_HEIGHT + random.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1);

        // Place stalk blocks
        for (int i = 0; i < height - 1; i++) {
            BlockPos pos = start.above(i);
            if (level.isEmptyBlock(pos)) {
                BlockState kelpStalk = ModBlocks.VOID_KELP_CROP.get().defaultBlockState();
                level.setBlock(pos, kelpStalk, 2);
            } else {
                // Stop if obstructed mid-way
                if (i == 0) return false;
                break;
            }
        }

        // Place top block
        BlockPos top = start.above(height - 1);
        if (level.isEmptyBlock(top)) {
            BlockState kelpTop = ModBlocks.VOID_KELP_TOP.get().defaultBlockState();
            level.setBlock(top, kelpTop, 2);
        }

        return true;
    }

    /**
     * Find the first non-air block below the origin, suitable for kelp.
     * We want sand, dirt, or stone-like surfaces (customize here).
     */
    private BlockPos findGround(WorldGenLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().set(pos);

        while (mutable.getY() > level.getMinBuildHeight() + 1 && level.isEmptyBlock(mutable)) {
            mutable.move(0, -1, 0);
        }

        BlockState stateBelow = level.getBlockState(mutable);
        if (stateBelow.is(Blocks.SAND) || stateBelow.is(Blocks.RED_SAND) || stateBelow.is(Blocks.STONE)) {
            return mutable;
        }

        return null;
    }
}