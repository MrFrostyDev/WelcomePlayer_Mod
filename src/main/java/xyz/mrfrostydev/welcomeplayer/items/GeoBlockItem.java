package xyz.mrfrostydev.welcomeplayer.items;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.client.renderers.GeoBlockItemRenderer;

import java.util.function.Consumer;

public class GeoBlockItem extends BlockItem implements GeoItem {
    private AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);
    private String modelName;
    private String textureName;
    private String animName;

    public GeoBlockItem(Block block, String resourceName, Properties properties) {
        super(block, properties);
        this.modelName = resourceName;
        this.textureName = resourceName;
        this.animName = resourceName;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public GeoBlockItem(Block block, String modelName, String textureName, String animName, Properties properties) {
        super(block, properties);
        this.modelName = modelName;
        this.textureName = textureName;
        this.animName = animName;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animCache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtil.getCurrentTick();
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoBlockItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new GeoBlockItemRenderer(
                            ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, modelName),
                            ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, textureName),
                            ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, animName)
                    );

                return this.renderer;
            }
        });
    }
}
