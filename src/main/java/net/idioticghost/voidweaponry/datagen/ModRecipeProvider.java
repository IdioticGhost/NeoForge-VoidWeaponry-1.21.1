package net.idioticghost.voidweaponry.datagen;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        List<ItemLike> VOID_COOKABLES = List.of(ModItems.COOKED_VOID_KELP.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.VOID_KELP_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.COOKED_VOID_KELP.get())
                .unlockedBy(getHasName(ModItems.COOKED_VOID_KELP.get()), has(ModItems.COOKED_VOID_KELP.get()))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + "cooked_void_kelp_block_from_cooked_void_kelp");


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.VOID_LANTERN.get(), 3)
                .pattern(" A ")
                .pattern("ABA")
                .pattern("ACA")
                .define('A', Items.IRON_NUGGET)
                .define('B', ModItems.VOID_KELP.get())
                .define('C', ModItems.VOIDBOUND_GOLD.get())
                .unlockedBy(getHasName(ModItems.VOIDBOUND_GOLD.get()), has(ModItems.VOIDBOUND_GOLD.get()))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + "void_lantern");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VOIDBOUND_GOLD.get(), 1)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', ModItems.VOID_SHARD.get())
                .define('B', Items.GOLD_INGOT)
                .unlockedBy(getHasName(ModItems.VOID_SHARD.get()), has(ModItems.VOID_SHARD.get()))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + "void_gold_from_shards");


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ENDSTONE_SAND_BLOCK.get(), 2)
                .requires(Blocks.END_STONE)
                .requires(Blocks.SAND)
                .unlockedBy(getHasName(Items.END_STONE), has(Items.END_STONE))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + ":end_stone_sand");

//        oreSmelting(pRecipeOutput, VOID_COOKABLES, RecipeCategory.FOOD, ModItems.COOKED_VOID_KELP.get(), 0.1f, 500, "void_food");

        stairBuilder(ModBlocks.VOIDGROWTH_STAIRS.get(), Ingredient.of(ModBlocks.VOIDGROWTH_PLANKS.get())).group("voidgrowth")
                .unlockedBy(getHasName(ModBlocks.VOIDGROWTH_PLANKS.get()), has(ModBlocks.VOIDGROWTH_PLANKS.get())).save(pRecipeOutput);
        slab(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOIDGROWTH_SLAB.get(), ModBlocks.VOIDGROWTH_PLANKS.get());

        buttonBuilder(ModBlocks.VOIDGROWTH_BUTTON.get(), Ingredient.of(ModBlocks.VOIDGROWTH_PLANKS.get())).group("voidgrowth")
                .unlockedBy(getHasName(ModBlocks.VOIDGROWTH_PLANKS.get()), has(ModBlocks.VOIDGROWTH_PLANKS.get())).save(pRecipeOutput);
        pressurePlate(pRecipeOutput, ModBlocks.VOIDGROWTH_PRESSURE_PLATE.get(), ModBlocks.VOIDGROWTH_PLANKS.get());

        fenceBuilder(ModBlocks.VOIDGROWTH_FENCE.get(), Ingredient.of(ModBlocks.VOIDGROWTH_PLANKS.get())).group("voidgrowth")
                .unlockedBy(getHasName(ModBlocks.VOIDGROWTH_PLANKS.get()), has(ModBlocks.VOIDGROWTH_PLANKS.get())).save(pRecipeOutput);
        fenceGateBuilder(ModBlocks.VOIDGROWTH_FENCE_GATE.get(), Ingredient.of(ModBlocks.VOIDGROWTH_PLANKS.get())).group("voidgrowth")
                .unlockedBy(getHasName(ModBlocks.VOIDGROWTH_PLANKS.get()), has(ModBlocks.VOIDGROWTH_PLANKS.get())).save(pRecipeOutput);

        doorBuilder(ModBlocks.VOIDGROWTH_DOOR.get(), Ingredient.of(ModBlocks.VOIDGROWTH_PLANKS.get())).group("voidgrowth")
                .unlockedBy(getHasName(ModBlocks.VOIDGROWTH_PLANKS.get()), has(ModBlocks.VOIDGROWTH_PLANKS.get())).save(pRecipeOutput);
        trapdoorBuilder(ModBlocks.VOIDGROWTH_TRAPDOOR.get(), Ingredient.of(ModBlocks.VOIDGROWTH_PLANKS.get())).group("voidgrowth")
                .unlockedBy(getHasName(ModBlocks.VOIDGROWTH_PLANKS.get()), has(ModBlocks.VOIDGROWTH_PLANKS.get())).save(pRecipeOutput);


        //SMITHING

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VOIDGOLD_SMITHING_TEMPLATE.get(), 2)
                .pattern("BAB")
                .pattern("BCB")
                .pattern("BBB")
                .define('A', Ingredient.of(
                        Items.NETHERITE_INGOT,
                        ModItems.VOIDGOLD_SMITHING_TEMPLATE.get()
                ))
                .define('B', Items.GOLD_BLOCK)
                .define('C', ModItems.VOIDBOUND_GOLD.get())
                .unlockedBy(getHasName(ModItems.VOIDBOUND_GOLD.get()), has(ModItems.VOIDBOUND_GOLD.get()));

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Items.NETHERITE_SWORD),
                        Ingredient.of(ModItems.VOIDBOUND_GOLD.get()),
                        RecipeCategory.MISC,
                        ModItems.VOIDGOLD_SWORD.get()
                ).unlocks("has_voidbound_gold", has(ModItems.VOIDBOUND_GOLD.get()))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + ":voidgold_smithing_sword");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Items.NETHERITE_AXE),
                        Ingredient.of(ModItems.VOIDBOUND_GOLD.get()),
                        RecipeCategory.MISC,
                        ModItems.VOIDGOLD_AXE.get()
                ).unlocks("has_voidbound_gold", has(ModItems.VOIDBOUND_GOLD.get()))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + ":voidgold_smithing_axe");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Items.NETHERITE_PICKAXE),
                        Ingredient.of(ModItems.VOIDBOUND_GOLD.get()),
                        RecipeCategory.MISC,
                        ModItems.VOIDGOLD_PICKAXE.get()
                ).unlocks("has_voidbound_gold", has(ModItems.VOIDBOUND_GOLD.get()))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + ":voidgold_smithing_pickaxe");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Items.NETHERITE_SHOVEL),
                        Ingredient.of(ModItems.VOIDBOUND_GOLD.get()),
                        RecipeCategory.MISC,
                        ModItems.VOIDGOLD_SHOVEL.get()
                ).unlocks("has_voidbound_gold", has(ModItems.VOIDBOUND_GOLD.get()))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + ":voidgold_smithing_shovel");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Items.NETHERITE_HOE),
                        Ingredient.of(ModItems.VOIDBOUND_GOLD.get()),
                        RecipeCategory.MISC,
                        ModItems.VOIDGOLD_HOE.get()
                ).unlocks("has_voidbound_gold", has(ModItems.VOIDBOUND_GOLD.get()))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + ":voidgold_smithing_hoe");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Items.NETHERITE_HELMET),
                        Ingredient.of(ModItems.VOIDBOUND_GOLD.get()),
                        RecipeCategory.MISC,
                        ModItems.VOIDGOLD_HELMET.get()
                ).unlocks("has_voidbound_gold", has(ModItems.VOIDBOUND_GOLD.get()))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + ":voidgold_smithing_helmet");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Items.NETHERITE_CHESTPLATE),
                        Ingredient.of(ModItems.VOIDBOUND_GOLD.get()),
                        RecipeCategory.MISC,
                        ModItems.VOIDGOLD_CHESTPLATE.get()
                ).unlocks("has_voidbound_gold", has(ModItems.VOIDBOUND_GOLD.get()))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + ":voidgold_smithing_chestplate");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Items.NETHERITE_LEGGINGS),
                        Ingredient.of(ModItems.VOIDBOUND_GOLD.get()),
                        RecipeCategory.MISC,
                        ModItems.VOIDGOLD_LEGGINGS.get()
                ).unlocks("has_voidbound_gold", has(ModItems.VOIDBOUND_GOLD.get()))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + ":voidgold_smithing_leggings");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Items.NETHERITE_BOOTS),
                        Ingredient.of(ModItems.VOIDBOUND_GOLD.get()),
                        RecipeCategory.MISC,
                        ModItems.VOIDGOLD_BOOTS.get()
                ).unlocks("has_voidbound_gold", has(ModItems.VOIDBOUND_GOLD.get()))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + ":voidgold_smithing_boots");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(Items.BOW),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        RecipeCategory.MISC,
                        ModItems.NETHERITE_BOW.get()
                ).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                .save(pRecipeOutput, VoidWeaponry.MOD_ID + ":netherite_smithing_bow");


    }


    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, VoidWeaponry.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
