package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, WelcomeplayerMain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
