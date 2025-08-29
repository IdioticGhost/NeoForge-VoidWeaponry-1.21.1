package net.idioticghost.voidweaponry.item.custom;

import net.idioticghost.voidweaponry.effect.ModEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;


public class TempoItem extends SwordItem {
    public TempoItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        // Only show custom tooltips, no super call
        for (int i = 1; i <= 14; i++) {
            pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.tempo_katana_" + i + ".tooltip"));
        }
    }

    private static final Random RANDOM = new Random();

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            player.addEffect(new MobEffectInstance(
                    ModEffects.PARRY,
                    5,
                    1,
                    false,
                    false
            ));

            player.getCooldowns().addCooldown(this, 100);
            float pitch = 1.8f + RANDOM.nextFloat() * 0.1f;

            player.level().playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.UI_TOAST_IN,
                    SoundSource.PLAYERS,
                    5.0f,
                    pitch
            );
        }


//        if (player instanceof Player) {
//            Vec3 start = player.position(); // bottom-left
//            Vec3 end = start.add(1, 1, 0);  // top-right (relative to player)
//            ParticleUtils.spawnDiagonalLine(player, ModParticles.RED_PARTICLES.get(), 20, 1.0, 0.5);
//        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }
}