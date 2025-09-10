package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

public class BlockRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, WelcomeplayerMain.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, WelcomeplayerMain.MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
        BLOCKS.register(eventBus);
    }

    // |--------------------------------------------------------------------------------------|
    // |----------------------------------------Blocks----------------------------------------|
    // |--------------------------------------------------------------------------------------|


    // |--------------------------------------------------------------------------------------|
    // |------------------------------------Block Entities------------------------------------|
    // |--------------------------------------------------------------------------------------|


}
