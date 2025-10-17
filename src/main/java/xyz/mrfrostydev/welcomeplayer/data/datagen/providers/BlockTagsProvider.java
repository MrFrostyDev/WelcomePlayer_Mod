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
                        BlockRegistry.RETROSTEEL_BLEND_BLOCK.get(),
                        BlockRegistry.RETROSTEEL_WHITE_WALL.get(),
                        BlockRegistry.RETROSTEEL_WHITE_PLATE.get(),
                        BlockRegistry.RETROSTEEL_WHITE_LIGHT.get(),
                        BlockRegistry.RETROSTEEL_SCREEN.get(),
                        BlockRegistry.RETROSTEEL_WHITE_STAIRS.get(),
                        BlockRegistry.RETROSTEEL_WHITE_SLAB.get(),
                        BlockRegistry.RETROSTEEL_BROWN_BLOCK.get(),
                        BlockRegistry.RETROSTEEL_BROWN_STAIRS.get(),
                        BlockRegistry.RETROSTEEL_BROWN_SLAB.get(),
                        BlockRegistry.RETROSTEEL_WHITE_TILES.get(),
                        BlockRegistry.RETROSTEEL_ORANGE_TILES.get(),
                        BlockRegistry.RETROSTEEL_ORANGE_PLATE.get(),
                        BlockRegistry.RETROSTEEL_METAL_BLOCK.get(),
                        BlockRegistry.RETROSTEEL_BEAMS.get(),
                        BlockRegistry.RETROSTEEL_GRATE.get(),

                        BlockRegistry.RETROSTEEL_ORE.get(),

                        BlockRegistry.RETRO_TESLA_COIL.get()
                );

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(
                        BlockRegistry.RETROSTEEL_BEAMS.get(),
                        BlockRegistry.RETRO_TESLA_COIL.get(),

                        BlockRegistry.RETROSTEEL_ORE.get()
                );

        this.tag(BlockTags.WALLS)
                .add(
                        BlockRegistry.RETROSTEEL_WHITE_WALL.get(),
                        BlockRegistry.RETROSTEEL_BROWN_WALL.get(),
                        BlockRegistry.RETROSTEEL_ORANGE_WALL.get()
                );
    }
}
