package xyz.mrfrostydev.welcomeplayer.client.gui.overlays;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.*;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommercialOverlay implements LayeredDraw.Layer {
    public static Random RANDOM = new Random();
    private static final int MAX_ADS = 6;
    private static final int MAX_DURATION = 2400;

    private static final ResourceLocation AD_0_SPRITE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "advertisements/ad_0");
    private static final ResourceLocation AD_1_SPRITE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "advertisements/ad_1");
    private static final ResourceLocation AD_2_SPRITE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "advertisements/ad_2");
    private static final ResourceLocation AD_3_SPRITE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "advertisements/ad_3");
    private static final ResourceLocation AD_4_SPRITE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "advertisements/ad_4");
    private static final List<ResourceLocation> AVAILABLE_ADS = List.of(
            AD_0_SPRITE, AD_1_SPRITE, AD_2_SPRITE, AD_3_SPRITE, AD_4_SPRITE
    );

    private static final int AD_TEXTURE_WIDTH = 129;
    private static final int AD_TEXTURE_HEIGHT = 101;

    private Minecraft minecraft;
    private static List<Ad> activeAds = new ArrayList<>(MAX_ADS);

    private int cooldown = 0;

    public CommercialOverlay() {
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        AudienceData data = ClientAudienceData.get();
        AudienceEvent goingEvent = data.getEventManager().getGoingEvent();

        for(Ad ad : activeAds)
            if(ad.duration > 0) ad.duration--;

        if(!goingEvent.is(AudienceEvents.COMMERCIAL_BREAK))return;
        if(player == null)return;
        if(player.isSpectator())return;

        for(int i=activeAds.size()-1; i >= 0; --i){
            Ad ad = activeAds.get(i);
            ad.render(guiGraphics, deltaTracker);

            if(ad.duration <= 0) {
                activeAds.remove(i);
            }
        }

        if(cooldown > 0) cooldown--;
        if((int)(Math.random() * 20) == 0 && cooldown <= 0 && activeAds.size() <= 6){
            cooldown = 400;
            ResourceLocation resource = AVAILABLE_ADS.get(RANDOM.nextInt(AVAILABLE_ADS.size()));
            int durationHalf = MAX_DURATION / 2;
            Ad in = new Ad(resource, (int)(Math.random() * durationHalf + durationHalf),
                    (int)Math.clamp(Math.random() * guiGraphics.guiWidth(), 20, guiGraphics.guiWidth() - AD_TEXTURE_WIDTH - 20),
                    (int) Math.clamp(Math.random() * guiGraphics.guiHeight(), 20, guiGraphics.guiHeight() - AD_TEXTURE_HEIGHT - 20)
            );
            activeAds.add(in);
        }
    }

    /*
    Gui Scale 1: 1920
    Gui Scale 2: 960
    Gui Scale 3: 640
    Gui Scale 4: 480
    */
    private static class Ad{
        ResourceLocation resource;
        int duration;
        int x;
        int y;

        public Ad(ResourceLocation resource, int duration, int x, int y){
            this.resource = resource;
            this.duration = duration;
            this.x = x;
            this.y = y;
        }

        public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker){
            PoseStack pose = guiGraphics.pose();;
            pose.pushPose();

            float scale = (float) guiGraphics.guiWidth() / 480;
            int posX = (int)(x / scale);
            int posY = (int)(y / scale);

            pose.scale(scale, scale, scale);
            guiGraphics.blitSprite(resource,
                    AD_TEXTURE_WIDTH, AD_TEXTURE_HEIGHT,
                    0, 0,
                    posX, posY,
                    AD_TEXTURE_WIDTH, AD_TEXTURE_HEIGHT);
            pose.popPose();
        }
    }
}
