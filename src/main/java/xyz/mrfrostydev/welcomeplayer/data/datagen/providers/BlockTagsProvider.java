package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;

import java.util.concurrent.CompletableFuture;

public class BlockTagsProvider extends net.neoforged.neoforge.common.data.BlockTagsProvider {

    // Get parameters from one of the `GatherDataEvent`s.
    public BlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, WelcomeplayerMain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        BlockRegistry.RETROSTEEL_BLEND_WALL.get(),
                        BlockRegistry.RETROSTEEL_WHITE_WALL.get(),
                        BlockRegistry.RETROSTEEL_BROWN_WALL.get()
                );
    }
}
