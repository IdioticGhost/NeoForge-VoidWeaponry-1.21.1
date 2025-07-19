package net.idioticghost.voidweaponry.datagen;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.worldgen.ModBiomeModifiers;
import net.idioticghost.voidweaponry.worldgen.ModConfiguredFeatures;
import net.idioticghost.voidweaponry.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

    public class ModDatapackEntries extends DatapackBuiltinEntriesProvider {
        public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()

                .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
                .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);
//                //.add(Registries.ENCHANTMENT, ModEnchantments::bootstrap)
        ;

        public ModDatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries, BUILDER, Set.of(VoidWeaponry.MOD_ID));
        }
    }
