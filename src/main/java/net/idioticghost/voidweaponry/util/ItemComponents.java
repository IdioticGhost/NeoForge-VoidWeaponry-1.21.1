package net.idioticghost.voidweaponry.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public class ItemComponents {

    public static Component generateHeatBar(int currentHeat, int maxHeat) {
        int totalBars = 10;
        int filledBars = Math.min(totalBars,
                (int) Math.floor(((double) currentHeat / maxHeat) * totalBars));

        String symbol = "="; // the actual fill character
        MutableComponent bar = Component.literal(""); // use MutableComponent

        // Add prefix
        bar.append(Component.literal("§6§l-+<["));

        // Gradient setup (purple → white)
        float hueStart = 0.75f; // purple
        float hueEnd = 0.75f;   // same hue for purple → white
        float saturationStart = 0.8f;
        float saturationEnd = 0f; // fade to white
        float brightnessStart = 1f;
        float brightnessEnd = 1f;

        for (int i = 0; i < totalBars; i++) {
            if (i < filledBars) {
                float t = totalBars <= 1 ? 0 : (float) i / (totalBars - 1); // always relative to totalBars
                float hue = hueStart + t * (hueEnd - hueStart);
                float sat = saturationStart + t * (saturationEnd - saturationStart);
                float bri = brightnessStart + t * (brightnessEnd - brightnessStart);

                java.awt.Color color = java.awt.Color.getHSBColor(hue, sat, bri);
                int rgb = (color.getRed() << 16) | (color.getGreen() << 8) | color.getBlue();

                bar.append(Component.literal(symbol).setStyle(Style.EMPTY.withColor(rgb)));
            } else {
                bar.append(Component.literal(symbol).setStyle(Style.EMPTY.withColor(0x555555)));
            }
        }

        // Add suffix
        bar.append(Component.literal("§6§l]>+-"));

        return bar;
    }
}