package net.idioticghost.voidweaponry.item.custom;

import it.unimi.dsi.fastutil.ints.IntList;
import net.idioticghost.voidweaponry.component.ModDataComponents;
import net.idioticghost.voidweaponry.entity.custom.BlackholeKnifeProjectileEntity;
import net.idioticghost.voidweaponry.entity.custom.WhiteholeKnifeProjectileEntity;
import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;

public class BlackholeKnifeItem extends SwordItem {
    public BlackholeKnifeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        boolean whiteVersion = stack.getOrDefault(ModDataComponents.KNIFE_VERSION.get(), false);
        if (!whiteVersion) {
            for (int i = 1; i <= 16; i++) {
                pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.blackhole_knife_" + i + ".tooltip"));
            }
        } else {
            for (int i = 1; i <= 15; i++) {
                pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.whitehole_knife_" + i + ".tooltip"));
            }
        }
    }

    public static BlackholeKnifeProjectileEntity findBlackholeKnife(Player player, Level level) {
        return level.getEntitiesOfClass(BlackholeKnifeProjectileEntity.class,
                        player.getBoundingBox().inflate(128))
                .stream()
                .filter(knife -> knife.getOwner() != null && knife.getOwner().getUUID().equals(player.getUUID()))
                .findFirst()
                .orElse(null);
    }

    public static WhiteholeKnifeProjectileEntity findWhiteholeKnife(Player player, Level level) {
        return level.getEntitiesOfClass(WhiteholeKnifeProjectileEntity.class,
                        player.getBoundingBox().inflate(128))
                .stream()
                .filter(knife -> knife.getOwner() != null && knife.getOwner().getUUID().equals(player.getUUID()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        boolean alreadyThrown = itemstack.getOrDefault(ModDataComponents.THREW_KNIFE.get(), false);
        boolean knifeVersion = itemstack.getOrDefault(ModDataComponents.KNIFE_VERSION.get(), false);

        if (alreadyThrown) { // Returning knife
            if (!level.isClientSide) {
                BlackholeKnifeProjectileEntity blackKnife = findBlackholeKnife(player, level);
                WhiteholeKnifeProjectileEntity whiteKnife = findWhiteholeKnife(player, level);

                if (whiteKnife != null) {
                    whiteKnife.setAbilityActive(true);
                    player.teleportTo(whiteKnife.getX(), whiteKnife.getY(), whiteKnife.getZ());
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.5F, 1.5F);

                    whiteKnife.discard();

                    // Reset + flip back
                    itemstack.set(ModDataComponents.THREW_KNIFE.get(), false);
                    itemstack.set(ModDataComponents.KNIFE_VERSION.get(), !knifeVersion);
                } else if (blackKnife != null) {
                    blackKnife.setAbilityActive(true);
                    blackKnife.level().playSound(
                            null,
                            blackKnife.getX(),
                            blackKnife.getY(),
                            blackKnife.getZ(),
                            SoundEvents.TRIDENT_THUNDER,
                            SoundSource.PLAYERS,
                            3.0f, // volume
                            0.25f  // pitch
                    );
                    blackKnife.level().playSound(
                            null,
                            blackKnife.getX(),
                            blackKnife.getY(),
                            blackKnife.getZ(),
                            SoundEvents.ZOMBIE_VILLAGER_CURE,
                            SoundSource.PLAYERS,
                            0.25f, // volume
                            0.75f  // pitch
                    );

                    player.displayClientMessage(Component.literal("Amongus... sus"), false);
                    itemstack.set(ModDataComponents.THREW_KNIFE.get(), true);

                    blackKnife.startSelfDestruct(140, () -> {
                        if (player != null) {
                            for (int i = 0; i < player.getInventory().items.size(); i++) {
                                ItemStack stack = player.getInventory().items.get(i);
                                if (stack.is(ModItems.BLACKHOLE_KNIFE.get())) {
                                    stack.set(ModDataComponents.KNIFE_VERSION.get(), !knifeVersion);
                                    stack.set(ModDataComponents.THREW_KNIFE.get(), false);
                                    break;
                                }
                            }

                            player.displayClientMessage(Component.literal("The Blackhole Knife reverted!"), false);
                        }

                        blackKnife.discard();
                    });
                } else {
                    itemstack.set(ModDataComponents.KNIFE_VERSION.get(), knifeVersion);
                }
            }
            player.getCooldowns().addCooldown(this, 40);
            return InteractionResultHolder.fail(itemstack);

        } else { // Throwing knife
            itemstack.set(ModDataComponents.THREW_KNIFE.get(), true);

            // Throw sound
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL,
                    0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

            if (!level.isClientSide) {
                if (knifeVersion) {
                    WhiteholeKnifeProjectileEntity projectile = new WhiteholeKnifeProjectileEntity(player, level);
                    projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0F);
                    level.addFreshEntity(projectile);
                } else {
                    BlackholeKnifeProjectileEntity projectile = new BlackholeKnifeProjectileEntity(player, level);
                    projectile.setOriginalStack(itemstack);
                    projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0F);
                    level.addFreshEntity(projectile);
                }
            }

            // *** Flip AFTER spawning projectile ***
            itemstack.set(ModDataComponents.KNIFE_VERSION.get(), !knifeVersion);

            player.awardStat(Stats.ITEM_USED.get(this));
            player.getCooldowns().addCooldown(this, 20);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
    }
}