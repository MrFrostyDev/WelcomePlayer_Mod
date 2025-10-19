package xyz.mrfrostydev.welcomeplayer.events.subscribers;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.network.SyncAudienceDataSmallPacket;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceEventUtil;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;
import xyz.mrfrostydev.welcomeplayer.utils.VendorUtil;

import java.util.List;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class WorldTickEvents {
    public static final int TICK_COOLDOWN = 200;
    public static final int MOOD_CHANGE_COOLDOWN = 12000;

    @SubscribeEvent
    public static void onWorldTickPost(LevelTickEvent.Post event){
        if(!(event.getLevel() instanceof ServerLevel svlevel))return;
        List<? extends Player> playerList = event.getLevel().players().stream().toList();
        int increaseByPlayer = Math.floorDiv(playerList.size(), 2);
        int levelTickCount = event.getLevel().getServer().getTickCount();

        VendorUtil.doTick(svlevel);

        if(!(AudienceUtil.isActive(svlevel)))return;

        AudienceUtil.doTick(svlevel);
        ObjectiveUtil.doTick(svlevel);

        if(levelTickCount % TICK_COOLDOWN == 0){
            AudienceData data = AudienceUtil.getAudienceData(svlevel);
            AudienceUtil.addInterestRaw(svlevel, -1 + increaseByPlayer);

            PacketDistributor.sendToAllPlayers(SyncAudienceDataSmallPacket.create(data));

            if(AudienceUtil.isPhaseShifting(svlevel)){
                if(!AudienceUtil.isChangeCooldown(svlevel)){
                    AudienceUtil.pickMood(svlevel);
                    AudienceEventUtil.pickEvent(svlevel);
                    AudienceUtil.restartPhaseShift(svlevel);
                    AudienceUtil.startChangeCooldown(svlevel);
                    AudienceUtil.syncToClients(svlevel);
                }
            }
        }

        // Try to pick a random mood and event every day.
        if(levelTickCount % MOOD_CHANGE_COOLDOWN == 0){
            if(!AudienceUtil.isChangeCooldown(svlevel)){
                AudienceUtil.pickMood(svlevel);
                AudienceEventUtil.pickEvent(svlevel);
                AudienceUtil.startChangeCooldown(svlevel);
                AudienceUtil.syncToClients(svlevel);
            }
        }

        // Try to pick a new objective if one isn't active.
        if(levelTickCount % (1000) == 0){
            if(ObjectiveUtil.getGoingObjective(svlevel).is(PlayerObjective.NOTHING)){
                ObjectiveUtil.pickObjective(svlevel);
            }
        }
    }
}
