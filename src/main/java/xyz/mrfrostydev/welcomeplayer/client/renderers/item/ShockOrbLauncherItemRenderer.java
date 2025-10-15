package xyz.mrfrostydev.welcomeplayer.client.renderers.item;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.items.gadgets.ShockOrbLauncherItem;

public class ShockOrbLauncherItemRenderer extends GeoItemRenderer<ShockOrbLauncherItem> {
    public ShockOrbLauncherItemRenderer() {
        super(new GeoModel<>(){
            @Override
            public ResourceLocation getModelResource(ShockOrbLauncherItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "shock_orb_launcher");
                return basePath.withPath("geo/item/" + basePath.getPath() + ".geo.json");
            }

            @Override
            public ResourceLocation getAnimationResource(ShockOrbLauncherItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "shock_orb_launcher");
                return basePath.withPath("animations/item/" + basePath.getPath() + ".animation.json");
            }

            @Override
            public ResourceLocation getTextureResource(ShockOrbLauncherItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "shock_orb_launcher_model");
                return basePath.withPath("textures/item/" + basePath.getPath() + ".png");
            }
        });
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
