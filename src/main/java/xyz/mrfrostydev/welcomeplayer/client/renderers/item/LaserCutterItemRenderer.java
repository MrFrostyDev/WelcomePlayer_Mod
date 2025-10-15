package xyz.mrfrostydev.welcomeplayer.client.renderers.item;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.items.gadgets.LaserCutterItem;

public class LaserCutterItemRenderer extends GeoItemRenderer<LaserCutterItem> {
    public LaserCutterItemRenderer() {
        super(new GeoModel<>(){
            @Override
            public ResourceLocation getModelResource(LaserCutterItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "laser_cutter");
                return basePath.withPath("geo/item/" + basePath.getPath() + ".geo.json");
            }

            @Override
            public ResourceLocation getAnimationResource(LaserCutterItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "laser_cutter");
                return basePath.withPath("animations/item/" + basePath.getPath() + ".animation.json");
            }

            @Override
            public ResourceLocation getTextureResource(LaserCutterItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "laser_cutter_model");
                return basePath.withPath("textures/item/" + basePath.getPath() + ".png");
            }
        });
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
