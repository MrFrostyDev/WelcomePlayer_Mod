package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.*;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(DatapackRegistry.VENDOR_ITEMS, VendorItems::bootstrap)
            .add(DatapackRegistry.AUDIENCE_EVENTS, AudienceEvents::bootstrap)
            .add(DatapackRegistry.AUDIENCE_REWARDS, AudienceRewards::bootstrap)
            .add(DatapackRegistry.PLAYER_OBJECTIVES, PlayerObjectives::bootstrap)
            .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    public DatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(WelcomeplayerMain.MOD_ID));
    }
}