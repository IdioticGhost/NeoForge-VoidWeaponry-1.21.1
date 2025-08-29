package net.idioticghost.voidweaponry.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static final String KEY_CATEGORY = "key.categories.voidweaponry";
    public static final String KEY_WEAPON_ABILITY = "key.voidweaponry.weapon_ability";

    public static KeyMapping ABILITY_KEY;

    public static void registerKeybinds() {
        ABILITY_KEY = new KeyMapping(
                KEY_WEAPON_ABILITY,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                KEY_CATEGORY
        );

        // Append to the key mapping array
        var options = net.minecraft.client.Minecraft.getInstance().options;
        KeyMapping[] old = options.keyMappings;
        KeyMapping[] newArray = new KeyMapping[old.length + 1];
        System.arraycopy(old, 0, newArray, 0, old.length);
        newArray[old.length] = ABILITY_KEY;
        options.keyMappings = newArray;
    }
}