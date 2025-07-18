package net.idioticghost.voidweaponry.worldgen.tree;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower SHADOW_PINE = new TreeGrower(VoidWeaponry.MOD_ID + ":shadow_pine",
            Optional.empty(), Optional.of(ModConfiguredFeatures.SHADOW_PINE_KEY), Optional.empty());
}
