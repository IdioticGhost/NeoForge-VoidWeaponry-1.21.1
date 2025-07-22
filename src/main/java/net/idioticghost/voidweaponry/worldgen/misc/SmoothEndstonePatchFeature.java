package net.idioticghost.voidweaponry.worldgen.misc;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SmoothEndstonePatchFeature extends Feature<NoneFeatureConfiguration> {

    private final BlockState patchBlockState;
    private final BlockState replaceableBlockState;

    public SmoothEndstonePatchFeature(Codec<NoneFeatureConfiguration> codec, BlockState patchBlockState, BlockState replaceableBlockState) {
        super(codec);
        this.patchBlockState = patchBlockState;
        this.replaceableBlockState = replaceableBlockState;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int radius = 4;
        boolean placedAny = false;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz <= radius * radius) {
                    BlockPos pos = origin.offset(dx, 0, dz);

                    BlockPos topPos = findTopBlock(level, pos);

                    if (topPos != null && level.getBlockState(topPos).equals(replaceableBlockState)) {
                        level.setBlock(topPos, patchBlockState, 2);
                        placedAny = true;
                    }
                }
            }
        }
        return placedAny;
    }

    private BlockPos findTopBlock(WorldGenLevel level, BlockPos pos) {
        for (int y = pos.getY() + 5; y >= 0; y--) {
            BlockPos checkPos = new BlockPos(pos.getX(), y, pos.getZ());
            if (level.getBlockState(checkPos).equals(replaceableBlockState)) {
                return checkPos;
            }
        }
        return null;
    }
}