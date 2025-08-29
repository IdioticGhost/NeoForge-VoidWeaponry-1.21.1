package net.idioticghost.voidweaponry.event;


import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.component.ModDataComponents;
import net.idioticghost.voidweaponry.effect.ModEffects;
import net.idioticghost.voidweaponry.item.ModItems;
import net.idioticghost.voidweaponry.item.ModToolTiers;
import net.idioticghost.voidweaponry.item.custom.MaelstromItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = VoidWeaponry.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) return;

        ItemStack stack = player.getMainHandItem();
        Item item = stack.getItem();

        // VOID_MULTITOOL gives Haste
        if (item == ModItems.VOID_MULTITOOL.get()) {
            if (!player.hasEffect(MobEffects.DIG_SPEED)) {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 2, 0, true, false));
            }
        }
        // VOIDGOLD_SWORD gives Strength whgen in the void
        else if (item == ModItems.VOIDGOLD_SWORD.get()) {
            if (!player.hasEffect(MobEffects.DAMAGE_BOOST)) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2, 0, true, false));
            }
        }
        // Any non-sword VOIDGOLD tier tool gives Haste in the void
        else if (item instanceof TieredItem tiered && tiered.getTier() == ModToolTiers.VOIDGOLD) {
            if (!player.hasEffect(MobEffects.DIG_SPEED)) {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 2, 0, true, false));
            }
        }
        MaelstromItem.handleHoldParticles(player);


        // ------------------------------
        // Heat decay (slower timer)
        int tickCounter = player.getPersistentData().getInt("dragonKatanaHeatTick");
        tickCounter++;

        if (tickCounter >= 100) { // ~5.5 seconds
            tickCounter = 0;

            for (ItemStack invStack : player.getInventory().items) {
                if (!invStack.is(ModItems.DRAGON_KATANA.get())) continue;

                int heat = invStack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
                if (heat <= 0) continue;

                heat = Math.max(0, heat - 3); // decrement
                invStack.set(ModDataComponents.KATANA_HEAT_VALUE.get(), heat);
            }
        }

        player.getPersistentData().putInt("dragonKatanaHeatTick", tickCounter);

        // ------------------------------
        // Heat application / DragonFire V check (every tick)
        boolean overheat = false;
        for (ItemStack invStack : player.getInventory().items) {
            if (!invStack.is(ModItems.DRAGON_KATANA.get())) continue;
            int heat = invStack.getOrDefault(ModDataComponents.KATANA_HEAT_VALUE.get(), 0);
            if (heat > 100) {
                overheat = true;
                break;
            }
        }

        MobEffectInstance dragonFire = player.getEffect(ModEffects.DRAGONFIRE);
        if (overheat) {
            if (dragonFire == null || dragonFire.getAmplifier() != 4 || dragonFire.getDuration() < 20) {
                player.addEffect(new MobEffectInstance(ModEffects.DRAGONFIRE, 999999, 4, true, true, true));
            }
        } else {
            if (dragonFire != null && dragonFire.getAmplifier() == 4) {
                player.removeEffect(ModEffects.DRAGONFIRE);
            }
        }
    }
}