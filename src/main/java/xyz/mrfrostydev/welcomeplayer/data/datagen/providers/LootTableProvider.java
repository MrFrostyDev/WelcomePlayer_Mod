package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.loot.ModBlockLoot;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.loot.ModEntityLoot;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends net.minecraft.data.loot.LootTableProvider {
    public LootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
                output,
                Collections.emptySet(),
                List.of(
                        new SubProviderEntry(ModEntityLoot::new, LootContextParamSets.ENTITY),
                        new SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK)
                ),
                registries
        );
    }
}
