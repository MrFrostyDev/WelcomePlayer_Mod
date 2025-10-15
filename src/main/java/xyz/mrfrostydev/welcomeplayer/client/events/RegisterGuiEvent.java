package xyz.mrfrostydev.welcomeplayer.client.events;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.client.gui.menus.blocks.MaterialTransitScreen;
import xyz.mrfrostydev.welcomeplayer.client.gui.menus.blocks.VendorScreen;
import xyz.mrfrostydev.welcomeplayer.client.gui.overlays.CommercialOverlay;
import xyz.mrfrostydev.welcomeplayer.client.gui.overlays.ObjectiveTrackerOverlay;
import xyz.mrfrostydev.welcomeplayer.client.gui.overlays.ShowHostMessageOverlay;
import xyz.mrfrostydev.welcomeplayer.registries.MenuRegistry;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID, value = Dist.CLIENT)
public class RegisterGuiEvent {

    @SubscribeEvent
    private static void onRegisterMenu(final RegisterMenuScreensEvent event) {
        event.register(MenuRegistry.VENDOR_MENU.get(), VendorScreen::new);
        event.register(MenuRegistry.MATERIAL_TRANSIT_MENU.get(), MaterialTransitScreen::new);
    }

    @SubscribeEvent
    public static void onRegisterGui(final RegisterGuiLayersEvent event){
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "commercial_overlay"), new CommercialOverlay());
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "show_host_message_overlay"), new ShowHostMessageOverlay());
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "objective_tracker_overlay"), new ObjectiveTrackerOverlay());
    }
}
