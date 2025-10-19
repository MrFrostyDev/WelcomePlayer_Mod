package xyz.mrfrostydev.welcomeplayer.client.events;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.client.renderers.block.BeaconBlockEntityRenderer;
import xyz.mrfrostydev.welcomeplayer.client.renderers.block.HostScreenBlockEntityRenderer;
import xyz.mrfrostydev.welcomeplayer.client.renderers.block.MaterialTransitBlockEntityRenderer;
import xyz.mrfrostydev.welcomeplayer.client.renderers.block.TeslaBlockEntityRenderer;
import xyz.mrfrostydev.welcomeplayer.client.renderers.entity.*;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID, value = Dist.CLIENT)
public class RenderRegisterEvents {

    @SubscribeEvent
    public static void onRegisterEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event){}

    @SubscribeEvent
    public static void onEntityRendererRegister(EntityRenderersEvent.RegisterRenderers event) {

        // Block Entities
        BlockEntityRenderers.register(BlockRegistry.MATERIAL_TRANSIT_ENTITY.get(), MaterialTransitBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockRegistry.BEACON_ENTITY.get(), BeaconBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockRegistry.RETRO_TESLA_COIL_ENTITY.get(), TeslaBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockRegistry.HOST_SCREEN_ENTITY.get(), HostScreenBlockEntityRenderer::new);

        // Entities
        EntityRenderers.register(EntityRegistry.BOUNCE_PAD.get(), BouncePadEntityRenderer::new);
        EntityRenderers.register(EntityRegistry.HANDIBOT.get(), HandibotEntityRenderer::new);
        EntityRenderers.register(EntityRegistry.SERVICE_BOT.get(), ServiceBotEntityRenderer::new);
        EntityRenderers.register(EntityRegistry.ERADICATOR.get(), EradicatorEntityRenderer::new);
        EntityRenderers.register(EntityRegistry.LASER_BLAST_PROJECTILE.get(), LaserBlastEntityRenderer::new);
        EntityRenderers.register(EntityRegistry.SHOCK_BOLT_PROJECTILE.get(), ShockBoltEntityRenderer::new);
        EntityRenderers.register(EntityRegistry.SHOCK_CHARGE_PROJECTILE.get(), ShockChargeEntityRenderer::new);

    }
}
