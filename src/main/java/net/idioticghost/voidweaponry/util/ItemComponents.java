package net.idioticghost.voidweaponry.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

public class ItemComponents {

    public static Component generateHeatBarLore(int currentHeat, int maxHeat) {
        int totalBars = 10;
        int filledBars = Math.min(totalBars,
                (int) Math.floor(((double) currentHeat / maxHeat) * totalBars));

        String symbol = "="; // the actual fill character
        MutableComponent bar = Component.literal(""); // use MutableComponent

        // Add prefix
        bar.append(Component.literal("-+<[")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFAA00)).withBold(true)));

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
        bar.append(Component.literal("]>+-")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFAA00)).withBold(true)));

        return bar;
    }

    public static Component generateHeatBarText(int currentHeat, int maxHeat, int boostTimer) {
        int totalBars = 10;
        int filledBars = Math.min(totalBars, (int) Math.floor(((double) currentHeat / maxHeat) * totalBars));
        String symbol = "=";

        MutableComponent bar = Component.literal("");

        // --- Timer display ---
        if (boostTimer > 0) {
            bar.append(Component.literal(String.format("[%ds] ", boostTimer))
                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFAA00)).withBold(true)));
        } else {
            bar.append(Component.literal("[--]     ")
                    .setStyle(Style.EMPTY.withColor(0x555555).withBold(true)));
        }

        // --- Bar prefix ---
        bar.append(Component.literal("-+<[")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFAA00)).withBold(true)));

        // --- Bar fill ---
        float hueStart = 0.75f; // purple
        float hueEnd = 0.75f;
        float saturationStart = 0.8f;
        float saturationEnd = 0f;
        float brightnessStart = 1f;
        float brightnessEnd = 1f;

        for (int i = 0; i < totalBars; i++) {
            if (i < filledBars) {
                float t = totalBars <= 1 ? 0 : (float) i / (totalBars - 1);
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

        // --- Bar suffix ---
        bar.append(Component.literal("]>+-")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFAA00)).withBold(true)));

        // --- Heat numbers ---
        float tHeat = Math.min(1f, (float) currentHeat / 150f);
        float satHeat = saturationStart + tHeat * (saturationEnd - saturationStart);
        float briHeat = brightnessStart + tHeat * (brightnessEnd - brightnessStart);
        java.awt.Color heatColor = java.awt.Color.getHSBColor(hueStart, satHeat, briHeat);
        int heatRGB = (heatColor.getRed() << 16) | (heatColor.getGreen() << 8) | heatColor.getBlue();

        Component heatText = Component.literal(String.valueOf(currentHeat))
                .setStyle(Style.EMPTY.withColor(heatRGB));

        // Cap color
        int capRGB = maxHeat >= 150 ? 0xFF5555 : (maxHeat >= 110 ? 0xFFAA00 : 0xFFFFFF);

        Component slashText = Component.literal("/").setStyle(Style.EMPTY.withColor(0x555555));
        Component capText = Component.literal(""+maxHeat).setStyle(Style.EMPTY.withColor(capRGB));

        bar.append(Component.literal("    ")); // spacing
        bar.append(heatText);
        bar.append(slashText);
        bar.append(capText);

        return bar;
    }
}