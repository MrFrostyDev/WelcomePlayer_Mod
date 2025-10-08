package xyz.mrfrostydev.welcomeplayer.client.renderers.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.service_bot.ServiceBotEntity;

public class ServiceBotEntityRenderer extends GeoEntityRenderer<ServiceBotEntity> {
    public ServiceBotEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "service_bot")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
