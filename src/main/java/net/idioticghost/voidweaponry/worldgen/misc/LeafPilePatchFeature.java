package net.idioticghost.voidweaponry.worldgen.misc;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LeafPilePatchFeature extends Feature<NoneFeatureConfiguration> {

    private final BlockState patchBlockState;
    private final BlockState replaceableBlockState;

    public LeafPilePatchFeature(Codec<NoneFeatureConfiguration> codec, BlockState patchBlockState, BlockState replaceableBlockState) {
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
                    BlockPos surfacePos = getSurface(level, origin.offset(dx, 0, dz)).below();

                    if (surfacePos != null &&
                            level.getBlockState(surfacePos).is(replaceableBlockState.getBlock())) {

                        int depth = 3 + random.nextInt(2);

                        for (int dy = 0; dy < depth; dy++) {
                            BlockPos digPos = surfacePos.below(dy);

                            if (level.getBlockState(digPos).is(replaceableBlockState.getBlock())) {
                                level.setBlock(digPos, patchBlockState, 2);
                                placedAny = true;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return placedAny;
    }

    private BlockPos getSurface(WorldGenLevel level, BlockPos pos) {
        return level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos);
    }
}