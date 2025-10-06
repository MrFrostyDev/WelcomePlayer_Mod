package xyz.mrfrostydev.welcomeplayer.client.events;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.client.renderers.block.BeaconBlockEntityRenderer;
import xyz.mrfrostydev.welcomeplayer.client.renderers.block.MaterialTransitBlockEntityRenderer;
import xyz.mrfrostydev.welcomeplayer.client.renderers.entity.BouncePadEntityRenderer;
import xyz.mrfrostydev.welcomeplayer.client.renderers.entity.EradicatorEntityRenderer;
import xyz.mrfrostydev.welcomeplayer.client.renderers.entity.HandibotEntityRenderer;
import xyz.mrfrostydev.welcomeplayer.client.renderers.entity.LaserBlastEntityRenderer;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class RenderRegisterEvents {

    @SubscribeEvent
    public static void onRegisterEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event){}

    @SubscribeEvent
    public static void onEntityRendererRegister(EntityRenderersEvent.RegisterRenderers event) {

        // Block Entities
        BlockEntityRenderers.register(BlockRegistry.MATERIAL_TRANSIT_ENTITY.get(), MaterialTransitBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockRegistry.BEACON_ENTITY.get(), BeaconBlockEntityRenderer::new);

        // Entities
        EntityRenderers.register(EntityRegistry.BOUNCE_PAD.get(), BouncePadEntityRenderer::new);
        EntityRenderers.register(EntityRegistry.HANDIBOT.get(), HandibotEntityRenderer::new);
        EntityRenderers.register(EntityRegistry.ERADICATOR.get(), EradicatorEntityRenderer::new);
        EntityRenderers.register(EntityRegistry.LASER_BLAST_PROJECTILE.get(), LaserBlastEntityRenderer::new);
    }
}
