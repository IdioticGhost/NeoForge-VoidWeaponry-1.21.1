package net.idioticghost.voidweaponry.worldgen.dimension;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

public class ModDimensions {
    public static final ResourceKey<Level> EVOLVING_VOID_LEVEL_KEY = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("voidweaponry", "evolvingvoid")
    );

    public static final ResourceKey<DimensionType> EVOLVING_VOID_TYPE_KEY = ResourceKey.create(
            Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath("voidweaponry", "evolvingvoid_type")
    );


    public static final ResourceKey<Level> VOID_MAW_LEVEL_KEY = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("voidweaponry", "voidmaw")
    );

    public static final ResourceKey<DimensionType> VOID_MAW_TYPE_KEY = ResourceKey.create(
            Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath("voidweaponry", "voidmaw_type")
    );
}