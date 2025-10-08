package xyz.mrfrostydev.welcomeplayer.client.renderers.item;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.items.gadgets.PhaseLinkItem;

public class PhaseLinkItemRenderer extends GeoItemRenderer<PhaseLinkItem> {
    public PhaseLinkItemRenderer() {
        super(new GeoModel<>(){
            @Override
            public ResourceLocation getModelResource(PhaseLinkItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "phase_link");
                return basePath.withPath("geo/item/" + basePath.getPath() + ".geo.json");
            }

            @Override
            public ResourceLocation getAnimationResource(PhaseLinkItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "phase_link");
                return basePath.withPath("animations/item/" + basePath.getPath() + ".animation.json");
            }

            @Override
            public ResourceLocation getTextureResource(PhaseLinkItem animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "phase_link_model");
                return basePath.withPath("textures/item/" + basePath.getPath() + ".png");
            }
        });
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
