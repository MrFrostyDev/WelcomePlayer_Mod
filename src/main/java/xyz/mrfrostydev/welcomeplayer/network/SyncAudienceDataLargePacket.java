package xyz.mrfrostydev.welcomeplayer.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.ClientAudienceData;

public record SyncAudienceDataLargePacket(AudienceData.AudienceDataLarge dataLarge) implements CustomPacketPayload {
    public static final Type<SyncAudienceDataLargePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "sync_audience_data_large"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncAudienceDataLargePacket> STREAM_CODEC = StreamCodec.composite(
            AudienceData.AudienceDataLarge.STREAM_CODEC, SyncAudienceDataLargePacket::dataLarge,
            SyncAudienceDataLargePacket::create
    );

    public static SyncAudienceDataLargePacket create(AudienceData data){
        return new SyncAudienceDataLargePacket(data.getDataLarge());
    }

    public static SyncAudienceDataLargePacket create(AudienceData.AudienceDataLarge dataLarge){
        return new SyncAudienceDataLargePacket(dataLarge);
    }

    public static void handle(SyncAudienceDataLargePacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            ClientAudienceData.setLargeData(packet.dataLarge);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
}
