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


        stairsBlock(BlockRegistry.RETROSTEEL_WHITE_STAIRS.get(), blockTexture(BlockRegistry.RETROSTEEL_WHITE_SMOOTH.get()));
        stairsBlock(BlockRegistry.RETROSTEEL_BROWN_STAIRS.get(), blockTexture(BlockRegistry.RETROSTEEL_BROWN_BLOCK.get()));
        stairsBlock(BlockRegistry.RETROSTEEL_ORANGE_STAIRS.get(), blockTexture(BlockRegistry.RETROSTEEL_ORANGE_SMOOTH.get()));

        slabBlock(BlockRegistry.RETROSTEEL_WHITE_SLAB.get(), blockTexture(BlockRegistry.RETROSTEEL_WHITE_SMOOTH.get()), blockTexture(BlockRegistry.RETROSTEEL_WHITE_SMOOTH.get()));
        slabBlock(BlockRegistry.RETROSTEEL_BROWN_SLAB.get(), blockTexture(BlockRegistry.RETROSTEEL_BROWN_BLOCK.get()), blockTexture(BlockRegistry.RETROSTEEL_BROWN_BLOCK.get()));
        slabBlock(BlockRegistry.RETROSTEEL_ORANGE_SLAB.get(), blockTexture(BlockRegistry.RETROSTEEL_ORANGE_SMOOTH.get()), blockTexture(BlockRegistry.RETROSTEEL_ORANGE_SMOOTH.get()));

        wallBlock(BlockRegistry.RETROSTEEL_WHITE_WALL.get(), blockTexture(BlockRegistry.RETROSTEEL_WHITE_SMOOTH.get()));
        wallBlock(BlockRegistry.RETROSTEEL_BROWN_WALL.get(), blockTexture(BlockRegistry.RETROSTEEL_BROWN_BLOCK.get()));
        wallBlock(BlockRegistry.RETROSTEEL_ORANGE_WALL.get(), blockTexture(BlockRegistry.RETROSTEEL_ORANGE_SMOOTH.get()));
    }
}
