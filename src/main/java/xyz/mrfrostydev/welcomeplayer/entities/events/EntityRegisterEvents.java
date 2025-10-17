package xyz.mrfrostydev.welcomeplayer.entities.events;

import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.eradicator.EradicatorEntity;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.handibot.HandibotEntity;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.service_bot.ServiceBotEntity;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class EntityRegisterEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(EntityRegistry.SERVICE_BOT.get(), ServiceBotEntity.createAttributes().build());
        event.put(EntityRegistry.HANDIBOT.get(), HandibotEntity.createAttributes().build());
        event.put(EntityRegistry.ERADICATOR.get(), EradicatorEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event){
        event.register(EntityRegistry.SERVICE_BOT.get(),
                SpawnPlacementTypes.ON_GROUND, Heightmap.Types.WORLD_SURFACE_WG,
                ServiceBotEntity::checkServiceBotSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }


}
