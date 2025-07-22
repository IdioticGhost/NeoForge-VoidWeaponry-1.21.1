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

public class DeadCoralMushroomFeature extends Feature<NoneFeatureConfiguration> {

    private static final Block[] DEAD_CORALS = new Block[]{
            Blocks.DEAD_BRAIN_CORAL_BLOCK,
            Blocks.DEAD_TUBE_CORAL_BLOCK,
            Blocks.DEAD_BUBBLE_CORAL_BLOCK,
            Blocks.DEAD_FIRE_CORAL_BLOCK,
            Blocks.DEAD_HORN_CORAL_BLOCK
    };

    public DeadCoralMushroomFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    private static BlockState randomDeadCoral(RandomSource random) {
        return DEAD_CORALS[random.nextInt(DEAD_CORALS.length)].defaultBlockState();
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        RandomSource random = ctx.random();

        // Ensure block below is EndstoneSand
        if (level.getBlockState(origin.below()).getBlock() != ModBlocks.ENDSTONE_SAND_BLOCK.get()) {
            return false;
        }

        int i = random.nextInt(3) + 3;
        int j = random.nextInt(3) + 3;
        int k = random.nextInt(3) + 3;
        int l = random.nextInt(3) + 1;

        BlockPos.MutableBlockPos mutablePos = origin.mutable();

        BlockState coralState = randomDeadCoral(random);

        for (int x = 0; x <= j; ++x) {
            for (int y = 0; y <= i; ++y) {
                for (int z = 0; z <= k; ++z) {
                    mutablePos.set(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
                    mutablePos.move(Direction.DOWN, l);

                    // The complicated condition from vanilla coral mushroom feature to create the shape
                    boolean onEdgeX = x == 0 || x == j;
                    boolean onEdgeY = y == 0 || y == i;
                    boolean onEdgeZ = z == 0 || z == k;

                    boolean insideX = x != 0 && x != j;
                    boolean insideY = y != 0 && y != i;
                    boolean insideZ = z != 0 && z != k;

                    boolean condition = (
                            (insideX || insideY) && (insideZ || insideY) && (insideX || insideZ) &&
                                    (onEdgeX || onEdgeY || onEdgeZ) &&
                                    !(random.nextFloat() < 0.1F)
                    );

                    // Place coral block if the placeCoralBlock helper allows it
                    if (condition) {
                        placeCoralBlock(level, random, mutablePos, coralState);
                    }
                }
            }
        }

        return true;
    }

    private boolean placeCoralBlock(LevelAccessor level, RandomSource random, BlockPos pos, BlockState state) {
        // Allow placing coral if block is air or water
        if (level.isEmptyBlock(pos) || level.getBlockState(pos).is(Blocks.WATER)) {
            ((WorldGenLevel)level).setBlock(pos, state, 2);
            return true;
        }
        return false;
    }
}