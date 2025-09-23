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
import xyz.mrfrostydev.welcomeplayer.utils.TextReader;

import java.util.ArrayDeque;

public class ShowHostMessageOverlay implements LayeredDraw.Layer {
    private static final int TIME_PER_CHAR = 9;
    private final int MAX_LINE_WIDTH = 120;

    private static ArrayDeque<String> dialogDeque = new ArrayDeque<>();
    private TextReader textReader = new TextReader(TIME_PER_CHAR);

    private Minecraft minecraft;
    private Font font;

    public ShowHostMessageOverlay() {
        this.minecraft = Minecraft.getInstance();
        this.font = Minecraft.getInstance().font;
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if(!dialogDeque.isEmpty()){
            textReader.addText(dialogDeque.pop());
        }
        textReader.tick();
        if (textReader.getMessageTime() > 0){
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

        float rotDegrees = (float)(15 * Math.cos((double) tick / 9));

        poseStack.pushPose();
        poseStack.scale(scaleX, scaleY, 1.0F);

        poseStack.translate(-scaledWidth / scaleX, -scaledHeight / scaleY, 0);

        int posX = (guiGraphics.guiWidth() / 2);
        int posY = ((guiGraphics.guiHeight() / 2) + 5) - (guiGraphics.guiHeight() / 7) ;

        poseStack.rotateAround(new Quaternionf().fromAxisAngleDeg(0, 0, -1, rotDegrees), posX, posY, 0);

        int yOffset = 0;
        for(FormattedCharSequence displayTextSeparated : font.split(Component.translatable(textReader.getDisplayText()), MAX_LINE_WIDTH)){
            guiGraphics.drawCenteredString(font, displayTextSeparated,
                    posX, posY + yOffset,
                    0xfa692a);

            yOffset += font.lineHeight;
        }

        poseStack.popPose();
    }

    public static void addHostMessage(String text) {
        dialogDeque.add(text);
    }
}
