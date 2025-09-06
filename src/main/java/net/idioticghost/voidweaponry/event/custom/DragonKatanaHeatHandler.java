package net.idioticghost.voidweaponry.event.custom;

import net.idioticghost.voidweaponry.component.ModDataComponents;
import net.idioticghost.voidweaponry.effect.ModEffects;
import net.idioticghost.voidweaponry.item.ModItems;
import net.idioticghost.voidweaponry.particle.ModParticles;
import net.idioticghost.voidweaponry.util.ItemComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class DragonKatanaHeatHandler {

    private static final int HEAT_DECAY_TICKS = 100;

    public static void tick(Player player) {
        handleHeatDecay(player);
        handleHeatEffects(player);
        handleBoostTimer(player);
        handleThresholds(player);
    }

    private static void handleHeatDecay(Player player) {
        int tickCounter = player.getPersistentData().getInt("dragonKatanaHeatTick");
        tickCounter++;

        if (tickCounter >= HEAT_DECAY_TICKS) {
            tickCounter = 0;
            for (ItemStack invStack : player.getInventory().items) {
                if (!invStack.is(ModItems.DRAGON_KATANA.get())) continue;

                int heat = invStack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
                if (heat <= 0) continue;
                invStack.set(ModDataComponents.KATANA_HEAT_VALUE.get(), Math.max(0, heat - 3));
            }
        }

        player.getPersistentData().putInt("dragonKatanaHeatTick", tickCounter);
    }

    private static void handleHeatEffects(Player player) {
        boolean overheat = false;

        for (ItemStack invStack : player.getInventory().items) {
            if (!invStack.is(ModItems.DRAGON_KATANA.get())) continue;
            int heat = invStack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
            if (heat > 100) {
                overheat = true;
                break;
            }
        }

        if (player.getMainHandItem().is(ModItems.DRAGON_KATANA.get())) {
            ItemStack stack = player.getMainHandItem();
            int heat = stack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
            int cap = stack.getOrDefault(ModDataComponents.KATANA_HEAT_CAP.get(), 110);
            int timer = stack.getOrDefault(ModDataComponents.KATANA_HEAT_BOOST_TIMER.get(), 0);

            Component barText = ItemComponents.generateHeatBarText(heat, cap, timer);
            player.displayClientMessage(barText, true);
        }

        if (overheat) {
            var dragonFire = player.getEffect(ModEffects.DRAGONFIRE);
            if (dragonFire == null || dragonFire.getAmplifier() != 4 || dragonFire.getDuration() < 20) {
                player.addEffect(new MobEffectInstance(ModEffects.DRAGONFIRE, 20, 4, true, true, true));
            }
        }
    }

    private static void handleBoostTimer(Player player) {
        int boostSecondCounter = player.getPersistentData().getInt("dragonKatanaBoostSecondCounter");
        boostSecondCounter++;

        if (boostSecondCounter >= 20) {
            boostSecondCounter = 0;
            for (ItemStack invStack : player.getInventory().items) {
                if (!invStack.is(ModItems.DRAGON_KATANA.get())) continue;

                int boostTimer = invStack.getOrDefault(ModDataComponents.KATANA_HEAT_BOOST_TIMER.get(), 0);
                if (boostTimer > 0) {
                    boostTimer--;
                    invStack.set(ModDataComponents.KATANA_HEAT_BOOST_TIMER.get(), boostTimer);

                    if (boostTimer <= 0) {
                        invStack.set(ModDataComponents.KATANA_HEAT_CAP.get(), 110);

                        int currentHeat = invStack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
                        if (currentHeat > 110) invStack.set(ModDataComponents.KATANA_HEAT_VALUE.get(), 110);

                        if (!player.level().isClientSide()) {
                            player.displayClientMessage(Component.literal("Heat boost expired. Cap reset to 110."), true);
                        }
                    }
                }
            }
        }

        player.getPersistentData().putInt("dragonKatanaBoostSecondCounter", boostSecondCounter);
    }

    private static void handleThresholds(Player player) {
        for (ItemStack invStack : player.getInventory().items) {
            if (!invStack.is(ModItems.DRAGON_KATANA.get())) continue;

            int heat = invStack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
            int lastThreshold = invStack.getOrDefault(ModDataComponents.KATANA_HEAT_LAST_THRESHOLD.get(), 0);
            int spiralTick = invStack.getOrDefault(ModDataComponents.KATANA_HEAT_SPIRAL_TICK.get(), 0);

            int threshold = 0;
            if (heat >= 111) threshold = 3;
            else if (heat >= 70) threshold = 2;
            else if (heat >= 40) threshold = 1;

            // Upward threshold
            if (threshold > lastThreshold) {
                invStack.set(ModDataComponents.KATANA_HEAT_LAST_THRESHOLD.get(), threshold);
                invStack.set(ModDataComponents.KATANA_HEAT_SPIRAL_TICK.get(), 1);
                spiralTick = 1;
            }

            // Downward threshold
            if (threshold < lastThreshold) {
                invStack.set(ModDataComponents.KATANA_HEAT_LAST_THRESHOLD.get(), threshold);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 2.0f, 1.25f);
            }

            // Animate spiral
            int maxTicks = 20;
            if (spiralTick > 0 && spiralTick <= maxTicks) {
                spawnHeatSpiral(player, spiralTick, maxTicks, heat);
                spiralTick++;
                invStack.set(ModDataComponents.KATANA_HEAT_SPIRAL_TICK.get(), spiralTick);
            }

            // Reset if heat drops below 40
            if (heat < 40 && lastThreshold != 0) {
                invStack.set(ModDataComponents.KATANA_HEAT_LAST_THRESHOLD.get(), 0);
                invStack.set(ModDataComponents.KATANA_HEAT_SPIRAL_TICK.get(), 0);
            }
        }
    }

    public static void spawnHeatSpiral(Player player, int startTick, int maxTicks, int heat) {
        if (!(player.level() instanceof ServerLevel serverLevel)) return;

        double height = 1.8;
        double radius = 0.5;
        double y = (height / maxTicks) * startTick;
        int rotations = 2;
        double angle = 2 * Math.PI * startTick * rotations / maxTicks;
        double xOffset = Math.cos(angle) * radius;
        double zOffset = Math.sin(angle) * radius;

        ParticleOptions particle;
        if (heat >= 111) particle = ParticleTypes.END_ROD;
        else if (heat >= 70) particle = ModParticles.DRAGONFIRE_PARTICLES.get();
        else if (heat >= 40) particle = ModParticles.BURNING_PARTICLES.get();
        else particle = ModParticles.BURNING_PARTICLES.get();

        serverLevel.sendParticles(particle,
                player.getX() + xOffset,
                player.getY() + y,
                player.getZ() + zOffset,
                3, 0, 0, 0, 0);
    }

    // Add this inside DragonKatanaHeatHandler

    public class DragonKatanaStrengthHandler {
        private static final ResourceLocation TEMP_STRENGTH_MODIFIER_ID =
                ResourceLocation.fromNamespaceAndPath("voidweaponry", "temp_strength_boost");

        public static void updateHeatStrength(Player player, double baseStrength) {
            if (player.level().isClientSide()) return;

            ItemStack stack = player.getMainHandItem();
            if (!stack.is(ModItems.DRAGON_KATANA.get())) return;

            double heat = stack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
            int boostTimer = stack.getOrDefault(ModDataComponents.KATANA_HEAT_BOOST_TIMER.get(), 0);

            AttributeInstance attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attackDamage == null) return;

            var old = attackDamage.getModifier(TEMP_STRENGTH_MODIFIER_ID);
            if (old != null) attackDamage.removeModifier(old);

            if (boostTimer > 0) {
                // Scale however you want
                double speedBonus = baseStrength + (heat / 150.0);
                attackDamage.addTransientModifier(new AttributeModifier(
                        TEMP_STRENGTH_MODIFIER_ID,
                        speedBonus,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                ));
            }
        }
    }

    private static final ResourceLocation TEMP_SPEED_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath("voidweaponry", "temp_speed_boost");

    public class DragonKatanaSpeedHandler {

        public static void updateHeatSpeed(Player player, double baseSpeed) {
            if (player.level().isClientSide()) return;

            ItemStack stack = player.getMainHandItem();
            if (!stack.is(ModItems.DRAGON_KATANA.get())) return;

            double heat = stack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
            int boostTimer = stack.getOrDefault(ModDataComponents.KATANA_HEAT_BOOST_TIMER.get(), 0);

            AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (movementSpeed == null) return;

            var old = movementSpeed.getModifier(TEMP_SPEED_MODIFIER_ID);
            if (old != null) movementSpeed.removeModifier(old);

            if (boostTimer > 0) {
                // Scale however you want
                double speedBonus = baseSpeed + (heat / 150.0);
                movementSpeed.addTransientModifier(new AttributeModifier(
                        TEMP_SPEED_MODIFIER_ID,
                        speedBonus,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                ));
            }
        }
    }
}