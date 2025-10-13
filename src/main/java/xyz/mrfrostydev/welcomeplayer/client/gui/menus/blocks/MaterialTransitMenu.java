package xyz.mrfrostydev.welcomeplayer.client.gui.menus.blocks;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.MaterialTransitBlockEntity;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.MenuRegistry;

import java.util.Objects;

public class MaterialTransitMenu extends AbstractContainerMenu {
    public final MaterialTransitBlockEntity blockEntity;
    public final ContainerLevelAccess access;
    public final ContainerData data;

    public MaterialTransitMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, getBlockEntity(inv, extraData), new SimpleContainerData(MaterialTransitBlockEntity.CONTAINER_SIZE), ContainerLevelAccess.NULL);
    }

    public MaterialTransitMenu(int id, Inventory plyInventory, MaterialTransitBlockEntity blockEntity, ContainerData data, ContainerLevelAccess access) {
        super(MenuRegistry.MATERIAL_TRANSIT_MENU.get(), id);

        this.blockEntity = blockEntity;
        checkContainerDataCount(data, 1);
        this.data = data;
        this.access = access;

        // Material Transit Slots
        for (int i = 0; i < 3; i++) {
            this.addSlot(new SlotItemHandler(blockEntity.itemContainer, i, 84 + i * 22, 56));
        }

        // Player Inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(plyInventory, j + i * 9 + 9, 34 + j * 18, 85 + i * 18));
            }
        }

        // Player Hotbar
        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(plyInventory, k, 34 + k * 18, 143));
        }

        this.addDataSlots(data);
        this.blockEntity.setOpen();
    }

    private static MaterialTransitBlockEntity getBlockEntity(final Inventory plyInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(plyInventory, "plyInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final BlockEntity blockAtPos = plyInventory.player.level().getBlockEntity(data.readBlockPos());
        if (blockAtPos instanceof MaterialTransitBlockEntity) {
            return (MaterialTransitBlockEntity) blockAtPos;
        }
        throw new IllegalStateException("The block entity is not correct at MaterialTransit#getBlockEntity " + blockAtPos);
    }

    // Total slots 0-38
    // Container 0-2
    // Inventory 3-29
    // Hotbar 30-38
    @Override
    public ItemStack quickMoveStack(Player player, int selectedSlotIndex) {
        // The quick moved slot stack
        ItemStack selectedStack = ItemStack.EMPTY;
        // The quick moved slot
        Slot selectedSlot = this.slots.get(selectedSlotIndex);

        // If the slot is in the valid range and the slot is not empty, otherwise skip quickMove.
        if (selectedSlot == null || !selectedSlot.hasItem())return ItemStack.EMPTY;

        ItemStack rawStack = selectedSlot.getItem();
        selectedStack = rawStack.copy();

        // Check the input slots are selected.
        if (selectedSlotIndex >= 0 && selectedSlotIndex < 3) {
            // Try to move the result slot into the player inventory/hotbar
            if (!this.moveItemStackTo(rawStack, 3, 39, true)) {
                // If cannot move, no longer quick move
                return ItemStack.EMPTY;
            }
        }
        // If slot was selected in player inventory, then move it to either
        // container, player inventory or hotbar.
        else if (selectedSlotIndex >= 3 && selectedSlotIndex < 39) {
            // Check if we can move it into the input slot.
            if (!this.moveItemStackTo(rawStack, 0, 3, false)) {
                // Checks if it was selected in inventory
                if (selectedSlotIndex < 30) {
                    // Try to move item into hotbar
                    if (!this.moveItemStackTo(rawStack, 30, 39, false)) {
                        return ItemStack.EMPTY; // If cannot move, no longer quick move
                    }
                }
                // Assume we select a hotbar item, then try to move into player inventory slot
                else if (!this.moveItemStackTo(rawStack, 3, 30, false)) {
                    return ItemStack.EMPTY; // If cannot move, no longer quick move
                }
            }
        }

        if (rawStack.isEmpty()) {
            // If the raw stack has completely moved out of the slot, set the slot to the empty stack
            selectedSlot.set(ItemStack.EMPTY);
        }
        else {
            // Otherwise, notify the slot that the stack count has changed
            selectedSlot.setChanged();
        }

        if (rawStack.getCount() == selectedStack.getCount()) {
            // If the raw stack was not able to move to another slot, no longer quick move
            return ItemStack.EMPTY;
        }
        // Execute logic on what to do post move with the remaining stack
        selectedSlot.onTake(player, rawStack);
        return selectedStack;
    }

    public float getProgress() {
        int i = this.data.get(0);
        return i != 0 ? Mth.clamp((float)i / MaterialTransitBlockEntity.TRANSFER_TIME, 0.0F, 1.0F) : 0.0F;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.blockEntity.setClose();
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, BlockRegistry.MATERIAL_TRANSIT.get());
    }
}
