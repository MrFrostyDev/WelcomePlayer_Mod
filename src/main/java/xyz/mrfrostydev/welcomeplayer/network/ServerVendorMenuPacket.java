package xyz.mrfrostydev.welcomeplayer.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.client.gui.menus.blocks.VendorMenu;
import xyz.mrfrostydev.welcomeplayer.data.VendorItem;

import java.util.List;

public record ServerVendorMenuPacket(List<VendorItem> data) implements CustomPacketPayload {
    public static final Type<ServerVendorMenuPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "server_vendor_menu_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerVendorMenuPacket> STREAM_CODEC = StreamCodec.composite(
            VendorItem.STREAM_CODEC.apply(ByteBufCodecs.list()), ServerVendorMenuPacket::data,
            ServerVendorMenuPacket::create
    );

    public static ServerVendorMenuPacket create(List<VendorItem> data){
        return new ServerVendorMenuPacket(data);
    }

    public static void handle(ServerVendorMenuPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            AbstractContainerMenu containermenu = context.player().containerMenu;
            if (containermenu instanceof VendorMenu menu) {
                menu.setShopItems(packet.data);
            }

        }).exceptionally(e -> {
            // Handle exception
            context.disconnect(Component.literal("ServerVendorMenuPacket could not send data: "));
            return null;
        });

    }

    @Override
    public Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
}
