package xyz.mrfrostydev.welcomeplayer.client.gui.screens;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import software.bernie.geckolib.util.RenderUtil;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.AudienceMood;
import xyz.mrfrostydev.welcomeplayer.data.AudiencePhase;
import xyz.mrfrostydev.welcomeplayer.utils.AnimatedImage;
import xyz.mrfrostydev.welcomeplayer.utils.GraphicUtil;
import xyz.mrfrostydev.welcomeplayer.utils.TextReader;

public class SurveillancePadScreen extends Screen {
    private static final ResourceLocation SCREEN_RESOURCE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "textures/gui/screens/surveillance_neutral_screen.png");
    private static final ResourceLocation SCREEN_SAD_RESOURCE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "textures/gui/screens/surveillance_sad_screen.png");
    private static final ResourceLocation SCREEN_HAPPY_RESOURCE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "textures/gui/screens/surveillance_happy_screen.png");
    private static final ResourceLocation SCREEN_ANGRY_RESOURCE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "textures/gui/screens/surveillance_angry_screen.png");
    private static final ResourceLocation SCREEN_CRUEL_RESOURCE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "textures/gui/screens/surveillance_cruel_screen.png");

    private final TextReader phaseText = new TextReader(3);
    private final TextReader moodText = new TextReader(3);
    private final TextReader eventText = new TextReader(3);

    private final AnimatedImage animatedNeutral = new AnimatedImage(SCREEN_RESOURCE, 12, 256, 512, 2);
    private final AnimatedImage animatedSad = new AnimatedImage(SCREEN_SAD_RESOURCE, 12, 256, 512, 2);
    private final AnimatedImage animatedHappy = new AnimatedImage(SCREEN_HAPPY_RESOURCE, 12, 256, 512, 2);
    private final AnimatedImage animatedAngry = new AnimatedImage(SCREEN_ANGRY_RESOURCE, 12, 256, 512, 2);
    private final AnimatedImage animatedCruel = new AnimatedImage(SCREEN_CRUEL_RESOURCE, 12, 256, 512, 2);

    private AnimatedImage usedImage = animatedNeutral;

    private final int MAX_LINE_WIDTH_TOP = 170;
    private final int MAX_LINE_WIDTH_BOTTOM = 65;

    AudienceData data;
    int imageWidth;
    int imageHeight;

    public SurveillancePadScreen(AudienceData data) {
        super(Component.empty());
        this.data = data;
        this.imageWidth = 228;
        this.imageHeight = 178;
    }

    @Override
    public void tick() {
        moodText.tick();
        phaseText.tick();
        eventText.tick();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == InputConstants.KEY_E ||
            keyCode == InputConstants.KEY_Q
        ) {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void init() {
        super.init();

        animatedNeutral.setVisible(false);
        animatedSad.setVisible(false);
        animatedHappy.setVisible(false);
        animatedAngry.setVisible(false);
        animatedCruel.setVisible(false);


        AudienceMood mood = data.getMood();
        AudiencePhase phase = data.getPhase();
        AudienceEvent event = data.getEventManager().getGoingEvent();

        String phaseComponent = "item.welcomeplayer.surveillance.interest.neutral";
        String moodComponent = "item.welcomeplayer.surveillance.mood.neutral";
        String eventComponent = "item.welcomeplayer.surveillance.event";

        if(phase == AudiencePhase.FURIOUS) phaseComponent = "item.welcomeplayer.surveillance.interest.furious";
        else if(phase == AudiencePhase.BORED) phaseComponent = "item.welcomeplayer.surveillance.interest.bored";
        else if(phase == AudiencePhase.INTERESTED) phaseComponent = "item.welcomeplayer.surveillance.interest.interested";
        else if(phase == AudiencePhase.THRILLED) phaseComponent = "item.welcomeplayer.surveillance.interest.thrilled";

        if(mood.equals(AudienceMood.SAD)) {
            moodComponent = "item.welcomeplayer.surveillance.mood.sad";
            usedImage = animatedSad;
        }
        else if(mood.equals(AudienceMood.HAPPY)) {
            moodComponent = "item.welcomeplayer.surveillance.mood.happy";
            usedImage = animatedHappy;
        }
        else if(mood.equals(AudienceMood.ANGRY)) {
            moodComponent = "item.welcomeplayer.surveillance.mood.angry";
            usedImage = animatedAngry;
        }
        else if(mood.equals(AudienceMood.CRUEL)) {
            moodComponent = "item.welcomeplayer.surveillance.mood.cruel";
            usedImage = animatedCruel;
        }

        phaseText.addPermenantText(Component.translatable(phaseComponent).getString());
        moodText.addPermenantText(Component.translatable(moodComponent).getString());
        eventText.addPermenantText(Component.translatable(eventComponent).append(event.getName()).getString());

        usedImage.setPosition((this.width - this.imageWidth) / 2, (this.height - this.imageHeight) / 2);
        usedImage.setVisible(true);
        usedImage.playLoop();
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        int tick = (int)RenderUtil.getCurrentTick();
        usedImage.render(guiGraphics, tick, partialTick);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        if(Minecraft.getInstance().options.hideGui)return;
        this.renderText(guiGraphics);
    }

    public void renderText(GuiGraphics guiGraphics){
        int posX = (guiGraphics.guiWidth() / 2) - 94;
        int posY = (guiGraphics.guiHeight() / 2) - 68;

        int OffsetY = 0;
        for(FormattedCharSequence displayTextSeparated : font.split(Component.literal(phaseText.getDisplayText()), MAX_LINE_WIDTH_TOP)){
            GraphicUtil.drawStringWithScale(guiGraphics, font, displayTextSeparated,
                    posX, posY + OffsetY, 0.8F, 0xfa692a, true);
            OffsetY += font.lineHeight;
        }

        for(FormattedCharSequence displayTextSeparated : font.split(Component.translatable(moodText.getDisplayText()), MAX_LINE_WIDTH_TOP)){
            GraphicUtil.drawStringWithScale(guiGraphics, font, displayTextSeparated,
                    posX, posY + 2 + OffsetY, 0.8F, 0xfa692a, true);
            OffsetY += font.lineHeight;
        }

        int OffsetEventY = 0;
        for(FormattedCharSequence displayTextSeparated : font.split(Component.translatable(eventText.getDisplayText()), MAX_LINE_WIDTH_BOTTOM)){
            GraphicUtil.drawStringWithScale(guiGraphics, font, displayTextSeparated,
                    posX, posY + 37 + OffsetEventY, 0.8F, 0xfa692a, true);
            OffsetEventY += font.lineHeight;
        }
    }
}
