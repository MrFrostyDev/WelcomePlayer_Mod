package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, WelcomeplayerMain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ItemRegistry.SERVICE_BOT_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ItemRegistry.HANDIBOT_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ItemRegistry.ERADICATOR_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
