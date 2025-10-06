package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class AnimatedImage extends AnimatedSprite {
    public AnimatedImage(ResourceLocation resource, int tickDelay, int textureWidth, int textureHeight, int totalFrames){
        super(resource, tickDelay, textureWidth, textureHeight, totalFrames);
    }

    public AnimatedImage(ResourceLocation resource, int x, int y, int tickDelay, int textureWidth, int textureHeight, int totalFrames){
        super(resource, tickDelay, x, y, textureWidth, textureHeight, totalFrames);
    }

    public void render(GuiGraphics guiGraphics, int tick, float partialTick){
        if(!isVisible) return;

        guiGraphics.blit(resource,
                posX, posY,
                textureWidth, imageHeight * frame,
                textureWidth, imageHeight,
                textureWidth, textureHeight);

        if(isPlaying && lastTick != tick && tick % tickDelay == 0){
            if(lastTick < 0) { // Buffer for first frame.
                lastTick = tick;
                return;
            }
            lastTick = tick;
            frame++;
            if(frame >= totalFrames) {
                if(isLooping) frame = 0;
                else {
                    frame = totalFrames - 1;
                    stop();
                }
            }
        }
    }
}
