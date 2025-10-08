package xyz.mrfrostydev.welcomeplayer.client.renderers.item;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.items.gadgets.StasisStickItem;

public class StasisStickItemRenderer extends GeoItemRenderer<StasisStickItem> {
    public StasisStickItemRenderer() {
        super(new GeoModel<>(){
            @Override
            public ResourceLocation getModelResource(StasisStickItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "stasis_stick");
                return basePath.withPath("geo/item/" + basePath.getPath() + ".geo.json");
            }

            @Override
            public ResourceLocation getAnimationResource(StasisStickItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "stasis_stick");
                return basePath.withPath("animations/item/" + basePath.getPath() + ".animation.json");
            }

            @Override
            public ResourceLocation getTextureResource(StasisStickItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "stasis_stick_model");
                return basePath.withPath("textures/item/" + basePath.getPath() + ".png");
            }
        });
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
