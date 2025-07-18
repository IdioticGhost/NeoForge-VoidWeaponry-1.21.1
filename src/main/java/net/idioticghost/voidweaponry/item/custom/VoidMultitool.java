package net.idioticghost.voidweaponry.item.custom;

import net.idioticghost.voidweaponry.item.ModToolTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class VoidMultitool extends TieredItem {
    public VoidMultitool(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_PICKAXE)
                || state.is(BlockTags.MINEABLE_WITH_AXE)
                || state.is(BlockTags.MINEABLE_WITH_SHOVEL)
                || state.is(BlockTags.MINEABLE_WITH_HOE);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)
                || state.is(BlockTags.MINEABLE_WITH_AXE)
                || state.is(BlockTags.MINEABLE_WITH_SHOVEL)
                || state.is(BlockTags.MINEABLE_WITH_HOE)) {
            return ModToolTiers.VOIDGOLD.getSpeed();
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = context.getItemInHand();

        BlockState modified = null;
        ItemAbility usedAction = null;

        if ((modified = state.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false)) != null) {
            usedAction = ItemAbilities.AXE_STRIP;
        } else if ((modified = state.getToolModifiedState(context, ItemAbilities.AXE_SCRAPE, false)) != null) {
            usedAction = ItemAbilities.AXE_SCRAPE;
        } else if ((modified = state.getToolModifiedState(context, ItemAbilities.AXE_WAX_OFF, false)) != null) {
            usedAction = ItemAbilities.AXE_WAX_OFF;
        } else if ((modified = state.getToolModifiedState(context, ItemAbilities.SHOVEL_FLATTEN, false)) != null) {
            usedAction = ItemAbilities.SHOVEL_FLATTEN;
        }

        if (modified != null) {
            if (!level.isClientSide) {
                level.setBlock(pos, modified, 11);
                level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, pos);

                if (usedAction == ItemAbilities.AXE_STRIP) {
                    level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else if (usedAction == ItemAbilities.AXE_SCRAPE) {
                    level.playSound(null, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else if (usedAction == ItemAbilities.AXE_WAX_OFF) {
                    level.playSound(null, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else if (usedAction == ItemAbilities.SHOVEL_FLATTEN) {
                    level.playSound(null, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
        return toolAction == ItemAbilities.PICKAXE_DIG
                || toolAction == ItemAbilities.AXE_DIG
                || toolAction == ItemAbilities.AXE_STRIP
                || toolAction == ItemAbilities.AXE_SCRAPE
                || toolAction == ItemAbilities.AXE_WAX_OFF
                || toolAction == ItemAbilities.SHOVEL_DIG
                || toolAction == ItemAbilities.SHOVEL_FLATTEN
                || toolAction == ItemAbilities.HOE_DIG
                || super.canPerformAction(stack, toolAction);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.voidweaponry.void_multitool_1.tooltip"));
        tooltip.add(Component.translatable("tooltip.voidweaponry.void_multitool_2.tooltip"));
    }
}