package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;

public class BlockStateProvider extends net.neoforged.neoforge.client.model.generators.BlockStateProvider {
    public BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, WelcomeplayerMain.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {


        stairsBlock(BlockRegistry.RETROSTEEL_WHITE_STAIRS.get(), blockTexture(BlockRegistry.RETROSTEEL_WHITE_WALL.get()));
        stairsBlock(BlockRegistry.RETROSTEEL_BROWN_STAIRS.get(), blockTexture(BlockRegistry.RETROSTEEL_BROWN_WALL.get()));

        slabBlock(BlockRegistry.RETROSTEEL_WHITE_SLAB.get(), blockTexture(BlockRegistry.RETROSTEEL_WHITE_WALL.get()), blockTexture(BlockRegistry.RETROSTEEL_WHITE_WALL.get()));
        slabBlock(BlockRegistry.RETROSTEEL_BROWN_SLAB.get(), blockTexture(BlockRegistry.RETROSTEEL_BROWN_WALL.get()), blockTexture(BlockRegistry.RETROSTEEL_BROWN_WALL.get()));

    }
}
