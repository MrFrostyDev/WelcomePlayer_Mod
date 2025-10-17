package xyz.mrfrostydev.welcomeplayer.events.subscribers.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceEventUtil;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class EventSpawnEvents {

    @SubscribeEvent
    public static void onEventStart(FinalizeSpawnEvent event){
        if(!(event.getEntity().level() instanceof ServerLevel svlevel))return;
        Mob mob = event.getEntity();
        AudienceEvent goingEvent = AudienceEventUtil.getGoingEvent(svlevel);

        if(goingEvent.is(AudienceEvents.SPEED_SUBJECTS)){
            AttributeInstance inst = mob.getAttribute(Attributes.MOVEMENT_SPEED);
            if(inst != null){
                AttributeModifier modifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "speed_subjects_movespeed_modifier"),
                        0.5F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                inst.addOrReplacePermanentModifier(modifier);
            }
        }
    }
}
