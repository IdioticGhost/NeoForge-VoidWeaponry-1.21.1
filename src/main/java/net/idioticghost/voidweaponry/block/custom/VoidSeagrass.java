package net.idioticghost.voidweaponry.block.custom;

import com.mojang.serialization.MapCodec;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class VoidSeagrass extends BushBlock {

    public static final MapCodec<VoidSeagrass> CODEC = simpleCodec(VoidSeagrass::new);

    public VoidSeagrass(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public VoidSeagrass() {
        this(BlockBehaviour.Properties.of()
                .noCollission()
                .instabreak()
                .sound(SoundType.WET_GRASS)
                .lightLevel(state -> 0)
                .noOcclusion()
        );
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(ModBlocks.ENDSTONE_SAND_BLOCK.get());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.EMPTY.defaultFluidState();
    }
}