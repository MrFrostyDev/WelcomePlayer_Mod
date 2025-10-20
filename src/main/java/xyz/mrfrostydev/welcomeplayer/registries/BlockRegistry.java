package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.blocks.*;
import xyz.mrfrostydev.welcomeplayer.blocks.BeaconBlock;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.*;

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

    public static final DeferredHolder<Block, Block> RETROSTEEL_BLEND_BLOCK = BLOCKS.register(
            "retrosteel_blend_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_WHITE_SMOOTH = BLOCKS.register(
            "retrosteel_white_smooth",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, StairBlock> RETROSTEEL_WHITE_STAIRS = BLOCKS.register(
            "retrosteel_white_stairs",
            () -> new StairBlock(RETROSTEEL_WHITE_SMOOTH.get().defaultBlockState(), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, SlabBlock> RETROSTEEL_WHITE_SLAB = BLOCKS.register(
            "retrosteel_white_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, WallBlock> RETROSTEEL_WHITE_WALL = BLOCKS.register(
            "retrosteel_white_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_BROWN_BLOCK = BLOCKS.register(
            "retrosteel_brown_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, StairBlock> RETROSTEEL_BROWN_STAIRS = BLOCKS.register(
            "retrosteel_brown_stairs",
            () -> new StairBlock(RETROSTEEL_BROWN_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, SlabBlock> RETROSTEEL_BROWN_SLAB = BLOCKS.register(
            "retrosteel_brown_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, WallBlock> RETROSTEEL_BROWN_WALL = BLOCKS.register(
            "retrosteel_brown_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_ORANGE_SMOOTH = BLOCKS.register(
            "retrosteel_orange_smooth",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_ORANGE_TILES = BLOCKS.register(
            "retrosteel_orange_tiles",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, StairBlock> RETROSTEEL_ORANGE_STAIRS = BLOCKS.register(
            "retrosteel_orange_stairs",
            () -> new StairBlock(RETROSTEEL_ORANGE_SMOOTH.get().defaultBlockState(), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, SlabBlock> RETROSTEEL_ORANGE_SLAB = BLOCKS.register(
            "retrosteel_orange_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, WallBlock> RETROSTEEL_ORANGE_WALL = BLOCKS.register(
            "retrosteel_orange_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_WHITE_TILES = BLOCKS.register(
            "retrosteel_white_tiles",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_WHITE_PLATE = BLOCKS.register(
            "retrosteel_white_plate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_ORANGE_PLATE = BLOCKS.register(
            "retrosteel_orange_plate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_WHITE_LIGHT = BLOCKS.register(
            "retrosteel_white_light",
            () ->  new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.HAT)
                    .requiresCorrectToolForDrops()
                    .strength(6.0F, 3.0F)
                    .lightLevel(s -> 15)
                    .sound(SoundType.METAL)
                    .isRedstoneConductor((s, g, p) -> false)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_SCREEN = BLOCKS.register(
            "retrosteel_screen",
            () ->  new HorizontalFacingBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(6.0F, 3.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_METAL_BLOCK = BLOCKS.register(
            "retrosteel_metal_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_METAL_TILES = BLOCKS.register(
            "retrosteel_metal_tiles",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_BEAMS = BLOCKS.register(
            "retrosteel_beams",
            () -> new Block(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETROSTEEL_GRATE = BLOCKS.register(
            "retrosteel_grate",
            () ->  new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(8.0F, 6.0F)
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredHolder<Block, Block> RETRO_PATTERN_WOOL = BLOCKS.register(
            "retro_pattern_wool",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()
            )
    );

    public static final DeferredHolder<Block, Block> RETRO_KEYPAD = BLOCKS.register(
            "retro_keypad",
            () -> new ButtonPanelBlock(BlockSetType.STONE, 10,
                    BlockBehaviour.Properties.of()
                            .noCollission()
                            .strength(1.0F)
                            .pushReaction(PushReaction.DESTROY)
            )
    );

    // |--------------------------------------------------------------------------------------|
    // |-------------------------------------Decorations--------------------------------------|
    // |--------------------------------------------------------------------------------------|

    public static final DeferredHolder<Block, Block> RETRO_PATTERN_CARPET = BLOCKS.register(
            "retro_pattern_carpet",
            () -> new WoolCarpetBlock(
                    DyeColor.BROWN,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BROWN)
                            .strength(0.1F).sound(SoundType.WOOL)
                            .ignitedByLava()
            )
    );

    // |--------------------------------------------------------------------------------------|
    // |----------------------------------------Metal-----------------------------------------|
    // |--------------------------------------------------------------------------------------|

    public static final DeferredHolder<Block, Block> RETROSTEEL_ORE = BLOCKS.register(
            "retrosteel_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 3.0F)
                    .sound(SoundType.AMETHYST)
            )
    );


    // |--------------------------------------------------------------------------------------|
    // |------------------------------------Block Entities------------------------------------|
    // |--------------------------------------------------------------------------------------|

    public static final DeferredHolder<Block, Block> HOST_SCREEN = BLOCKS.register(
            "host_screen",
            () -> new HostScreenBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(MapColor.METAL)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .pushReaction(PushReaction.IGNORE)
                    .isValidSpawn(Blocks::never)
            )
    );

    public static final Supplier<BlockEntityType<HostScreenBlockEntity>> HOST_SCREEN_ENTITY = BLOCK_ENTITY_TYPES.register(
            "host_screen_entity",
            () -> BlockEntityType.Builder.of(
                    HostScreenBlockEntity::new,
                    HOST_SCREEN.get()
            ).build(null)
    );

    public static final DeferredHolder<Block, Block> VENDOR_TOP = BLOCKS.register(
            "vendor_top",
            () -> new VendorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(20F, 8.0F)
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
                    .strength(20F, 8.0F)
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

    public static final DeferredHolder<Block, Block> RETRO_TESLA_COIL = BLOCKS.register(
            "retro_tesla_coil",
            () -> new TeslaBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(MapColor.METAL)
                    .strength(16.0F, 6.0F)
                    .pushReaction(PushReaction.IGNORE)
            )
    );

    public static final Supplier<BlockEntityType<TeslaBlockEntity>> RETRO_TESLA_COIL_ENTITY = BLOCK_ENTITY_TYPES.register(
            "retro_tesla_coil_entity",
            () -> BlockEntityType.Builder.of(
                    TeslaBlockEntity::new,
                    RETRO_TESLA_COIL.get()
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
