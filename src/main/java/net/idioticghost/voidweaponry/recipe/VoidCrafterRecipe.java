package net.idioticghost.voidweaponry.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record   VoidCrafterRecipe(Ingredient inputItem, ItemStack output) implements Recipe<VoidCrafterRecipeInput> {
    @Override
    public boolean matches(VoidCrafterRecipeInput pInput, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(VoidCrafterRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }
}
