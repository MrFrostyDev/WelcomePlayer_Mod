package xyz.mrfrostydev.welcomeplayer.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.ClientObjectiveData;
import xyz.mrfrostydev.welcomeplayer.data.ObjectiveManagerData;

public record SyncObjectiveDataPacket(ObjectiveManagerData data) implements CustomPacketPayload {
    public static final Type<SyncObjectiveDataPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "sync_objective_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncObjectiveDataPacket> STREAM_CODEC = StreamCodec.composite(
            ObjectiveManagerData.STREAM_CODEC, SyncObjectiveDataPacket::data,
            SyncObjectiveDataPacket::create
    );

    public static SyncObjectiveDataPacket create(ObjectiveManagerData data){
        return new SyncObjectiveDataPacket(data);
    }

    public static void handle(SyncObjectiveDataPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            ClientObjectiveData.setData(packet.data);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
}
