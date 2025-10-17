package xyz.mrfrostydev.welcomeplayer.events.subscribers.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.AnimalTameEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.PlayerObjectives;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class EventTameEvents {

    @SubscribeEvent
    public static void onTame(AnimalTameEvent event){
        Animal animal = event.getAnimal();

        if(animal.level() instanceof ServerLevel svlevel){
            PlayerObjective obj = ObjectiveUtil.getGoingObjective(svlevel);
            if(obj.is(svlevel, PlayerObjectives.DOG_PERSON) && animal.getType().equals(EntityType.WOLF)){
                ObjectiveUtil.addProgress(svlevel, 1);
            }
            else if(obj.is(svlevel, PlayerObjectives.CAT_PERSON) && animal.getType().equals(EntityType.CAT)){
                ObjectiveUtil.addProgress(svlevel, 1);
            }
        }
    }
}
