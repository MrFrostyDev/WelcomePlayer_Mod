package xyz.mrfrostydev.welcomeplayer.events.subscribers;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceMood;
import xyz.mrfrostydev.welcomeplayer.registries.TagRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class MoodEvents {

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event){
        if(!(event.getEntity().level() instanceof ServerLevel svlevel)) return;

        LivingEntity target = event.getEntity();
        AudienceMood mood = AudienceUtil.getMood(svlevel);

        if(!target.getType().equals(EntityType.PLAYER)) return;

        if(mood.equals(AudienceMood.SAD)){
            AudienceUtil.addInterestRaw(svlevel, 5);
        }
        else if(mood.equals(AudienceMood.ANGRY)){
            AudienceUtil.addInterestRaw(svlevel, 2);
        }
        else if(mood.equals(AudienceMood.CRUEL)){
            AudienceUtil.addInterestRaw(svlevel, 5);
        }
    }

    @SubscribeEvent
    public static void onAnimalKill(LivingDeathEvent event){
        if(!(event.getEntity().level() instanceof ServerLevel svlevel)) return;

        LivingEntity target = event.getEntity();
        AudienceMood mood = AudienceUtil.getMood(svlevel);

        if(!target.getType().is(TagRegistry.ANIMAL)) return;

        else if(mood.equals(AudienceMood.SAD)){
            AudienceUtil.addInterestRaw(svlevel, -2);
        }
        else if(mood.equals(AudienceMood.CRUEL)){
            AudienceUtil.addInterestRaw(svlevel, 2);
        }
    }

    @SubscribeEvent
    public static void onScaryKill(LivingDeathEvent event){
        if(!(event.getEntity().level() instanceof ServerLevel svlevel)) return;

        LivingEntity target = event.getEntity();
        AudienceMood mood = AudienceUtil.getMood(svlevel);

        if(!target.getType().is(TagRegistry.SCARY)) return;

        else if(mood.equals(AudienceMood.SAD)){
            AudienceUtil.addInterestRaw(svlevel, 2);
        }
        else if(mood.equals(AudienceMood.CRUEL)){
            AudienceUtil.addInterestRaw(svlevel, -2);
        }
    }
}
