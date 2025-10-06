package xyz.mrfrostydev.welcomeplayer.client.renderers.block;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.BeaconBlockEntity;

public class BeaconBlockEntityRenderer extends GeoBlockRenderer<BeaconBlockEntity> {
    public BeaconBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "beacon")));
    }

    @Override
    public ResourceLocation getTextureLocation(BeaconBlockEntity animatable) {
        if(animatable.isActivated()){
            return ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "textures/block/beacon_active.png");
        }
        return super.getTextureLocation(animatable);
    }
}
