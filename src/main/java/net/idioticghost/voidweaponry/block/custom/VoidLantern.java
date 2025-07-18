package net.idioticghost.voidweaponry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class VoidLantern extends LanternBlock {
    public static final BooleanProperty CLICKED = BooleanProperty.create("clicked");

    public VoidLantern(Properties properties) {
        super(properties.lightLevel(state -> state.getValue(CLICKED) ? 15 : 0));
        this.registerDefaultState(this.defaultBlockState()
                .setValue(CLICKED, false)
                .setValue(HANGING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CLICKED); // Add your custom property
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // Always place on the ground (not hanging)
        BlockState baseState = super.getStateForPlacement(context);
        return baseState != null
                ? baseState.setValue(HANGING, false).setValue(CLICKED, false)
                : null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        // Only allow placement on top of solid blocks (like lantern on ground)
        return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                            Player player, BlockHitResult hit) {
        if (!level.isClientSide) {
            boolean clicked = state.getValue(CLICKED);
            level.setBlock(pos, state.setValue(CLICKED, !clicked), 3);
        }
        return InteractionResult.SUCCESS;
    }
}