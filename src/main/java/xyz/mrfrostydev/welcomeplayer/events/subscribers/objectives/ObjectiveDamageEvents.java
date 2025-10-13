package xyz.mrfrostydev.welcomeplayer.events.subscribers.objectives;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.PlayerObjectives;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class ObjectiveDamageEvents {

    @SubscribeEvent
    public static void onPlayerDamagedObjective(LivingDamageEvent.Post event){
        LivingEntity victim = event.getEntity();
        if(!(victim.level() instanceof ServerLevel svlevel))return;
        if(!(victim.getType().equals(EntityType.PLAYER)))return;

        PlayerObjective obj = ObjectiveUtil.getGoingObjective(svlevel);
        float damage = event.getNewDamage();
        if(
                obj.is(svlevel, PlayerObjectives.SUFFERING)
                || obj.is(svlevel, PlayerObjectives.PAIN)
                || obj.is(svlevel, PlayerObjectives.CRUEL_SUFFERING)
        ){
            if(victim.getType().equals(EntityType.PLAYER)){
                ObjectiveUtil.addProgress(svlevel, (int)damage);
            }
        }
    }
}
