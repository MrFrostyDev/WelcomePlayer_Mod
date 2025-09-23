package xyz.mrfrostydev.welcomeplayer.data.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.*;

import java.util.concurrent.CompletableFuture;

public class DataInitialize {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        DatapackProvider datapackProvider =  new DatapackProvider(output, lookupProvider);
        BlockTagsProvider blockTagsProvider =  new BlockTagsProvider(output, lookupProvider, existingFileHelper);

        generator.addProvider(event.includeServer(), datapackProvider);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new EntityTypeTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter()));
        generator.addProvider(event.includeServer(), new GlobalLootModifierProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new ItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new SoundDefinitionsProvider(output, existingFileHelper));
    }
}
