package xyz.mrfrostydev.welcomeplayer.client.renderers.block;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.MaterialTransitBlockEntity;

public class MaterialTransitBlockEntityRenderer extends GeoBlockRenderer<MaterialTransitBlockEntity> {
    public MaterialTransitBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "material_transit")));
    }
}
