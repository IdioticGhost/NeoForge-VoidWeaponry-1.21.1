package net.idioticghost.voidweaponry.worldgen.plant;

import com.mojang.serialization.Codec;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VoidSeagrassFeature extends Feature<NoneFeatureConfiguration> {

    public VoidSeagrassFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        // Try placing 4 seagrass blocks in a cluster
        int placed = 0;
        for (int i = 0; i < 8 && placed < 4; i++) {
            BlockPos offsetPos = origin.offset(
                    random.nextInt(3) - 1, // X offset: -1, 0, or +1
                    0,
                    random.nextInt(3) - 1  // Z offset: -1, 0, or +1
            );

            BlockPos ground = findGround(level, offsetPos);
            if (ground != null) {
                BlockPos placePos = ground.above();

                if (level.isEmptyBlock(placePos)) {
                    BlockState state = ModBlocks.VOID_SEAGRASS.get().defaultBlockState();
                    level.setBlock(placePos, state, 2);
                    placed++;
                }
            }
        }

        return placed > 0;
    }

    private BlockPos findGround(WorldGenLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().set(pos);

        while (mutable.getY() > level.getMinBuildHeight() + 1 && level.isEmptyBlock(mutable)) {
            mutable.move(0, -1, 0);
        }

        BlockState stateBelow = level.getBlockState(mutable);
        if (stateBelow.is(ModBlocks.ENDSTONE_SAND_BLOCK.get())) {
            return mutable;
        }

        return null;
    }
}