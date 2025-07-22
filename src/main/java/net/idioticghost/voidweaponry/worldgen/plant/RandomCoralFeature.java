package net.idioticghost.voidweaponry.worldgen.plant;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

public class RandomCoralFeature extends Feature<NoneFeatureConfiguration> {

    private final List<Block> coralVariants;
    private final int triesPerFeature;

    public RandomCoralFeature(Codec<NoneFeatureConfiguration> codec, List<Block> coralVariants, int triesPerFeature) {
        super(codec);
        this.coralVariants = coralVariants;
        this.triesPerFeature = triesPerFeature;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        boolean placedAny = false;

        for (int i = 0; i < triesPerFeature; i++) {
            int dx = random.nextInt(7) - 3;
            int dy = random.nextInt(7) - 3;
            int dz = random.nextInt(7) - 3;
            BlockPos pos = origin.offset(dx, dy, dz);

            if (!world.isEmptyBlock(pos)) continue;

            Block coralBlock = coralVariants.get(random.nextInt(coralVariants.size()));

            BlockState stateToPlace;

            // Handle wall fans
            if (coralBlock == Blocks.DEAD_BRAIN_CORAL_WALL_FAN || coralBlock == Blocks.DEAD_BUBBLE_CORAL_WALL_FAN
                    || coralBlock == Blocks.DEAD_FIRE_CORAL_WALL_FAN || coralBlock == Blocks.DEAD_HORN_CORAL_WALL_FAN
                    || coralBlock == Blocks.DEAD_TUBE_CORAL_WALL_FAN) {

                Direction[] horizontalDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                boolean placed = false;

                for (Direction dir : horizontalDirections) {
                    BlockPos neighborPos = pos.relative(dir.getOpposite());
                    if (world.getBlockState(neighborPos).isSolidRender(world, neighborPos)) {
                        stateToPlace = coralBlock.defaultBlockState()
                                .setValue(net.minecraft.world.level.block.CoralWallFanBlock.FACING, dir)
                                .setValue(net.minecraft.world.level.block.CoralWallFanBlock.WATERLOGGED, false);
                        world.setBlock(pos, stateToPlace, 2);
                        placedAny = true;
                        placed = true;
                        break;
                    }
                }

                if (!placed) continue;

            } else {
                // Regular coral blocks or fans
                stateToPlace = coralBlock.defaultBlockState();
                if (stateToPlace.hasProperty(net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED)) {
                    stateToPlace = stateToPlace.setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED, false);
                }

                BlockPos below = pos.below();
                if (world.getBlockState(below).isSolidRender(world, below)) {
                    world.setBlock(pos, stateToPlace, 2);
                    placedAny = true;
                }
            }
        }

        return placedAny;
    }
}