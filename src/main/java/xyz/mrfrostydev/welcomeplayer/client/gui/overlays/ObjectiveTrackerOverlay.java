package xyz.mrfrostydev.welcomeplayer.client.gui.overlays;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.ClientObjectiveData;
import xyz.mrfrostydev.welcomeplayer.data.ObjectiveManagerData;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.utils.GraphicUtil;
import xyz.mrfrostydev.welcomeplayer.utils.TextReader;

public class ObjectiveTrackerOverlay implements LayeredDraw.Layer {
    private static final ResourceLocation BACK_BAR_SPRITE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "objective_back_bar");
    private static final ResourceLocation PROGRESS_BAR_SPRITE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "objective_progress_bar");

    private static final int TIME_PER_CHAR = 6;
    private final int MAX_LINE_WIDTH = 180;

    private Minecraft minecraft;
    private Font font;

    private PlayerObjective objective;
    private TextReader textReader = new TextReader(TIME_PER_CHAR);

    private static final int BACK_BAR_WIDTH = 46;
    private static final int BACK_BAR_HEIGHT = 9;

    private static final int PROGRESS_BAR_WIDTH = 42;
    private static final int PROGRESS_BAR_HEIGHT = 5;

    public ObjectiveTrackerOverlay() {
        this.minecraft = Minecraft.getInstance();
        this.font = Minecraft.getInstance().font;
        this.objective = PlayerObjective.NOTHING;
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;

        if(player == null)return;
        if(Minecraft.getInstance().options.hideGui)return;
        if(player.isSpectator())return;

        ObjectiveManagerData data = ClientObjectiveData.get();
        if(data.getGoingObjective().is(PlayerObjective.NOTHING)){
            objective = data.getGoingObjective();
            return;
        }

        if(!data.getGoingObjective().is(objective)){
            objective = data.getGoingObjective();
            textReader.addPermenantText(Component.translatable("objective.welcomeplayer." + objective.id(), data.getMaxProgress()).getString());
        }

        textReader.tick();
        this.renderBar(guiGraphics, data);
        this.renderText(guiGraphics);
    }

    public void renderText(GuiGraphics guiGraphics){
        int posX = 15;
        int posY = ((guiGraphics.guiHeight() / 2) - 40);

        if(font.getSplitter().stringWidth(textReader.getDisplayText()) > MAX_LINE_WIDTH)
            posY -= font.lineHeight;

        int yOffset = 0;
        for(FormattedCharSequence displayTextSeparated : font.split(Component.literal(textReader.getDisplayText()), MAX_LINE_WIDTH)){
            GraphicUtil.drawStringWithScale(guiGraphics, font, displayTextSeparated,
                    posX, posY + yOffset, 0.8F, 0xfa692a, true);

            yOffset += font.lineHeight;
        }
    }

    public void renderBar(GuiGraphics guiGraphics, ObjectiveManagerData data){
        int progress = data.getProgress();
        int maxProgress = data.getMaxProgress();
        int progressBarWidth = Mth.ceil(PROGRESS_BAR_WIDTH * Math.min(progress / Math.max(1.0, maxProgress), 1));

        int posX = 15;
        int posY = ((guiGraphics.guiHeight() / 2) - 30) ;

        /*
         *  (ResourceLocation sprite,
         *   int textureWidth, int textureWidth,
         *   int uPosition, int uPosition,
         *   int x, int x,
         *   int uWidth, int vHeight)
         */

        // Back Bar
        guiGraphics.blitSprite(BACK_BAR_SPRITE,
                BACK_BAR_WIDTH, BACK_BAR_HEIGHT,
                0, 0,
                posX, posY,
                BACK_BAR_WIDTH, BACK_BAR_HEIGHT);

        // Progress Bar
        guiGraphics.blitSprite(PROGRESS_BAR_SPRITE,
                BACK_BAR_WIDTH, PROGRESS_BAR_HEIGHT,
                0, 0,
                posX + 2, posY + 2,
                progressBarWidth, PROGRESS_BAR_HEIGHT);
    }
}
