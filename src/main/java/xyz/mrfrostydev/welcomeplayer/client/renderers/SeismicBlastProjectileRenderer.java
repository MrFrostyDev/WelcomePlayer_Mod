package xyz.mrfrostydev.welcomeplayer.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import xyz.mrfrostydev.welcomeplayer.entities.projectiles.SeismicBlastProjectile;

public class SeismicBlastProjectileRenderer extends EntityRenderer<SeismicBlastProjectile> {
    public SeismicBlastProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SeismicBlastProjectile p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {}

    @Override
    public ResourceLocation getTextureLocation(SeismicBlastProjectile entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
