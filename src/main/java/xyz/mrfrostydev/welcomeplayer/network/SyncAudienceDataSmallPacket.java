package xyz.mrfrostydev.welcomeplayer.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.ClientAudienceData;

public record SyncAudienceDataSmallPacket(AudienceData.AudienceDataSmall dataSmall) implements CustomPacketPayload {
    public static final Type<SyncAudienceDataSmallPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "sync_audience_data_small"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncAudienceDataSmallPacket> STREAM_CODEC = StreamCodec.composite(
            AudienceData.AudienceDataSmall.STREAM_CODEC, SyncAudienceDataSmallPacket::dataSmall,
            SyncAudienceDataSmallPacket::create
    );

    public static SyncAudienceDataSmallPacket create(AudienceData data){
        return new SyncAudienceDataSmallPacket(data.getDataSmall());
    }

    public static SyncAudienceDataSmallPacket create(AudienceData.AudienceDataSmall dataSmall){
        return new SyncAudienceDataSmallPacket(dataSmall);
    }

    public static void handle(SyncAudienceDataSmallPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            ClientAudienceData.setSmallData(packet.dataSmall);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
}
