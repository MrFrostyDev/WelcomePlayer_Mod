package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.blocks.BeaconBlock;
import xyz.mrfrostydev.welcomeplayer.blocks.MaterialTransitBlock;
import xyz.mrfrostydev.welcomeplayer.blocks.ShowActivatorBlock;
import xyz.mrfrostydev.welcomeplayer.blocks.VendorBlock;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.BeaconBlockEntity;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.MaterialTransitBlockEntity;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.VendorBlockEntity;

import java.util.function.Supplier;

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

    public static final DeferredHolder<Block, Block> SHOW_ACTIVATOR = BLOCKS.register(
            "show_activator",
            () -> new ShowActivatorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .pushReaction(PushReaction.IGNORE)
                    .isValidSpawn(Blocks::never)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_BLEND_WALL = BLOCKS.register(
            "retrosteel_blend_wall",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_WHITE_WALL = BLOCKS.register(
            "retrosteel_white_wall",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_BROWN_WALL = BLOCKS.register(
            "retrosteel_brown_wall",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    // |--------------------------------------------------------------------------------------|
    // |------------------------------------Block Entities------------------------------------|
    // |--------------------------------------------------------------------------------------|

    public static final DeferredHolder<Block, Block> VENDOR_TOP = BLOCKS.register(
            "vendor_top",
            () -> new VendorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(40F, 3600000.0F)
                    .noLootTable()
                    .pushReaction(PushReaction.IGNORE)
                    .isValidSpawn(Blocks::never),
                    true
            )
    );

    public static final DeferredHolder<Block, Block> VENDOR_BOTTOM = BLOCKS.register(
            "vendor_bottom",
            () -> new VendorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(40F, 3600000.0F)
                    .noLootTable()
                    .pushReaction(PushReaction.IGNORE)
                    .isValidSpawn(Blocks::never),
                    false
            )
    );

    public static final Supplier<BlockEntityType<VendorBlockEntity>> VENDOR_ENTITY = BLOCK_ENTITY_TYPES.register(
            "vendor_entity",
            () -> BlockEntityType.Builder.of(
                    VendorBlockEntity::new,
                    VENDOR_BOTTOM.get()
            ).build(null)
    );

    public static final DeferredHolder<Block, Block> BEACON = BLOCKS.register(
            "beacon",
            () -> new BeaconBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(MapColor.METAL)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .pushReaction(PushReaction.IGNORE)
                    .isValidSpawn(Blocks::never)
            )
    );

    public static final Supplier<BlockEntityType<BeaconBlockEntity>> BEACON_ENTITY = BLOCK_ENTITY_TYPES.register(
            "beacon_entity",
            () -> BlockEntityType.Builder.of(
                    BeaconBlockEntity::new,
                    BEACON.get()
            ).build(null)
    );

    public static final DeferredHolder<Block, Block> MATERIAL_TRANSIT = BLOCKS.register(
            "material_transit",
            () -> new MaterialTransitBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(MapColor.METAL)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .pushReaction(PushReaction.IGNORE)
                    .isValidSpawn(Blocks::never)
            )
    );

    public static final Supplier<BlockEntityType<MaterialTransitBlockEntity>> MATERIAL_TRANSIT_ENTITY = BLOCK_ENTITY_TYPES.register(
            "material_transit_entity",
            () -> BlockEntityType.Builder.of(
                    MaterialTransitBlockEntity::new,
                    MATERIAL_TRANSIT.get()
            ).build(null)
    );

}
