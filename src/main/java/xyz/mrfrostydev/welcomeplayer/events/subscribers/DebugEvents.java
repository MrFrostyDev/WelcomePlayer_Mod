package xyz.mrfrostydev.welcomeplayer.events.subscribers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class DebugEvents {

    @SubscribeEvent
    public static void onLivingEntityHurt(LivingDamageEvent.Post event){
        WelcomeplayerMain.LOGGER.debug("[Welcomeplayer LivingEntity Hurt]: " + event.getEntity().getName().getString() + ", " + event.getNewDamage());
    }
}
