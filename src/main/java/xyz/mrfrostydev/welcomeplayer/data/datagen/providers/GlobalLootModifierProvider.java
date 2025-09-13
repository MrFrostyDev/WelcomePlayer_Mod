package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

import java.util.concurrent.CompletableFuture;


public class GlobalLootModifierProvider extends net.neoforged.neoforge.common.data.GlobalLootModifierProvider {

    public GlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, WelcomeplayerMain.MOD_ID);
    }

    @Override
    protected void start() {

    }
}
