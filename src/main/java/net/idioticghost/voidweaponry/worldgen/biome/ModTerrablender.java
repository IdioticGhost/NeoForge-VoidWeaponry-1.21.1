package net.idioticghost.voidweaponry.worldgen.biome;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "overworld"), 5));
    }
}
