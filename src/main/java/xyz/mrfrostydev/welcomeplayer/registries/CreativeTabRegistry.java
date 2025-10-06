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
            .icon(() -> new ItemStack(Items.GLOWSTONE))
            .displayItems((params, output) -> {
                output.accept(ItemRegistry.RETROSTEEL_WHITE_WALL.get());
                output.accept(ItemRegistry.RETROSTEEL_BROWN_WALL.get());
                output.accept(ItemRegistry.RETROSTEEL_BLEND_WALL.get());

                output.accept(ItemRegistry.BOUNCE_PAD.get());

                output.accept(ItemRegistry.SMALL_BATTERY.get());
                output.accept(ItemRegistry.BATTERY.get());
                output.accept(ItemRegistry.LARGE_BATTERY.get());

                output.accept(ItemRegistry.SHOW_ACTIVATOR.get());
                output.accept(ItemRegistry.MATERIAL_TRANSIT.get());
                output.accept(ItemRegistry.BEACON.get());
                output.accept(ItemRegistry.VENDOR_TOP.get());
                output.accept(ItemRegistry.VENDOR_BOTTOM.get());

                output.accept(ItemRegistry.HANDIBOT_SPAWN_EGG.get());
                output.accept(ItemRegistry.ERADICATOR_SPAWN_EGG.get());
            })
            .build()
    );


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
