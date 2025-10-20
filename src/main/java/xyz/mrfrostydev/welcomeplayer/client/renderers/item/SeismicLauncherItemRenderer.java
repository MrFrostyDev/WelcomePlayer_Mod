package xyz.mrfrostydev.welcomeplayer.client.renderers.item;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.items.gadgets.SeismicLauncherItem;

public class SeismicLauncherItemRenderer extends GeoItemRenderer<SeismicLauncherItem> {
    public SeismicLauncherItemRenderer() {
        super(new GeoModel<>(){
            @Override
            public ResourceLocation getModelResource(SeismicLauncherItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "seismic_launcher");
                return basePath.withPath("geo/item/" + basePath.getPath() + ".geo.json");
            }

            @Override
            public ResourceLocation getAnimationResource(SeismicLauncherItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "seismic_launcher");
                return basePath.withPath("animations/item/" + basePath.getPath() + ".animation.json");
            }

            @Override
            public ResourceLocation getTextureResource(SeismicLauncherItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "seismic_launcher_model");
                return basePath.withPath("textures/item/" + basePath.getPath() + ".png");
            }
        });
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
