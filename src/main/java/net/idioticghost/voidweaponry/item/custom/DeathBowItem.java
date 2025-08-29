package net.idioticghost.voidweaponry.item.custom;

import net.idioticghost.voidweaponry.entity.ModEntities;
import net.idioticghost.voidweaponry.entity.custom.DeathArrowEntity;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.function.Consumer;

public class DeathBowItem extends BowItem {
    public DeathBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        // Only show custom tooltips, no super call
        for (int i = 1; i <= 11; i++) {
            pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.death_bow_" + i + ".tooltip"));
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity shooter, int timeLeft) {
        if (!(shooter instanceof Player player)) return;

        int charge = this.getUseDuration(stack, shooter) - timeLeft;
        float velocity = BowItem.getPowerForTime(charge);

        if (velocity < 0.1F) return;

        if (!level.isClientSide) {
            DeathArrowEntity arrow = new DeathArrowEntity(ModEntities.DEATH_ARROW.get(), level, player);
            arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity * 3.0F, 1.0F);
            arrow.setBaseDamage(2.0D);
            arrow.setCritArrow(velocity == 1.0F);
            arrow.setChargeLevel(velocity);

            level.addFreshEntity(arrow);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);

        player.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }
    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
    }
}