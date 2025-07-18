package net.idioticghost.voidweaponry.block.custom;


import com.mojang.serialization.MapCodec;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.checkerframework.checker.nullness.qual.Nullable;

public class VoidKelpTopBlock extends GrowingPlantHeadBlock {

    public static final MapCodec<VoidKelpTopBlock> CODEC = simpleCodec(VoidKelpTopBlock::new);
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 9, 16);

    public VoidKelpTopBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.14);
    }

    @Override
    public MapCodec<VoidKelpTopBlock> codec() {
        return CODEC;
    }

    // No water required for placement
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context);
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir(); // Can grow into air instead of water
    }

    @Override
    protected Block getBodyBlock() {
        return ModBlocks.VOID_KELP_CROP.get(); // your stem block
    }

    @Override
    protected boolean canAttachTo(BlockState state) {
        return state.is(ModBlocks.ENDSTONE_SAND_BLOCK.get()) || state.is(ModBlocks.VOID_KELP_TOP.get()) || state.is(ModBlocks.VOID_KELP_CROP.get());
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return 1; // One block per bonemeal
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return Fluids.EMPTY.defaultFluidState(); // ✅ not waterlogged
    }
}