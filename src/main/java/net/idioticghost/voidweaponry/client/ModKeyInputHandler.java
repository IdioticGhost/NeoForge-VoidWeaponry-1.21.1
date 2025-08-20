package net.idioticghost.voidweaponry.client;

import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(modid = "voidweaponry", value = Dist.CLIENT)
public class ModKeyInputHandler {

    private static final double DEATH_BOW_EFFECT_RADIUS = 5.0;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ModKeybinds.ABILITY_KEY.consumeClick()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            ItemStack held = mc.player.getMainHandItem();

            if (held.isEmpty()) {
                System.out.println("No item held!");
            } else if (held.getItem() == ModItems.DEATH_BOW.get()) {
                System.out.println("Ability used with custom item!");
                // Your ability logic here
            } else {
                System.out.println("Ability used with other item!");
            }
        }
    }
}