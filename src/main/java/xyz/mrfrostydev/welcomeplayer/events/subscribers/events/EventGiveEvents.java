package xyz.mrfrostydev.welcomeplayer.events.subscribers.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.events.AudienceEventStartedEvent;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;

import static xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents.HOT_POTATO;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class EventGiveEvents {

    @SubscribeEvent
    public static void onGiveItem(AudienceEventStartedEvent event){
        if(!(event.getLevel() instanceof ServerLevel svlevel))return;
        AudienceEvent startedEvent = event.getStartedEvent();

        if(startedEvent.is(HOT_POTATO)){
            svlevel.players().forEach(p -> {
                p.getInventory().add(new ItemStack(ItemRegistry.POTATO_BOMB, 1));
            });
        }
    }
}
