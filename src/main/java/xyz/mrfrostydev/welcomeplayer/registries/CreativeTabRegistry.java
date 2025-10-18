package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

import java.util.function.Supplier;

public class CreativeTabRegistry {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, WelcomeplayerMain.MOD_ID);

    public static final Supplier<CreativeModeTab> WELCOMEPLAYER_TAB = CREATIVE_MODE_TABS.register("welcomeplayer_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + WelcomeplayerMain.MOD_ID + ".tab"))
            .icon(() -> new ItemStack(ItemRegistry.BOUNCE_PAD))
            .displayItems((params, output) -> {
                output.accept(ItemRegistry.RETROSTEEL_BROWN_BLOCK.get());
                output.accept(ItemRegistry.RETROSTEEL_BROWN_STAIRS.get());
                output.accept(ItemRegistry.RETROSTEEL_BROWN_SLAB.get());
                output.accept(ItemRegistry.RETROSTEEL_BROWN_WALL.get());
                output.accept(ItemRegistry.RETROSTEEL_BLEND_BLOCK.get());

                output.accept(ItemRegistry.RETROSTEEL_WHITE_SMOOTH.get());
                output.accept(ItemRegistry.RETROSTEEL_WHITE_TILES.get());
                output.accept(ItemRegistry.RETROSTEEL_WHITE_PLATE.get());
                output.accept(ItemRegistry.RETROSTEEL_WHITE_LIGHT.get());
                output.accept(ItemRegistry.RETROSTEEL_WHITE_STAIRS.get());
                output.accept(ItemRegistry.RETROSTEEL_WHITE_SLAB.get());
                output.accept(ItemRegistry.RETROSTEEL_WHITE_WALL.get());

                output.accept(ItemRegistry.RETROSTEEL_SCREEN.get());
                output.accept(ItemRegistry.RETROSTEEL_ORANGE_SMOOTH.get());
                output.accept(ItemRegistry.RETROSTEEL_ORANGE_TILES.get());
                output.accept(ItemRegistry.RETROSTEEL_ORANGE_PLATE.get());
                output.accept(ItemRegistry.RETROSTEEL_ORANGE_STAIRS.get());
                output.accept(ItemRegistry.RETROSTEEL_ORANGE_SLAB.get());
                output.accept(ItemRegistry.RETROSTEEL_ORANGE_WALL.get());
                output.accept(ItemRegistry.RETROSTEEL_METAL_BLOCK.get());
                output.accept(ItemRegistry.RETROSTEEL_METAL_TILES.get());
                output.accept(ItemRegistry.RETROSTEEL_BEAMS.get());
                output.accept(ItemRegistry.RETROSTEEL_GRATE.get());
                output.accept(ItemRegistry.RETRO_PATTERN_WOOL.get());
                output.accept(ItemRegistry.RETRO_PATTERN_CARPET.get());

                output.accept(ItemRegistry.BOUNCE_PAD.get());
                output.accept(ItemRegistry.PHASE_LINK.get());
                output.accept(ItemRegistry.STASIS_STICK.get());
                output.accept(ItemRegistry.LASER_CUTTER.get());
                output.accept(ItemRegistry.SURVEILLANCE_PAD.get());
                output.accept(ItemRegistry.SHOCK_ORB_LAUNCHER.get());

                output.accept(ItemRegistry.POTATO_BOMB.get());

                output.accept(ItemRegistry.SMALL_BATTERY.get());
                output.accept(ItemRegistry.BATTERY.get());
                output.accept(ItemRegistry.LARGE_BATTERY.get());

                output.accept(ItemRegistry.RETROSTEEL_ORE.get());
                output.accept(ItemRegistry.RAW_RETROSTEEL.get());
                output.accept(ItemRegistry.RETROSTEEL_INGOT.get());

                output.accept(ItemRegistry.SHOW_ACTIVATOR.get());
                output.accept(ItemRegistry.MATERIAL_TRANSIT.get());
                output.accept(ItemRegistry.BEACON.get());
                output.accept(ItemRegistry.VENDOR_TOP.get());
                output.accept(ItemRegistry.VENDOR_BOTTOM.get());
                output.accept(ItemRegistry.RETRO_TESLA_COIL.get());

                output.accept(ItemRegistry.SERVICE_BOT_SPAWN_EGG.get());
                output.accept(ItemRegistry.HANDIBOT_SPAWN_EGG.get());
                output.accept(ItemRegistry.ERADICATOR_SPAWN_EGG.get());
            })
            .build()
    );


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
