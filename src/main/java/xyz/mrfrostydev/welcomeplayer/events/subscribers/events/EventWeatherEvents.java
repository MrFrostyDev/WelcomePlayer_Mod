package xyz.mrfrostydev.welcomeplayer.events.subscribers.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents;
import xyz.mrfrostydev.welcomeplayer.events.AudienceEventStartedEvent;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceEventUtil;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class EventWeatherEvents {

    @SubscribeEvent
    public static void onEventStart(AudienceEventStartedEvent event){
        if(!(event.getLevel() instanceof ServerLevel svlevel))return;
        AudienceEvent startedEvent = event.getStartedEvent();

        if(startedEvent.is(AudienceEvents.TOXIC_RAIN)){
            int duration = ServerLevel.RAIN_DURATION.sample(svlevel.getServer().overworld().getRandom());
            svlevel.getServer().overworld().setWeatherParameters(0, duration, true, false);
        }
        else if(startedEvent.is(AudienceEvents.SAD_RAIN)){
            int duration = ServerLevel.RAIN_DURATION.sample(svlevel.getServer().overworld().getRandom());
            svlevel.getServer().overworld().setWeatherParameters(0, duration, true, false);
        }
        else if(startedEvent.is(AudienceEvents.ROARING_THUNDER)){
            int duration = ServerLevel.RAIN_DURATION.sample(svlevel.getServer().overworld().getRandom());
            svlevel.getServer().overworld().setWeatherParameters(0, duration, true, true);
        }
    }

    @SubscribeEvent
    public static void onEntityTickInRainOrWater(EntityTickEvent.Post event){
        if(!(event.getEntity().level() instanceof ServerLevel svlevel))return;
        if(!(event.getEntity() instanceof LivingEntity target))return;
        if(!event.getEntity().isInWaterOrRain())return;

        AudienceEvent goingEvent = AudienceEventUtil.getGoingEvent(svlevel);
        if(goingEvent.is(AudienceEvents.TOXIC_RAIN) && !target.hasEffect(MobEffects.POISON)){
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 0, false , false,true));
        }
        else if(goingEvent.is(AudienceEvents.SAD_RAIN)){
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 0, false , false,true));
        }
    }
}
