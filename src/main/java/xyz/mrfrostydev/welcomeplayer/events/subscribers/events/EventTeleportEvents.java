package xyz.mrfrostydev.welcomeplayer.events.subscribers.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents;
import xyz.mrfrostydev.welcomeplayer.events.AudienceEventEndEvent;
import xyz.mrfrostydev.welcomeplayer.events.AudienceEventStartedEvent;
import xyz.mrfrostydev.welcomeplayer.events.subscribers.WorldTickEvents;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceEventUtil;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class EventTeleportEvents {

    @SubscribeEvent
    public static void onEventStartTeleport(AudienceEventStartedEvent event){
        if(!(event.getLevel() instanceof ServerLevel svlevel))return;
        AudienceEvent startedEvent = event.getStartedEvent();

        if(startedEvent.is(AudienceEvents.WARP_MALFUNCTION)){
            for (ServerPlayer player : svlevel.getPlayers( p -> !p.isSpectator())) {
                randomTeleport(svlevel, player);
            }
        }
    }

    @SubscribeEvent
    public static void onEventTickTeleport(LevelTickEvent.Post event){
        if(!(event.getLevel() instanceof ServerLevel svlevel))return;
        AudienceEvent goingEvent = AudienceEventUtil.getGoingEvent(svlevel);

        int tickCount = svlevel.getServer().getTickCount();
        if(goingEvent.is(AudienceEvents.WARP_MALFUNCTION)
                && (tickCount == WorldTickEvents.MOOD_CHANGE_COOLDOWN / 3) || tickCount == WorldTickEvents.MOOD_CHANGE_COOLDOWN - WorldTickEvents.MOOD_CHANGE_COOLDOWN / 3){
            for (ServerPlayer player : svlevel.getPlayers( p -> !p.isSpectator())) {
                randomTeleport(svlevel, player);
            }
        }
    }

    @SubscribeEvent
    public static void onEventEndTeleport(AudienceEventEndEvent event){
        if(!(event.getLevel() instanceof ServerLevel svlevel))return;
        AudienceEvent endedEvent = event.getEndedEvent();

        if(endedEvent.is(AudienceEvents.WARP_MALFUNCTION)){
            for (ServerPlayer player : svlevel.getPlayers( p -> !p.isSpectator())) {
                randomTeleport(svlevel, player);
            }
        }
    }

    // uses LivingEntity#randomTeleport
    public static void randomTeleport(ServerLevel svlevel, Player player) {
        double randX = player.getX() + (svlevel.random.nextDouble() - 0.5) * 64.0;
        double randY = player.getY() + (double)(svlevel.random.nextInt(64) - 32);
        double randZ = player.getZ() + (svlevel.random.nextDouble() - 0.5) * 64.0;

        for(int i=0;i<50;i++){
            if(player.randomTeleport(randX, randY, randZ, true)){
                svlevel.playSound(null,
                        player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS,
                        1.0F, 1.0F);
                return;
            }
        }
    }
}
