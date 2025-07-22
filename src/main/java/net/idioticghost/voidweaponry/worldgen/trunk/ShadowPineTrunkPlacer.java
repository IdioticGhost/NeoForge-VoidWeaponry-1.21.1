package net.idioticghost.voidweaponry.worldgen.trunk;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.worldgen.ModFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class ShadowPineTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<ShadowPineTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            trunkPlacerParts(instance).apply(instance, ShadowPineTrunkPlacer::new)
    );

    public ShadowPineTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModFeatures.SHADOW_PINE_TRUNK_PLACER.value();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            int height,
            BlockPos startPos,
            TreeConfiguration config) {

        setDirtAt(level, blockSetter, random, startPos.below(), config);

        // --- Main trunk with alternating gnarled logs ---
        for (int y = 0; y < height; y++) {
            BlockPos pos = startPos.above(y);

            BlockState logState;
            if (y % 3 == 0) {
                logState = random.nextFloat() < 0.5F
                        ? Blocks.ACACIA_WOOD.defaultBlockState()
                        : Blocks.TUFF.defaultBlockState();
            } else {
                logState = Blocks.ACACIA_WOOD.defaultBlockState();
            }

            if (level.isStateAtPosition(pos, state -> state.canBeReplaced())) {
                blockSetter.accept(pos, logState);
            }

            // --- Random voidgrowth fence between 7 and 12 blocks up ---
            if (y >= 7 && y <= 12 && random.nextFloat() < 0.25F) { // 25% chance
                Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                BlockPos fencePos = pos.relative(dir);

                if (level.isStateAtPosition(fencePos, state -> state.canBeReplaced())) {
                    blockSetter.accept(fencePos, Blocks.TUFF_WALL.defaultBlockState());
                }
            }
        }

        // --- Gnarled base structures around trunk ---
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            if (random.nextFloat() < 0.5F) {
                int stackHeight = 1 + random.nextInt(3);
                BlockPos basePos = startPos.relative(dir);

                for (int i = 0; i < stackHeight; i++) {
                    BlockPos gnarledPos = basePos.above(i);
                    BlockPos belowPos = gnarledPos.below();
                    boolean isBelowSolid = level.isStateAtPosition(belowPos, state -> state.isSolid());

                    if (level.isStateAtPosition(gnarledPos, state -> state.canBeReplaced()) && isBelowSolid) {
                        BlockState blockToPlace;
                        if (random.nextFloat() < 0.5F) { // 50% chance
                            blockToPlace = Blocks.TUFF.defaultBlockState();
                        } else {
                            blockToPlace = Blocks.ACACIA_WOOD.defaultBlockState();
                        }
                        blockSetter.accept(gnarledPos, blockToPlace);
                    }
                }

                // Slab cap
                BlockPos slabPos = basePos.above(stackHeight);
                boolean slabCanPlace = level.isStateAtPosition(slabPos, state -> state.isAir() || state.canBeReplaced())
                        && !level.isStateAtPosition(slabPos.below(), state -> state.isAir());

                if (slabCanPlace && random.nextFloat() < 0.5F) {
                    blockSetter.accept(slabPos, Blocks.TUFF_SLAB.defaultBlockState());
                }
            }
        }

        return List.of(new FoliagePlacer.FoliageAttachment(startPos.above(height), 0, false));
    }
}