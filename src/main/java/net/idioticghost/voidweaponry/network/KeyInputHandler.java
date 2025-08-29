package net.idioticghost.voidweaponry.network;

import net.idioticghost.voidweaponry.client.ModKeybinds;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(modid = "voidweaponry", value = Dist.CLIENT)
public class KeyInputHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ModKeybinds.ABILITY_KEY != null && ModKeybinds.ABILITY_KEY.consumeClick()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.getConnection() == null) return;

            // Send the payload directly
            mc.getConnection().send(new AbilityKeyPacket());
        }
    }
}
