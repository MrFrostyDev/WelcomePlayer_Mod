package xyz.mrfrostydev.welcomeplayer.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.client.gui.overlays.ShowHostMessageOverlay;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;

public class StartShowIntroductionPacket implements CustomPacketPayload {
    public static final Type<StartShowIntroductionPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "start_show_introduction"));

    public static final StreamCodec<RegistryFriendlyByteBuf, StartShowIntroductionPacket> STREAM_CODEC = CustomPacketPayload.codec(
            StartShowIntroductionPacket::write,
            StartShowIntroductionPacket::new
    );

    public StartShowIntroductionPacket(){}
    public StartShowIntroductionPacket(FriendlyByteBuf buf){}
    public void write(FriendlyByteBuf buf){}

    public static void handle(StartShowIntroductionPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if(context.player().level() instanceof ServerLevel svlevel){
                AudienceUtil.startGameShow(svlevel);
            }
            else{
                ShowHostMessageOverlay.triggerIntroMessage();
            }
        }).exceptionally(e -> {
            // Handle exception
            context.disconnect(Component.literal("StartShowIntroductionPacket could not send data: "));
            return null;
        });

    }

    @Override
    public Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
}
