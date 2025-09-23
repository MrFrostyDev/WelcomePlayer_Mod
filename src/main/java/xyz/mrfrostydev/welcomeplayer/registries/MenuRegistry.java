package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.client.gui.menus.blocks.VendorMenu;

import java.util.function.Supplier;


public class MenuRegistry {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, WelcomeplayerMain.MOD_ID);

    public static void register(IEventBus event){
            MENUS.register(event);
    }

    public static final Supplier<MenuType<VendorMenu>> VENDOR_MENU = MENUS.register(
            "vendor_menu",
            () -> IMenuTypeExtension.create(VendorMenu::new)
    );

}
