package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.AudienceReward;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.data.VendorItem;

@EventBusSubscriber
public class DatapackRegistry {
    public static final ResourceKey<Registry<VendorItem>> VENDOR_ITEMS = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "vendor_items"));
    public static final ResourceKey<Registry<AudienceEvent>> AUDIENCE_EVENTS = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "audience_events"));
    public static final ResourceKey<Registry<AudienceReward>> AUDIENCE_REWARDS = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "audience_rewards"));
    public static final ResourceKey<Registry<PlayerObjective>> PLAYER_OBJECTIVES = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "player_objectives"));

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

        event.dataPackRegistry(
                AUDIENCE_REWARDS,
                AudienceReward.CODEC,
                AudienceReward.CODEC
        );

        event.dataPackRegistry(
                PLAYER_OBJECTIVES,
                PlayerObjective.CODEC,
                PlayerObjective.CODEC
        );
    }
}
