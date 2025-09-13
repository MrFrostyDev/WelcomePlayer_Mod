package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceRewards;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.PlayerObjectives;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.VendorItems;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(DatapackRegistry.VENDOR_ITEMS, VendorItems::bootstrap)
            .add(DatapackRegistry.AUDIENCE_EVENTS, AudienceEvents::bootstrap)
            .add(DatapackRegistry.AUDIENCE_REWARDS, AudienceRewards::bootstrap)
            .add(DatapackRegistry.PLAYER_OBJECTIVES, PlayerObjectives::bootstrap);

    public DatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(WelcomeplayerMain.MOD_ID));
    }
}