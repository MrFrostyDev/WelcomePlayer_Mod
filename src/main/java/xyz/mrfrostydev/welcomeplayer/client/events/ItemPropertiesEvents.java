package xyz.mrfrostydev.welcomeplayer.client.events;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.items.gadgets.LaserCutterItem;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID, value = Dist.CLIENT)
public class ItemPropertiesEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(
                    ItemRegistry.LASER_CUTTER.get(),
                    ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "charging"),
                    (stack, level, player, seed) ->
                            LaserCutterItem.isCharging(stack) ? 1.0F : 0.0F
            );
        });
    }
}
