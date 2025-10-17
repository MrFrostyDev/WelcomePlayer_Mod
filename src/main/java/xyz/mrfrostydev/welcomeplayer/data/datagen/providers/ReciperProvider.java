package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.recipes.CraftingTableRecipes;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.recipes.SmeltingRecipes;

import java.util.concurrent.CompletableFuture;

public class ReciperProvider extends RecipeProvider {
    public ReciperProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        CraftingTableRecipes.get(output);
        SmeltingRecipes.get(output);
    }
}
