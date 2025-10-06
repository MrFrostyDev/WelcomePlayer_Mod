package xyz.mrfrostydev.welcomeplayer.client.gui.menus.blocks;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

public class MaterialTransitScreen extends AbstractContainerScreen<MaterialTransitMenu> {
    private static final ResourceLocation MENU_RESOURCE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "textures/gui/container/material_transit_menu.png");
    private static final ResourceLocation PROGRESS_RESOURCE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "container/material_transit/progress");

    public static final int PROGRESS_WIDTH = 40;
    public static final int PROGRESS_HEIGHT = 32;

    private final MaterialTransitMenu menu;
    private Inventory plyInventory;
    protected Player player;

    public MaterialTransitScreen(MaterialTransitMenu menu, Inventory plyInventory, Component title)  {
        super(menu, plyInventory, Component.empty());

        this.plyInventory = plyInventory;
        this.menu = menu;
        this.player = plyInventory.player;
        this.imageWidth = 228;
        this.imageHeight = 178;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int posX = (this.width - this.imageWidth) / 2;
        int posY = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(MENU_RESOURCE, posX, posY, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int posX = Mth.ceil(this.width - PROGRESS_WIDTH) / 2;
        int posY = (Mth.ceil(this.height - PROGRESS_HEIGHT) / 2) - 28;

        int progressInPixels = Mth.ceil(menu.getProgress() * PROGRESS_HEIGHT);
        guiGraphics.blitSprite(PROGRESS_RESOURCE,
                PROGRESS_WIDTH, PROGRESS_HEIGHT,
                0, PROGRESS_HEIGHT - progressInPixels,
                posX, posY - progressInPixels,
                PROGRESS_WIDTH, progressInPixels
        );
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {}
}
