package xyz.mrfrostydev.welcomeplayer.data.datagen.providers.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SmeltingRecipes extends RecipeProvider {

    public SmeltingRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public static void get(RecipeOutput output){
        oreSmelting(output,
                List.of(
                        ItemRegistry.RETROSTEEL_ORE.get(),
                        ItemRegistry.RAW_RETROSTEEL.get()
                ),
                RecipeCategory.MISC,
                ItemRegistry.RETROSTEEL_INGOT.get(),
                0.7F,
                200,
                "retrosteel_ingot"
        );
    }
}
