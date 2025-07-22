package net.idioticghost.voidweaponry.worldgen.plant;

import com.mojang.serialization.Codec;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.minecraft.Util;
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
import java.util.stream.Stream;

public class DeadCoralClawFeature extends Feature<NoneFeatureConfiguration> {

    private static final Block[] DEAD_CORALS = new Block[]{
            Blocks.DEAD_BRAIN_CORAL_BLOCK,
            Blocks.DEAD_TUBE_CORAL_BLOCK,
            Blocks.DEAD_BUBBLE_CORAL_BLOCK,
            Blocks.DEAD_FIRE_CORAL_BLOCK,
            Blocks.DEAD_HORN_CORAL_BLOCK
    };

    public DeadCoralClawFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    private static BlockState randomDeadCoral(RandomSource random) {
        return DEAD_CORALS[random.nextInt(DEAD_CORALS.length)].defaultBlockState();
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos pos = ctx.origin();
        RandomSource random = ctx.random();

        Block blockBelow = level.getBlockState(pos.below()).getBlock();
        if (blockBelow != ModBlocks.ENDSTONE_SAND_BLOCK.get()) {
            return false;
        }

        if (!placeCoralBlock(level, random, pos, randomDeadCoral(random))) {
            return false;
        }
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);

        int i = random.nextInt(2) + 2;

        List<Direction> directions = Util.toShuffledList(
                Stream.of(direction, direction.getClockWise(), direction.getCounterClockWise()),
                random
        );

        for (Direction dir : directions.subList(0, i)) {
            BlockPos.MutableBlockPos mutablePos = pos.mutable();
            int j = random.nextInt(2) + 1;
            mutablePos.move(dir);
            int k;
            Direction moveDir;

            if (dir == direction) {
                moveDir = direction;
                k = random.nextInt(3) + 2;
            } else {
                mutablePos.move(Direction.UP);
                Direction[] options = new Direction[] {dir, Direction.UP};
                moveDir = Util.getRandom(options, random);
                k = random.nextInt(3) + 3;
            }

            for (int l = 0; l < j && placeCoralBlock(level, random, mutablePos, randomDeadCoral(random)); ++l) {
                mutablePos.move(moveDir);
            }

            mutablePos.move(moveDir.getOpposite());
            mutablePos.move(Direction.UP);

            for (int i1 = 0; i1 < k; ++i1) {
                mutablePos.move(direction);
                if (!placeCoralBlock(level, random, mutablePos, randomDeadCoral(random))) {
                    break;
                }
                if (random.nextFloat() < 0.25F) {
                    mutablePos.move(Direction.UP);
                }
            }
        }

        return true;
    }

    private boolean placeCoralBlock(WorldGenLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (level.isEmptyBlock(pos) || level.getBlockState(pos).is(Blocks.WATER)) {
            level.setBlock(pos, state, 2);
            return true;
        }
        return false;
    }
}