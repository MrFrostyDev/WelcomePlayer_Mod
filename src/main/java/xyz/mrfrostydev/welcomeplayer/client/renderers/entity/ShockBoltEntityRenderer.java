package xyz.mrfrostydev.welcomeplayer.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.entities.projectiles.ShockBoltProjectile;

public class ShockBoltEntityRenderer extends GeoEntityRenderer<ShockBoltProjectile> {
    public ShockBoltEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new GeoModel<>(){
            @Override
            public ResourceLocation getModelResource(ShockBoltProjectile animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "shock_bolt_projectile");
                return basePath.withPath("geo/entity/" + basePath.getPath() + ".geo.json");
            }

            @Override
            public ResourceLocation getAnimationResource(ShockBoltProjectile animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "shock_bolt_projectile");
                return basePath.withPath("animations/entity/" + basePath.getPath() + ".animation.json");
            }

            @Override
            public ResourceLocation getTextureResource(ShockBoltProjectile animatable) {
                ResourceLocation basePath = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "shock_bolt_projectile");
                String str = switch(animatable.getLength()){
                    case 2 -> "_short";
                    case 3 -> "_long";
                    default -> "_med";
                };
                return basePath.withPath("textures/entity/" + basePath.getPath() + str + ".png");
            }
        });
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void preRender(PoseStack poseStack, ShockBoltProjectile animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot()) / 2));
        poseStack.mulPose(Axis.XP.rotationDegrees(-Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot()) / 2));
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }
}
