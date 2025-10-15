package xyz.mrfrostydev.welcomeplayer.client.renderers.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.entities.projectiles.ShockChargeProjectile;

public class ShockChargeEntityRenderer extends GeoEntityRenderer<ShockChargeProjectile> {
    public ShockChargeEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "shock_charge_projectile")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
