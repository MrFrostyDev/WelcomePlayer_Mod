package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.items.GeoBlockItem;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.items.gadgets.BouncePadItem;
import xyz.mrfrostydev.welcomeplayer.items.gadgets.PhaseLinkItem;
import xyz.mrfrostydev.welcomeplayer.items.gadgets.StasisStickItem;
import xyz.mrfrostydev.welcomeplayer.items.gadgets.SurveillancePadItem;


public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, WelcomeplayerMain.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    // |-----------------------------------------------------------------------------------|
    // |-------------------------------------Gadgets---------------------------------------|
    // |-----------------------------------------------------------------------------------|
    public static final DeferredHolder<Item, Item> BOUNCE_PAD = ITEMS.register("bounce_pad", () -> new BouncePadItem(new Item.Properties().stacksTo(8)));
    public static final DeferredHolder<Item, Item> PHASE_LINK = ITEMS.register("phase_link", () -> new PhaseLinkItem(new Item.Properties().stacksTo(1), 5));
    public static final DeferredHolder<Item, Item> STASIS_STICK = ITEMS.register("stasis_stick", () -> new StasisStickItem(new Item.Properties().attributes(StasisStickItem.createAttributes(5.0F, -3.1F)).stacksTo(1)));
    public static final DeferredHolder<Item, Item> SURVEILLANCE_PAD = ITEMS.register("surveillance_pad", () -> new SurveillancePadItem(new Item.Properties().stacksTo(1)));

    // |-----------------------------------------------------------------------------------|
    // |------------------------------------Block Items------------------------------------|
    // |-----------------------------------------------------------------------------------|
    public static final DeferredHolder<Item, Item> SHOW_ACTIVATOR = ITEMS.register("show_activator", () -> new BlockItem(BlockRegistry.SHOW_ACTIVATOR.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> HOST_SCREEN = ITEMS.register("host_screen", () -> new BlockItem(BlockRegistry.HOST_SCREEN.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> MATERIAL_TRANSIT = ITEMS.register("material_transit", () -> new GeoBlockItem(BlockRegistry.MATERIAL_TRANSIT.get(), "material_transit", new Item.Properties()));
    public static final DeferredHolder<Item, Item> BEACON = ITEMS.register("beacon", () -> new GeoBlockItem(BlockRegistry.BEACON.get(), "beacon", new Item.Properties()));
    public static final DeferredHolder<Item, Item> VENDOR_TOP = ITEMS.register("vendor_top", () -> new BlockItem(BlockRegistry.VENDOR_TOP.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> VENDOR_BOTTOM = ITEMS.register("vendor_bottom", () -> new BlockItem(BlockRegistry.VENDOR_BOTTOM.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RETRO_TESLA_COIL = ITEMS.register("retro_tesla_coil", () -> new GeoBlockItem(BlockRegistry.RETRO_TESLA_COIL.get(), "retro_tesla_coil", new Item.Properties()));


    public static final DeferredHolder<Item, Item> RETROSTEEL_BLEND_WALL = ITEMS.register("retrosteel_blend_wall", () -> new BlockItem(BlockRegistry.RETROSTEEL_BLEND_WALL.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RETROSTEEL_WHITE_WALL = ITEMS.register("retrosteel_white_wall", () -> new BlockItem(BlockRegistry.RETROSTEEL_WHITE_WALL.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RETROSTEEL_BROWN_WALL = ITEMS.register("retrosteel_brown_wall", () -> new BlockItem(BlockRegistry.RETROSTEEL_BROWN_WALL.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RETROSTEEL_ORANGE_TILES = ITEMS.register("retrosteel_orange_tiles", () -> new BlockItem(BlockRegistry.RETROSTEEL_ORANGE_TILES.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RETROSTEEL_WHITE_TILES = ITEMS.register("retrosteel_white_tiles", () -> new BlockItem(BlockRegistry.RETROSTEEL_WHITE_TILES.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RETROSTEEL_METAL_BLOCK = ITEMS.register("retrosteel_metal_block", () -> new BlockItem(BlockRegistry.RETROSTEEL_METAL_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RETROSTEEL_BEAMS = ITEMS.register("retrosteel_beams", () -> new BlockItem(BlockRegistry.RETROSTEEL_BEAMS.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> RETRO_PATTERN_WOOL = ITEMS.register("retro_pattern_wool", () -> new BlockItem(BlockRegistry.RETRO_PATTERN_WOOL.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RETRO_PATTERN_CARPET = ITEMS.register("retro_pattern_carpet", () -> new BlockItem(BlockRegistry.RETRO_PATTERN_CARPET.get(), new Item.Properties()));

    // |-----------------------------------------------------------------------------------|
    // |--------------------------------------Armors---------------------------------------|
    // |-----------------------------------------------------------------------------------|


    // |-----------------------------------------------------------------------------------|
    // |------------------------------------Misc Items-------------------------------------|
    // |-----------------------------------------------------------------------------------|
    public static final DeferredHolder<Item, Item> RETROSTEEL_INGOT = ITEMS.register("retrosteel_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> RAW_RETROSTEEL = ITEMS.register("raw_retrosteel", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> RETROSTEEL_ORE = ITEMS.register("retrosteel_ore", () -> new BlockItem(BlockRegistry.RETROSTEEL_ORE.get() ,new Item.Properties()));


    public static final DeferredHolder<Item, Item> SMALL_BATTERY = ITEMS.register("small_battery", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BATTERY = ITEMS.register("battery", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredHolder<Item, Item> LARGE_BATTERY = ITEMS.register("large_battery", () -> new Item(new Item.Properties().stacksTo(2)));

    public static final DeferredHolder<Item, Item> SERVICE_BOT_SPAWN_EGG = ITEMS.register("service_bot_spawn_egg", () -> new DeferredSpawnEggItem(
            EntityRegistry.SERVICE_BOT,0xf26d07, 0xf6d5c1, new Item.Properties()
    ));
    public static final DeferredHolder<Item, Item> HANDIBOT_SPAWN_EGG = ITEMS.register("handibot_spawn_egg", () -> new DeferredSpawnEggItem(
            EntityRegistry.HANDIBOT,0xf26d07, 0x2e453b, new Item.Properties()
    ));
    public static final DeferredHolder<Item, Item> ERADICATOR_SPAWN_EGG = ITEMS.register("eradicator_spawn_egg", () -> new DeferredSpawnEggItem(
            EntityRegistry.ERADICATOR,0xf26d07, 0xffb429, new Item.Properties()
    ));

    // |----------------------------------------------------------------------------------|
    // |------------------------------------Methods---------------------------------------|
    // |----------------------------------------------------------------------------------|

}
