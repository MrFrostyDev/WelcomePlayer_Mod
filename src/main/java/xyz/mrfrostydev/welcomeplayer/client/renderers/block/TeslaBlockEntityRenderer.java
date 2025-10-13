package xyz.mrfrostydev.welcomeplayer.client.renderers.block;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.TeslaBlockEntity;

public class TeslaBlockEntityRenderer extends GeoBlockRenderer<TeslaBlockEntity> {
    public TeslaBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "retro_tesla_coil")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
