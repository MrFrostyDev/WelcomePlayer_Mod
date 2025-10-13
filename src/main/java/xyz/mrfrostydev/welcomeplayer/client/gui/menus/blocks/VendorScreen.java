package xyz.mrfrostydev.welcomeplayer.client.gui.menus.blocks;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.VendorBlockEntity;
import xyz.mrfrostydev.welcomeplayer.data.VendorItem;
import xyz.mrfrostydev.welcomeplayer.network.ClientVendorUpdatePacket;
import xyz.mrfrostydev.welcomeplayer.utils.ClientUtil;
import xyz.mrfrostydev.welcomeplayer.utils.GraphicUtil;
import xyz.mrfrostydev.welcomeplayer.utils.VendorUtil;

import java.util.List;

public class VendorScreen extends AbstractContainerScreen<VendorMenu> {
    private static final ResourceLocation MENU_RESOURCE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "textures/gui/container/vendor_menu.png");
    private static final ResourceLocation SPEECH_SPRITE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "container/vendor/speech_bubble");
    private static final ResourceLocation UNLOCK_SPRITE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "container/vendor/unlock");
    private static final ResourceLocation UNLOCK_HOVER_SPRITE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "container/vendor/unlock_hover");
    private static final ResourceLocation VENDOR_SPRITE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "container/vendor/vendor");
    private static final ResourceLocation VENDOR_OFFER_SPRITE = ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "container/vendor/vendor_offer");
    private static final int[] VENDOR_ANIM = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65};

    private static final int UNLOCK_TEXTURE_WIDTH = 34;
    private static final int UNLOCK_TEXTURE_HEIGHT = 24;
    private static final int SPEECH_TEXTURE_WIDTH = 60;
    private static final int SPEECH_TEXTURE_HEIGHT = 39;
    private static final int VENDOR_TEXTURE_WIDTH = 74;
    private static final int VENDOR_TEXTURE_HEIGHT = 65;

    private final VendorMenu menu;
    private Inventory plyInventory;
    private boolean hoveringOption;
    protected Player player;

    public VendorScreen(VendorMenu menu, Inventory plyInventory, Component title) {
        super(menu, plyInventory, Component.empty());

        this.plyInventory = plyInventory;
        this.menu = menu;
        this.player = plyInventory.player;
        this.hoveringOption = false;
        this.imageWidth = 228;
        this.imageHeight = 178;
    }

    @Override
    protected void init() {
        super.init();
        int posX = (this.width / 2) - 95;
        int posY = (this.height / 2) - 72;
        for(int i = 0; i < VendorBlockEntity.CONTAINER_SIZE; i++){
            if(i % 4 == 0 && i != 0){
                posY += UNLOCK_TEXTURE_HEIGHT + 5;
                posX = (this.width / 2) - 95;
            }
            this.addRenderableWidget(new VendorOptionWidget(i, posX, posY, UNLOCK_TEXTURE_WIDTH, UNLOCK_TEXTURE_HEIGHT, null));
            posX += UNLOCK_TEXTURE_WIDTH + 2;
        }

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int posX = (this.width - this.imageWidth) / 2;
        int posY = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(MENU_RESOURCE, posX, posY, 0, 0, this.imageWidth, this.imageHeight);

        int venderOffsetX = 162;
        int venderOffsetY = 80;
        if(hoveringOption){
            guiGraphics.blitSprite(VENDOR_OFFER_SPRITE,
                    VENDOR_TEXTURE_WIDTH, 130,
                    0, VENDOR_ANIM[ClientUtil.getRenderTick() % VENDOR_ANIM.length],
                    posX + venderOffsetX, posY + venderOffsetY,
                    VENDOR_TEXTURE_WIDTH, VENDOR_TEXTURE_HEIGHT
            );
            hoveringOption = false;
        }
        else{
            guiGraphics.blitSprite(VENDOR_SPRITE,
                    VENDOR_TEXTURE_WIDTH, 130,
                    0, VENDOR_ANIM[ClientUtil.getRenderTick() % VENDOR_ANIM.length],
                    posX + venderOffsetX, posY + venderOffsetY,
                    VENDOR_TEXTURE_WIDTH, VENDOR_TEXTURE_HEIGHT
            );
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {}

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        /*
            ResourceLocation sprite,
            int textureWidth, int textureHeight,
            int uPosition, int vPosition,
            int x, int y,
            int blitOffset, int uWidth, int vHeight
        */

        Component balanceComp = Component.translatable("block.welcomeplayer.vendor.balance").append(
                Component.literal("" + VendorUtil.getBatteryBalance(this.plyInventory)));
        guiGraphics.drawString(font, balanceComp,
                (guiGraphics.guiWidth() / 2) - 87, (guiGraphics.guiHeight() / 2) + 56,
                0x8b1919, false
        );
    }

    private class VendorOptionWidget extends AbstractWidget {
        private int index;

        public VendorOptionWidget(int index, int x, int y, int width, int height, Component message) {
            super(x, y, width, height, message);
            this.index = index;
        }

        @Override
        protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            if(shopItemAvailable()){
                this.active = true;
                VendorItem shopItem = menu.getShopItems().get(index);
                ItemStack focusStack = shopItem.getItem();

                if(this.isHovered){
                    int posX = 56;
                    int posY = -55;

                    guiGraphics.blitSprite(UNLOCK_HOVER_SPRITE,
                            getX(), getY(),
                            UNLOCK_TEXTURE_WIDTH, UNLOCK_TEXTURE_HEIGHT
                    );

                    guiGraphics.blitSprite(SPEECH_SPRITE,
                            (guiGraphics.guiWidth() / 2) + posX, (guiGraphics.guiHeight() / 2) + posY,
                            SPEECH_TEXTURE_WIDTH, SPEECH_TEXTURE_HEIGHT
                    );

                    Component c = Component.translatable(focusStack.getDescriptionId()).withStyle(Style.EMPTY.withColor(0x8b1919));
                    List<FormattedCharSequence> formatted = font.split(c, SPEECH_TEXTURE_WIDTH - 8);
                    float scaleText = 1.0F;
                    int yOffsetAmount = Mth.ceil(font.lineHeight * scaleText);

                    int xOffset = 0;
                    int yOffset = 0;
                    for(FormattedCharSequence line : formatted){
                        GraphicUtil.drawStringWithScale(guiGraphics, font, line,
                                ((float)guiGraphics.guiWidth() / 2) + posX + 4 + xOffset, ((float) guiGraphics.guiHeight() / 2) + posY + 4 + yOffset,
                                scaleText,
                                0, false
                        );
                        yOffset += yOffsetAmount;
                    }

                    Component priceComp = Component.literal("$").withColor(0x8b1919)
                            .append(Component.literal(String.valueOf(shopItem.price())).withStyle(Style.EMPTY.withBold(true)).withColor(0x8b1919));
                    guiGraphics.drawString(font, priceComp,
                            (guiGraphics.guiWidth() / 2) + posX + 4, (guiGraphics.guiHeight() / 2) + posY + 23,
                            0, false
                    );
                    hoveringOption = true;
                }
                else{
                    guiGraphics.blitSprite(UNLOCK_SPRITE,
                            getX(), getY(),
                            UNLOCK_TEXTURE_WIDTH, UNLOCK_TEXTURE_HEIGHT
                    );
                }

                int posXItem = getX() + 9;
                int posYItem = getY() + ((this.height - 16) / 2) + 1;
                guiGraphics.renderItem(focusStack, posXItem, posYItem);
                guiGraphics.renderItemDecorations(font, focusStack, posXItem, posYItem);
            }
            else{
                this.active = false;
            }
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}

        @Override
        public void onClick(double mouseX, double mouseY, int button) {
            if(shopItemAvailable()){
                ItemStack focusStack = menu.getShopItems().get(index).getItem();
                int price = menu.getShopItems().get(index).price();

                if(VendorUtil.tryPurchase(plyInventory, price)){
                    PacketDistributor.sendToServer(ClientVendorUpdatePacket.create(focusStack, price));
                }
            }
        }

        private boolean shopItemAvailable(){
            return menu.getShopItems().size() > index
                    && menu.data.get(0) >= menu.getShopItems().get(index).minInterest();
        }
    }
}
