package net.idioticghost.voidweaponry.block.custom;

import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class StarBerryBushBlock extends CaveVinesBlock {
    public static final BooleanProperty BERRIES = CaveVines.BERRIES;

    public StarBerryBushBlock(Properties properties) {
        super(properties.sound(SoundType.CAVE_VINES));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(BERRIES, false));
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.STAR_BERRIES.get());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        boolean hasBerries = state.getValue(BERRIES);
        if (hasBerries) {
            int dropCount = 1;
            popResource(level, pos, new ItemStack(ModItems.STAR_BERRIES.get(), dropCount));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            BlockState newState = state.setValue(BERRIES, false).setValue(AGE, 1);
            level.setBlock(pos, newState, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (!state.getValue(BERRIES)) {
            level.setBlock(pos, state.setValue(BERRIES, true), 2);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader reader, BlockPos pos, BlockState state) {
        return !state.getValue(BERRIES);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        return above.getBlock() == ModBlocks.SHADOW_PINE_LEAVES.get() ||
                above.getBlock() == ModBlocks.PURPLE_SHADOW_PINE_LEAVES.get();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState above = level.getBlockState(pos.above());

        if (above.getBlock() == ModBlocks.SHADOW_PINE_LEAVES.get() ||
                above.getBlock() == ModBlocks.PURPLE_SHADOW_PINE_LEAVES.get()) {
            return this.defaultBlockState();
        }

        return null;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(BERRIES)) {
            if (random.nextInt(5) == 0) {
                level.setBlock(pos, state.setValue(BERRIES, true), 2);
            }
        }
    }
}