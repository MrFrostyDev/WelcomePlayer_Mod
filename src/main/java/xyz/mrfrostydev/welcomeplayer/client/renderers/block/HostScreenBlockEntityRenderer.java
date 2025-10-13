package xyz.mrfrostydev.welcomeplayer.client.renderers.block;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.HostScreenBlockEntity;

public class HostScreenBlockEntityRenderer extends GeoBlockRenderer<HostScreenBlockEntity> {
    public HostScreenBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "host_screen")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(HostScreenBlockEntity animatable) {
        if(animatable.isActive())
            return ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "textures/block/host_screen_talking.png");
        else
            return ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "textures/block/host_screen.png");

    }
}
