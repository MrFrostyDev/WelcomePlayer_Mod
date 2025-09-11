package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.VendorItem;

@EventBusSubscriber
public class DatapackRegistry {
    public static final ResourceKey<Registry<VendorItem>> VENDOR_ITEMS = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "flesh_merchant_items"));
    public static final ResourceKey<Registry<AudienceEvent>> AUDIENCE_EVENTS = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "flesh_lord_events"));

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {

        event.dataPackRegistry(
                VENDOR_ITEMS,
                VendorItem.CODEC,
                VendorItem.CODEC
        );

        event.dataPackRegistry(
                AUDIENCE_EVENTS,
                AudienceEvent.CODEC,
                AudienceEvent.CODEC
        );
    }
}
