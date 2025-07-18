package net.idioticghost.voidweaponry.event;


import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.item.ModItems;
import net.idioticghost.voidweaponry.item.ModToolTiers;
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
    }
}