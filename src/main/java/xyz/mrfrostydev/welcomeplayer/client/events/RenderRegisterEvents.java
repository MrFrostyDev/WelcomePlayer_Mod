package xyz.mrfrostydev.welcomeplayer.client.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class RenderRegisterEvents {

    @SubscribeEvent
    public static void onRegisterEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event){}

    @SubscribeEvent
    public static void onEntityRendererRegister(EntityRenderersEvent.RegisterRenderers event) {

        // Block Entities

        // Entities
    }
}
