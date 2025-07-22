package net.idioticghost.voidweaponry.worldgen.plant;

import com.mojang.serialization.Codec;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

public class DeadCoralTreeFeature extends Feature<NoneFeatureConfiguration> {

    private static final Block[] DEAD_CORALS = new Block[]{
            Blocks.DEAD_BRAIN_CORAL_BLOCK,
            Blocks.DEAD_TUBE_CORAL_BLOCK,
            Blocks.DEAD_BUBBLE_CORAL_BLOCK,
            Blocks.DEAD_FIRE_CORAL_BLOCK,
            Blocks.DEAD_HORN_CORAL_BLOCK
    };

    public DeadCoralTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    private static BlockState randomDeadCoral(RandomSource random) {
        return DEAD_CORALS[random.nextInt(DEAD_CORALS.length)].defaultBlockState();
    }

    private boolean placeCoralBlock(LevelAccessor level, RandomSource random, BlockPos pos, BlockState coralState) {
        if (!level.isEmptyBlock(pos)) {
            return false;
        }
        level.setBlock(pos, coralState, 2);
        return true;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        LevelAccessor level = ctx.level();
        BlockPos pos = ctx.origin();
        RandomSource random = ctx.random();

        BlockPos blockBelow = pos.below();
        if (level.getBlockState(blockBelow).getBlock() != ModBlocks.ENDSTONE_SAND_BLOCK.get()) {
            return false;
        }

        BlockState coralBlock = randomDeadCoral(random);
        BlockPos.MutableBlockPos mutablePos = pos.mutable();

        int height = random.nextInt(3) + 1;

        for (int i = 0; i < height; i++) {
            if (!placeCoralBlock(level, random, mutablePos, coralBlock)) {
                return true;
            }
            mutablePos.move(Direction.UP);
        }

        BlockPos topPos = mutablePos.immutable();

        int branchesCount = random.nextInt(3) + 2; // 2 to 4 branches
        List<Direction> directions = Direction.Plane.HORIZONTAL.shuffledCopy(random);

        for (Direction direction : directions.subList(0, branchesCount)) {
            mutablePos.set(topPos);
            mutablePos.move(direction);

            int branchLength = random.nextInt(5) + 2;
            int verticalOffsetCounter = 0;

            for (int j = 0; j < branchLength; j++) {
                if (!placeCoralBlock(level, random, mutablePos, coralBlock)) {
                    break;
                }
                verticalOffsetCounter++;
                mutablePos.move(Direction.UP);

                if (j == 0 || (verticalOffsetCounter >= 2 && random.nextFloat() < 0.25F)) {
                    mutablePos.move(direction);
                    verticalOffsetCounter = 0;
                }
            }
        }

        return true;
    }
}