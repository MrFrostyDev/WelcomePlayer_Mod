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

            })
            .build()
    );


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
