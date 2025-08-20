package net.idioticghost.voidweaponry.item;

import net.idioticghost.voidweaponry.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier VOIDGOLD = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_VOIDGOLD_TOOL,
            2300, 10.5f, 4, 28, () -> Ingredient.of(ModItems.VOIDBOUND_GOLD));

    public static final Tier VOIDARTIFACT = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_VOIDGOLD_TOOL,
            2300, 10.5f, 4, 28, () -> Ingredient.of(ModItems.VOIDBOUND_GOLD));
    }

