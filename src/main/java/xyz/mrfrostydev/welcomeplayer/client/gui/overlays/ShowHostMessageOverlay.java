package xyz.mrfrostydev.welcomeplayer.client.gui.overlays;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Quaternionf;
import software.bernie.geckolib.util.RenderUtil;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.ClientAudienceData;
import xyz.mrfrostydev.welcomeplayer.network.StartShowIntroductionPacket;
import xyz.mrfrostydev.welcomeplayer.registries.SoundEventRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.AnimatedImage;
import xyz.mrfrostydev.welcomeplayer.utils.TextReader;

import java.util.ArrayDeque;

public class ShowHostMessageOverlay implements LayeredDraw.Layer {
    private static final ResourceLocation ANNOUNCER_RESOURCE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "textures/gui/overlays/announcer_talk.png");

    private static final int ANNOUNCER_WIDTH = 100;
    private static final int ANNOUNCER_HEIGHT = 272;

    private static final int TIME_PER_CHAR = 7;

    private final int MAX_LINE_WIDTH = 150;
    private final AnimatedImage announcerImage;

    private static ArrayDeque<String> dialogDeque;
    private static boolean isIntro;

    private TextReader textReader = new TextReader(TIME_PER_CHAR);

    private Minecraft minecraft;
    private Font font;

    public ShowHostMessageOverlay() {
        dialogDeque = new ArrayDeque<>();
        isIntro = false;

        this.minecraft = Minecraft.getInstance();
        this.font = Minecraft.getInstance().font;
        this.announcerImage = new AnimatedImage(ANNOUNCER_RESOURCE, 10,
                100, 272, 4);
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if(!dialogDeque.isEmpty()){
            textReader.addText(dialogDeque.pop(), !ClientAudienceData.get().isActive() ? 9 : TIME_PER_CHAR);
        }
        // Handle introduction dialog
        if(isIntro && textReader.getDialogQueue().isEmpty()){
            isIntro = false;
            PacketDistributor.sendToServer(new StartShowIntroductionPacket());
        }

        announcerImage.render(guiGraphics, (int)RenderUtil.getCurrentTick(), 1.0F);
        textReader.tick();
        if (textReader.getMessageTime() > 0){
            int posX = (guiGraphics.guiWidth() - ANNOUNCER_WIDTH) / 2;
            int posY = (int)(guiGraphics.guiHeight() * 0.1);
            announcerImage.setPosition(posX, posY);
            announcerImage.playLoop();
            announcerImage.setVisible(true);
            this.renderAnimatedText(guiGraphics);
        }
        else{
            announcerImage.setVisible(false);
            announcerImage.stop();
        }
    }

    private void renderAnimatedText(GuiGraphics guiGraphics){
        if (Minecraft.getInstance().options.hideGui
                || Minecraft.getInstance().player == null
                || Minecraft.getInstance().player.isSpectator()) return;

        PoseStack poseStack = guiGraphics.pose();
        Player player = Minecraft.getInstance().player;
        int tick = player.tickCount;

        float scaleX = 2.0F;
        float scaleY = 2.5F;

        float halfWidth = (float) guiGraphics.guiWidth() / 2;
        float halfHeight = (float) guiGraphics.guiHeight() / 2;

        float scaledWidth = (halfWidth * scaleX) - halfWidth;
        float scaledHeight = (halfHeight * scaleY) - halfHeight;

        float rotDegrees = (float)(5 * Math.cos((double) tick / 9));

        float scaledOffsetX = -scaledWidth / scaleX;
        float scaledOffsetY = -scaledHeight / scaleY;

        poseStack.pushPose();
        poseStack.scale(scaleX, scaleY, 1.0F);

        poseStack.translate(scaledOffsetX, scaledOffsetY, 0);

        int posX = guiGraphics.guiWidth() / 2;
        int posY = (int)(guiGraphics.guiHeight() / 2.2 - ((double)guiGraphics.guiHeight() * guiGraphics.guiHeight() / 280 * 0.1 * 0.25) * 0.9);

        poseStack.rotateAround(new Quaternionf().fromAxisAngleDeg(0, 0, -1, rotDegrees), posX, posY, 0);

        int yOffset = 0;
        for(FormattedCharSequence displayTextSeparated : font.split(Component.literal(textReader.getDisplayText()), MAX_LINE_WIDTH)){
            guiGraphics.drawCenteredString(font, displayTextSeparated,
                    posX, posY + yOffset,
                    0xfa692a);

            yOffset += font.lineHeight;
        }
        poseStack.popPose();

        if(!textReader.isTextFullyDisplayed() && tick % 3 == 0){
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEventRegistry.PHASE_BLINK_BEEP.get(), 1.2F));
        }
    }

    public static void addHostMessage(String text) {
        dialogDeque.add(text);
    }

    public static void triggerIntroMessage() {
        if(!isIntro){
            isIntro = true;
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.0").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.1").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.2").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.3").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.4").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.5").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.6").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.7").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.8").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.9").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.10").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.11").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.12").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.13").getString());
            dialogDeque.add(Component.translatable("dialog.welcomeplayer.intro.14").getString());
        }
    }
}
