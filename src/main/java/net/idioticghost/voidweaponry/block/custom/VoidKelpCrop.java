package net.idioticghost.voidweaponry.block.custom;

import net.idioticghost.voidweaponry.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.KelpPlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class VoidKelpCrop extends KelpPlantBlock {

    public VoidKelpCrop() {
        super(Properties.of()
                .noCollission()
                .instabreak()
                .sound(SoundType.WET_GRASS)
                .lightLevel(state -> 0)
                .noOcclusion()
                .randomTicks() // if you want it to grow
        );
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!canSurvive(state, level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        // If the block above is gone and this is now the top → become top block
        if (direction == Direction.UP && neighborState.isAir()) {
            // Replace this block with the head/top block
            level.setBlock(pos, net.idioticghost.voidweaponry.block.ModBlocks.VOID_KELP_TOP.get().defaultBlockState(), 3);
            return net.idioticghost.voidweaponry.block.ModBlocks.VOID_KELP_TOP.get().defaultBlockState();
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }


    // Prevent automatic fluid placement
    @Override
    protected FluidState getFluidState(BlockState state) {
        return Fluids.EMPTY.defaultFluidState();
    }

    // Only allow growing on itself or your custom block
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return below.is(net.idioticghost.voidweaponry.block.ModBlocks.VOID_KELP_CROP.get()) || below.is(net.idioticghost.voidweaponry.block.ModBlocks.VOID_KELP_TOP.get()) || below.is(ModBlocks.ENDSTONE_SAND_BLOCK.get());
    }


    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) ModBlocks.VOID_KELP_TOP.get();
    }



}

