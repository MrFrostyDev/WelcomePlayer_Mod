package xyz.mrfrostydev.welcomeplayer.client.gui.menus.blocks;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.VendorBlockEntity;
import xyz.mrfrostydev.welcomeplayer.data.VendorItem;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.MenuRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VendorMenu extends AbstractContainerMenu {
    public final VendorBlockEntity blockEntity;
    public final ContainerLevelAccess access;
    public final ContainerData data;
    private List<VendorItem> shopItems;

    public VendorMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, getBlockEntity(inv, extraData), new ArrayList<VendorItem>(), new SimpleContainerData(1), ContainerLevelAccess.NULL);
    }

    public VendorMenu(int id, Inventory plyInventory, VendorBlockEntity blockEntity, List<VendorItem> shopItems, ContainerData data, ContainerLevelAccess access) {
        super(MenuRegistry.VENDOR_MENU.get(), id);

        this.blockEntity = blockEntity;
        this.shopItems = shopItems;
        this.data = data;
        this.access = access;

        this.addDataSlots(data);
    }

    private static VendorBlockEntity getBlockEntity(final Inventory plyInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(plyInventory, "plyInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final BlockEntity blockAtPos = plyInventory.player.level().getBlockEntity(data.readBlockPos());
        if (blockAtPos instanceof VendorBlockEntity) {
            return (VendorBlockEntity) blockAtPos;
        }
        throw new IllegalStateException("The block entity is not correct at VendorMenu#getBlockEntity " + blockAtPos);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int selectedSlotIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, BlockRegistry.VENDOR_BOTTOM.get());
    }

    public void setShopItems(List<VendorItem> shopItems) {
        this.shopItems = shopItems;
    }

    public List<VendorItem> getShopItems() {
        return this.shopItems;
    }
}
