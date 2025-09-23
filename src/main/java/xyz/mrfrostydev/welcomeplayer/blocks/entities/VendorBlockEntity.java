package xyz.mrfrostydev.welcomeplayer.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xyz.mrfrostydev.welcomeplayer.client.gui.menus.blocks.VendorMenu;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;
import xyz.mrfrostydev.welcomeplayer.utils.VendorUtil;

public class VendorBlockEntity extends BlockEntity implements MenuProvider {
    public static final int MAX_COMMON = 10;
    public static final int MAX_UNCOMMON = 6;
    public static final int MAX_RARE = 2;
    public static final int CONTAINER_SIZE = MAX_COMMON + MAX_UNCOMMON + MAX_RARE;

    private boolean isActive;
    private int interest;

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return VendorBlockEntity.this.interest;
        }

        @Override
        public void set(int index, int value) {}

        @Override
        public int getCount() {
            return 1;
        }
    };

    public VendorBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.VENDOR_ENTITY.get(), pos, blockState);
        this.isActive = true;
        this.interest = 0;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof VendorBlockEntity blockEntity)) return;

        if(level instanceof ServerLevel svlevel){
            blockEntity.isActive = true;

            int global = AudienceUtil.getInterest(svlevel);
            if(global > blockEntity.interest){
                blockEntity.interest = global;
            }
        }

        level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
    }

    public boolean isActive() {
        return isActive;
    }

    // |------------------------------------------------------------|
    // |------------------------Data/Network------------------------|
    // |------------------------------------------------------------|

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.isActive = tag.getBoolean("isActive");
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("isActive", this.isActive);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries){
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider registries) {
        super.onDataPacket(connection, packet, registries);
    }

    // |------------------------------------------------------------|
    // |------------------------MenuProvider------------------------|
    // |------------------------------------------------------------|

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.welcomeplayer.vendor");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        if(player.level() instanceof ServerLevel svlevel){
            return new VendorMenu(containerId,
                    playerInventory,
                    this,
                    VendorUtil.getVendorShopItems(svlevel),
                    this.data,
                    ContainerLevelAccess.create(player.level(), worldPosition));
        }
        return null;
    }
}
