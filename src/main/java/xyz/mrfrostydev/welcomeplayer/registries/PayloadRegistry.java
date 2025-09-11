package xyz.mrfrostydev.welcomeplayer.registries;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.network.*;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class PayloadRegistry {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(WelcomeplayerMain.MOD_ID)
                .versioned("1.0")
                .optional();

        registrar.playToClient(
                ServerVendorMenuPacket.TYPE,
                ServerVendorMenuPacket.STREAM_CODEC,
                ServerVendorMenuPacket::handle
        );

        registrar.playToClient(
                ServerShowHostMessagePacket.TYPE,
                ServerShowHostMessagePacket.STREAM_CODEC,
                ServerShowHostMessagePacket::handle
        );

        registrar.playToServer(
                ClientVendorUpdatePacket.TYPE,
                ClientVendorUpdatePacket.STREAM_CODEC,
                ClientVendorUpdatePacket::handle
        );

        registrar.playToClient(
                SyncAudienceDataSmallPacket.TYPE,
                SyncAudienceDataSmallPacket.STREAM_CODEC,
                SyncAudienceDataSmallPacket::handle
        );

        registrar.playToClient(
                SyncAudienceDataLargePacket.TYPE,
                SyncAudienceDataLargePacket.STREAM_CODEC,
                SyncAudienceDataLargePacket::handle
        );
    }
}
