package xyz.mrfrostydev.welcomeplayer.events.subscribers.player;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.network.SyncAudienceDataLargePacket;
import xyz.mrfrostydev.welcomeplayer.network.SyncAudienceDataSmallPacket;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;

@EventBusSubscriber
public class PlayerEvents {

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if(!player.level().isClientSide()){
            AudienceData lordsData = AudienceUtil.getAudienceData((ServerLevel)player.level());
            PacketDistributor.sendToPlayer((ServerPlayer) player, SyncAudienceDataSmallPacket.create(lordsData));
            PacketDistributor.sendToPlayer((ServerPlayer) player, SyncAudienceDataLargePacket.create(lordsData));
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if(!player.level().isClientSide()){
            AudienceData lordsData = AudienceUtil.getAudienceData((ServerLevel)player.level());
            PacketDistributor.sendToPlayer((ServerPlayer) player, SyncAudienceDataSmallPacket.create(lordsData));
            PacketDistributor.sendToPlayer((ServerPlayer) player, SyncAudienceDataLargePacket.create(lordsData));
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        Player player = event.getEntity();
        if(!player.level().isClientSide() && event.isWasDeath()){
            AudienceData lordsData = AudienceUtil.getAudienceData((ServerLevel)player.level());
            PacketDistributor.sendToPlayer((ServerPlayer) player, SyncAudienceDataSmallPacket.create(lordsData));
            PacketDistributor.sendToPlayer((ServerPlayer) player, SyncAudienceDataLargePacket.create(lordsData));
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if(!player.level().isClientSide()){
            AudienceData lordsData = AudienceUtil.getAudienceData((ServerLevel)player.level());
            PacketDistributor.sendToPlayer((ServerPlayer) player, SyncAudienceDataSmallPacket.create(lordsData));
            PacketDistributor.sendToPlayer((ServerPlayer) player, SyncAudienceDataLargePacket.create(lordsData));
        }
    }
}
