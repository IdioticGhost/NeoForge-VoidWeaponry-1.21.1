package net.idioticghost.voidweaponry.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static final String KEY_CATEGORY = "key.categories.voidweaponry";
    public static final String KEY_ABILITY = "key.voidweaponry.ability";

    public static KeyMapping ABILITY_KEY;

    public static void registerKeybinds(FMLClientSetupEvent event) {
        ABILITY_KEY = new KeyMapping(
                KEY_ABILITY, // Translation key
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_R, // Default key
                KEY_CATEGORY // Category name
        );

        net.minecraft.client.Minecraft.getInstance().options.keyMappings =
                appendKeyMapping(net.minecraft.client.Minecraft.getInstance().options.keyMappings, ABILITY_KEY);
    }

    private static KeyMapping[] appendKeyMapping(KeyMapping[] original, KeyMapping keyMapping) {
        KeyMapping[] newArray = new KeyMapping[original.length + 1];
        System.arraycopy(original, 0, newArray, 0, original.length);
        newArray[original.length] = keyMapping;
        return newArray;
    }
}