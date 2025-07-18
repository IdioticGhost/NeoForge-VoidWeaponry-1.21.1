package net.idioticghost.voidweaponry.particle.custom;

import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

@EventBusSubscriber(modid = "voidweaponry")
public class CritParticleHandler {

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();

        if (stack.getItem() == ModItems.DEATH_BOW.get()) {
            event.setCriticalHit(false);
        }
    }
}