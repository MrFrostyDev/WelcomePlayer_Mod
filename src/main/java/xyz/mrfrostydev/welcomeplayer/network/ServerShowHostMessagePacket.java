package xyz.mrfrostydev.welcomeplayer.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.client.gui.overlays.ShowHostMessageOverlay;

public record ServerShowHostMessagePacket(Component data) implements CustomPacketPayload {
    public static final Type<ServerShowHostMessagePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "server_show_host_message"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerShowHostMessagePacket> STREAM_CODEC = StreamCodec.composite(
            ComponentSerialization.STREAM_CODEC, ServerShowHostMessagePacket::data,
            ServerShowHostMessagePacket::create
    );

    public static ServerShowHostMessagePacket create(Component component){
        return new ServerShowHostMessagePacket(component);
    }

    public static void handle(ServerShowHostMessagePacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            ShowHostMessageOverlay.addHostMessage(packet.data.getString());
        }).exceptionally(e -> {
            // Handle exception
            context.disconnect(Component.literal("ServerShowHostMessagePacket could not send data: "));
            return null;
        });

    }

    @Override
    public Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
}
