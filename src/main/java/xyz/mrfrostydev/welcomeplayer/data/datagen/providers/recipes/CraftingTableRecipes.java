package xyz.mrfrostydev.welcomeplayer.data.datagen.providers.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class CraftingTableRecipes extends RecipeProvider {

    public CraftingTableRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public static void get(RecipeOutput output){
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, new ItemStack(ItemRegistry.RATION.get(), 3))
                .requires(ItemRegistry.RETROSTEEL_INGOT.get())
                .requires(Tags.Items.FOODS_COOKED_MEAT)
                .requires(Tags.Items.FOODS_COOKED_MEAT)
                .requires(Tags.Items.FOODS_COOKED_MEAT)
                .unlockedBy("has_retrosteel_ingot", has(ItemRegistry.RETROSTEEL_INGOT.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, new ItemStack(ItemRegistry.RATION.get(), 3))
                .requires(ItemRegistry.RETROSTEEL_INGOT.get())
                .requires(Tags.Items.FOODS_COOKED_FISH)
                .requires(Tags.Items.FOODS_COOKED_FISH)
                .requires(Tags.Items.FOODS_COOKED_FISH)
                .unlockedBy("has_retrosteel_ingot", has(ItemRegistry.RETROSTEEL_INGOT.get()))
                .save(output, "ration_cooked_fish");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, new ItemStack(ItemRegistry.RATION.get(), 3))
                .requires(ItemRegistry.RETROSTEEL_INGOT.get())
                .requires(Tags.Items.FOODS_VEGETABLE)
                .requires(Tags.Items.FOODS_VEGETABLE)
                .requires(Tags.Items.FOODS_VEGETABLE)
                .requires(Tags.Items.FOODS_VEGETABLE)
                .requires(Tags.Items.FOODS_VEGETABLE)
                .requires(Tags.Items.FOODS_VEGETABLE)
                .unlockedBy("has_retrosteel_ingot", has(ItemRegistry.RETROSTEEL_INGOT.get()))
                .save(output, "ration_veggie");
    }
}
