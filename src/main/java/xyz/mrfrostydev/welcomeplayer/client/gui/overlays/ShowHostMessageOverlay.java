package xyz.mrfrostydev.welcomeplayer.client.gui.overlays;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;
import xyz.mrfrostydev.welcomeplayer.utils.StringRevealer;

import java.util.ArrayDeque;
import java.util.Random;

public class ShowHostMessageOverlay implements LayeredDraw.Layer {
    private static final Random RANDOM = new Random();
    private static final int TIME_PER_CHAR = 9;
    private static final int TIME_BUFFER = 300; // Extra time for reading
    private final int MAX_LINE_WIDTH = 120;

    private static ArrayDeque<String> dialogDeque = new ArrayDeque<>();
    private String currentText = ""; // Current active text being used
    private String displayText = ""; // Dialog that is displayed (slowly iterating till it matches currentText)
    private StringRevealer stringReveal = new StringRevealer();
    private int messageTime = 0;

    private Minecraft minecraft;
    private Font font;
    private int color;

    public ShowHostMessageOverlay() {
        this.minecraft = Minecraft.getInstance();
        this.font = Minecraft.getInstance().font;
        this.color = randomColour();
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        // if dialog queue is not empty and there isn't dialog currently in use
        if (!dialogDeque.isEmpty() && currentText.isEmpty()){
            currentText = dialogDeque.pop();
            int len = currentText.length();
            stringReveal.setString(currentText);
            messageTime = len * TIME_PER_CHAR + TIME_BUFFER;
        }
        // if dialog is finished, reset
        if (messageTime <= 0){
            displayText = "";
            currentText = "";
        }
        if (messageTime > 0){
            messageTime--;
            if (messageTime % TIME_PER_CHAR == 0) {
                displayText = stringReveal.reveal();
            }
            this.renderAnimatedText(guiGraphics);
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

        float rotDegrees = (float)(20 * Math.cos((double) tick / 9));

        poseStack.pushPose();
        poseStack.scale(scaleX, scaleY, 1.0F);

        poseStack.translate(-scaledWidth / scaleX, -scaledHeight / scaleY, 0);

        int posX = (guiGraphics.guiWidth() / 2);
        int posY = ((guiGraphics.guiHeight() / 2) + 5) - (guiGraphics.guiHeight() / 7) ;

        poseStack.rotateAround(new Quaternionf().fromAxisAngleDeg(0, 0, -1, rotDegrees), posX, posY, 0);

        if(messageTime % 15 == 0) color = randomColour();
        int yOffset = 0;
        for(FormattedCharSequence displayTextSeparated : font.split(Component.translatable(displayText), MAX_LINE_WIDTH)){
            guiGraphics.drawCenteredString(font, displayTextSeparated,
                    posX, posY + yOffset,
                    color);

            yOffset += font.lineHeight;
        }

        poseStack.popPose();
    }

    public int randomColour(){
        return RANDOM.nextInt(0xffffff + 1);
    }

    public static void addFleshLordMessage(String string) {
        dialogDeque.add(string);
    }
}
