package net.idioticghost.voidweaponry.item.custom;

import net.idioticghost.voidweaponry.effect.ModEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import java.util.Random;


public class TempoItem extends SwordItem {
    public TempoItem(Tier tier, Properties properties) {
        super(tier, properties);
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