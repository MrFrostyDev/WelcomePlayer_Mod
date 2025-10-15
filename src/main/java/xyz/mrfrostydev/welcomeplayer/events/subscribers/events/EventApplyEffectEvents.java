package xyz.mrfrostydev.welcomeplayer.events.subscribers.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.events.AudienceEventStartedEvent;

import static xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents.RAIDING_PARTY;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class EventApplyEffectEvents {

    @SubscribeEvent
    public static void onStartApplyEffects(AudienceEventStartedEvent event){
        if(!(event.getLevel() instanceof ServerLevel svlevel))return;
        AudienceEvent startedEvent = event.getStartedEvent();

        if(startedEvent.is(RAIDING_PARTY)){
            svlevel.players().forEach(p -> {
                p.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 6000, 0,
                        false, false, true));
            });
        }
    }

}
