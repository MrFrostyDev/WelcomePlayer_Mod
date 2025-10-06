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


public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, WelcomeplayerMain.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    // |-----------------------------------------------------------------------------------|
    // |-------------------------------------Gadgets---------------------------------------|
    // |-----------------------------------------------------------------------------------|
    public static final DeferredHolder<Item, Item> BOUNCE_PAD = ITEMS.register("bounce_pad", () -> new BouncePadItem(new Item.Properties().stacksTo(8)));

    // |-----------------------------------------------------------------------------------|
    // |------------------------------------Block Items------------------------------------|
    // |-----------------------------------------------------------------------------------|
    public static final DeferredHolder<Item, Item> SHOW_ACTIVATOR = ITEMS.register("show_activator", () -> new BlockItem(BlockRegistry.SHOW_ACTIVATOR.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> MATERIAL_TRANSIT = ITEMS.register("material_transit", () -> new GeoBlockItem(BlockRegistry.MATERIAL_TRANSIT.get(), "material_transit", new Item.Properties()));
    public static final DeferredHolder<Item, Item> BEACON = ITEMS.register("beacon", () -> new GeoBlockItem(BlockRegistry.BEACON.get(), "beacon", new Item.Properties()));
    public static final DeferredHolder<Item, Item> VENDOR_TOP = ITEMS.register("vendor_top", () -> new BlockItem(BlockRegistry.VENDOR_TOP.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> VENDOR_BOTTOM = ITEMS.register("vendor_bottom", () -> new BlockItem(BlockRegistry.VENDOR_BOTTOM.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> RETROSTEEL_BLEND_WALL = ITEMS.register("retrosteel_blend_wall", () -> new BlockItem(BlockRegistry.RETROSTEEL_BLEND_WALL.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RETROSTEEL_WHITE_WALL = ITEMS.register("retrosteel_white_wall", () -> new BlockItem(BlockRegistry.RETROSTEEL_WHITE_WALL.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RETROSTEEL_BROWN_WALL = ITEMS.register("retrosteel_brown_wall", () -> new BlockItem(BlockRegistry.RETROSTEEL_BROWN_WALL.get(), new Item.Properties()));


    // |-----------------------------------------------------------------------------------|
    // |--------------------------------------Armors---------------------------------------|
    // |-----------------------------------------------------------------------------------|


    // |-----------------------------------------------------------------------------------|
    // |------------------------------------Misc Items-------------------------------------|
    // |-----------------------------------------------------------------------------------|
    public static final DeferredHolder<Item, Item> SMALL_BATTERY = ITEMS.register("small_battery", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BATTERY = ITEMS.register("battery", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredHolder<Item, Item> LARGE_BATTERY = ITEMS.register("large_battery", () -> new Item(new Item.Properties().stacksTo(2)));

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
