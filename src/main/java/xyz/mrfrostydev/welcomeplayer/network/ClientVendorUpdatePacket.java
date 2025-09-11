package xyz.mrfrostydev.welcomeplayer.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.mrfrostydev.boundbyflesh.WelcomeplayerMain;
import xyz.mrfrostydev.boundbyflesh.utils.FleshMerchantUtil;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.utils.VendorUtil;

public record ClientVendorUpdatePacket(ItemStack stack, int price) implements CustomPacketPayload {
    public static final Type<ClientVendorUpdatePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "client_vendor_update_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientVendorUpdatePacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, ClientVendorUpdatePacket::stack,
            ByteBufCodecs.INT, ClientVendorUpdatePacket::price,
            ClientVendorUpdatePacket::create
    );

    public static ClientVendorUpdatePacket create(ItemStack stack, int price){
        return new ClientVendorUpdatePacket(stack, price);
    }

    public static void handle(ClientVendorUpdatePacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            VendorUtil.tryPurchase(context.player().getInventory(), packet.price);
            context.player().getInventory().add(packet.stack);
        }).exceptionally(e -> {
            // Handle exception
            context.disconnect(Component.literal("ClientVendorUpdatePacket could not send data: "));
            return null;
        });

    }

    @Override
    public Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
}
