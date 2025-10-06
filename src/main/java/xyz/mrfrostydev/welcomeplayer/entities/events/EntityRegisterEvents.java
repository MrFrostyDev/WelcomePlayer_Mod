package xyz.mrfrostydev.welcomeplayer.entities.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.eradicator.EradicatorEntity;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.handibot.HandibotEntity;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class EntityRegisterEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(EntityRegistry.HANDIBOT.get(), HandibotEntity.createAttributes().build());
        event.put(EntityRegistry.ERADICATOR.get(), EradicatorEntity.createAttributes().build());
    }

}
