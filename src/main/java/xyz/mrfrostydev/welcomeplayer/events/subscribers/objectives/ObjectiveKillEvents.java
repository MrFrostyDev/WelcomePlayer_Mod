package xyz.mrfrostydev.welcomeplayer.events.subscribers.objectives;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.PlayerObjectives;
import xyz.mrfrostydev.welcomeplayer.registries.TagRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class ObjectiveKillEvents {

    @SubscribeEvent
    public static void onEntityKill(LivingDeathEvent event){
        if(!(event.getEntity().level() instanceof ServerLevel svlevel))return;
        if(event.getSource().getEntity()== null)return;
        if(!(event.getSource().getEntity().getType().equals(EntityType.PLAYER)))return; // Must be killed by player

        if(ObjectiveUtil.isCurrentObjective(svlevel, PlayerObjectives.ZOMBIE_KILLER)){
            if(event.getEntity().getType().equals(EntityType.ZOMBIE)){
                ObjectiveUtil.addProgress(svlevel, 1);
            }
        }
        if(ObjectiveUtil.isCurrentObjective(svlevel, PlayerObjectives.THE_BUTCHER)){
            if(event.getEntity().getType().is(TagRegistry.ANIMAL)){
                ObjectiveUtil.addProgress(svlevel, 1);
            }
        }
    }
}
