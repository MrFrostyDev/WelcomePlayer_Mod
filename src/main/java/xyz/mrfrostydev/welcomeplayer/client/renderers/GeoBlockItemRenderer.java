package xyz.mrfrostydev.welcomeplayer.client.renderers;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import xyz.mrfrostydev.welcomeplayer.items.GeoBlockItem;

public class GeoBlockItemRenderer extends GeoItemRenderer<GeoBlockItem> {
    public GeoBlockItemRenderer(ResourceLocation modelResource, ResourceLocation textureResource, ResourceLocation animResource) {
        super(new GeoModel<>() {
            @Override
            public ResourceLocation getModelResource(GeoBlockItem animatable) {
                return modelResource.withPath("geo/block/" + modelResource.getPath() + ".geo.json");
            }

            @Override
            public ResourceLocation getTextureResource(GeoBlockItem animatable) {
                return textureResource.withPath("textures/block/" + textureResource.getPath() + ".png");
            }

            @Override
            public ResourceLocation getAnimationResource(GeoBlockItem animatable) {
                return animResource.withPath("animations/block/" + animResource.getPath()  + ".animation.json");
            }
        });
    }
}
