package xyz.mrfrostydev.welcomeplayer.events.subscribers.player;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.ObjectiveManagerData;
import xyz.mrfrostydev.welcomeplayer.network.SyncAudienceDataLargePacket;
import xyz.mrfrostydev.welcomeplayer.network.SyncAudienceDataSmallPacket;
import xyz.mrfrostydev.welcomeplayer.network.SyncObjectiveDataPacket;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

@EventBusSubscriber
public class PlayerEvents {

    private static void syncPlayerData(Player player){
        AudienceData lordsData = AudienceUtil.getAudienceData((ServerLevel)player.level());
        ObjectiveManagerData objData = ObjectiveUtil.getObjectiveManager((ServerLevel)player.level());
        PacketDistributor.sendToPlayer((ServerPlayer) player, SyncAudienceDataSmallPacket.create(lordsData));
        PacketDistributor.sendToPlayer((ServerPlayer) player, SyncAudienceDataLargePacket.create(lordsData));
        PacketDistributor.sendToPlayer((ServerPlayer) player, SyncObjectiveDataPacket.create(objData));
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if(!player.level().isClientSide()){
            syncPlayerData(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if(!player.level().isClientSide()){
            syncPlayerData(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        Player player = event.getEntity();
        if(!player.level().isClientSide() && event.isWasDeath()){
            syncPlayerData(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if(!player.level().isClientSide()){
            syncPlayerData(player);
        }
    }
}
