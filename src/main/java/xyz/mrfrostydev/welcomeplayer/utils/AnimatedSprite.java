package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class AnimatedSprite {
    protected ResourceLocation resource;
    protected int tickDelay;
    protected final int textureWidth;
    protected final int textureHeight;
    protected final int totalFrames;
    protected boolean isVisible;

    protected boolean isPlaying;
    protected boolean isLooping;
    protected int frame;
    protected int lastTick;

    protected int posX;
    protected int posY;

    protected final int imageHeight;

    public AnimatedSprite(ResourceLocation resource, int tickDelay, int textureWidth, int textureHeight, int totalFrames){
        this.resource = resource;
        this.tickDelay = tickDelay;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.totalFrames = totalFrames;
        this.isPlaying = false;
        this.isLooping = false;
        this.frame = 0;
        this.lastTick = -1;
        this.posX = 0;
        this.posY = 0;
        this.imageHeight = textureHeight / totalFrames;
        this.isVisible = true;
    }

    public AnimatedSprite(ResourceLocation resource, int tickDelay, int posX, int posY, int textureWidth, int textureHeight, int totalFrames){
        this.resource = resource;
        this.tickDelay = tickDelay;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.totalFrames = totalFrames;
        this.isPlaying = false;
        this.isLooping = false;
        this.frame = 0;
        this.lastTick = -1;
        this.posX = posX;
        this.posY = posY;
        this.imageHeight = textureHeight / totalFrames;
    }

    public void render(GuiGraphics guiGraphics, int tick, float partialTick){
        if(!isVisible) return;

        guiGraphics.blitSprite(resource,
                textureWidth, textureHeight,
                0, imageHeight * frame,
                posX, posY,
                textureWidth, imageHeight);

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

    public void play(){
        if(!isPlaying){
            isPlaying = true;
            isLooping = false;
        }
    }

    public void playLoop(){
        if(!isPlaying){
            isLooping = true;
            isPlaying = true;
        }
    }

    public void reset(){
        frame = 0;
        stop();
    }

    public void stop(){
        if(isPlaying){
            isPlaying = false;
        }
    }

    public void setResource(ResourceLocation resource){
        this.resource = resource;
    }

    public void setPosition(int x, int y){
        this.posX = x;
        this.posY = y;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setFrame(int value) {
        if(value >= totalFrames) frame = totalFrames - 1;
        else frame = value;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean hasEnded(){
        if (isLooping) return false;
        else return frame >= totalFrames - 1;
    }
}
