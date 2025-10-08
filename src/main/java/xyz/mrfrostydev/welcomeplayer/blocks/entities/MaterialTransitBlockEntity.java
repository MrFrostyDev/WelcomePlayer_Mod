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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.client.gui.menus.blocks.MaterialTransitMenu;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

import java.util.ArrayList;
import java.util.List;

public class MaterialTransitBlockEntity extends BlockEntity implements IItemHandler, MenuProvider, GeoBlockEntity {
    public static final int CONTAINER_SIZE = 3;
    public static final int TRANSFER_TIME = 240;

    public ItemStackHandler itemContainer;

    private int progress;
    private boolean opened;

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return MaterialTransitBlockEntity.this.progress;
        }

        @Override
        public void set(int index, int value) {
            MaterialTransitBlockEntity.this.progress = value;
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public MaterialTransitBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.MATERIAL_TRANSIT_ENTITY.get(), pos, blockState);
        itemContainer = new ItemStackHandler(CONTAINER_SIZE){
            @Override
            protected void onContentsChanged(int slot) {
                if(level != null){
                    progress = 0;
                    level.sendBlockUpdated(pos, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                    setChanged();
                }
            }
        };
        this.progress = 0;
        this.opened = false;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof MaterialTransitBlockEntity blockEntity)) return;

        if(level.isClientSide())return;
        PlayerObjective objective = ObjectiveUtil.getGoingObjective(level);
        List<ItemStack> targetStacks = new ArrayList<>(3);

        for(int i=0; i<blockEntity.getSlots(); i++){
            ItemStack stack = getStackIfObjectiveMatch(level, objective, blockEntity.itemContainer.getStackInSlot(i));
            if(!stack.isEmpty()){
                targetStacks.add(stack);
            }
        }
        if(!targetStacks.isEmpty()){
            blockEntity.progress++;
            if(blockEntity.progress >= TRANSFER_TIME){
                blockEntity.completeTransit(targetStacks);
                blockEntity.progress = 0;
                targetStacks.clear();
            }
            blockEntity.setChanged();
        }
    }

    public void setOpen(){
        this.opened = true;
    }

    public void setClose(){
        this.opened = false;
    }

    public void completeTransit(List<ItemStack> stacks){
        int addedProgress = 0;
        for(ItemStack stack : stacks){
            addedProgress += stack.getCount();
            stack.setCount(0);
        }
        if(level instanceof ServerLevel svlevel){
            ObjectiveUtil.addProgress(svlevel, addedProgress);
        }
    }

    private static ItemStack getStackIfObjectiveMatch(Level level, PlayerObjective obj, ItemStack stack){
        if(stack.isEmpty()) return ItemStack.EMPTY;
        if(ObjectiveUtil.compareStackWithObjective(level, obj, stack)){
            return stack;
        }
        return ItemStack.EMPTY;
    }

    public int getProgress() {
        return progress;
    }

    // |------------------------------------------------------------|
    // |------------------------Data/Network------------------------|
    // |------------------------------------------------------------|

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("progress", this.progress);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.progress = tag.getInt("progress");
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
    // |-------------------------Animations-------------------------|
    // |------------------------------------------------------------|

    private final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation OPEN_ANIM = RawAnimation.begin().thenPlay("material_transit.animation.open");
    private static final RawAnimation CLOSE_ANIM = RawAnimation.begin().thenPlay("material_transit.animation.close");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::handleAnimation));
    }

    private <T extends GeoBlockEntity> PlayState handleAnimation(AnimationState<T> state) {
        if(this.opened){
            return state.setAndContinue(OPEN_ANIM);
        }
        else{
            return state.setAndContinue(CLOSE_ANIM);
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animCache;
    }

    // |------------------------------------------------------------|
    // |------------------------Menu Provider-----------------------|
    // |------------------------------------------------------------|

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.welcomeplayer.material_transit");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new MaterialTransitMenu(containerId,
                playerInventory,
                this,
                this.data,
                ContainerLevelAccess.create(player.level(), worldPosition));
    }

    // |------------------------------------------------------------|
    // |------------------------Item Handling-----------------------|
    // |------------------------------------------------------------|

    @Override
    public int getSlots() {
        return CONTAINER_SIZE;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return itemContainer.getStackInSlot(slot).copy();
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        ItemStack itemStack = stack.copy();
        return this.itemContainer.insertItem(slot, itemStack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack targetStack = itemContainer.getStackInSlot(slot);
        ItemStack returnItem;
        if(amount < targetStack.getCount()){
            returnItem = targetStack.copy();
            returnItem.setCount(amount);
            targetStack.setCount(targetStack.getCount() - amount);
        }
        else{
            returnItem = targetStack.copy();
            itemContainer.setStackInSlot(slot, ItemStack.EMPTY);
        }

        return returnItem;
    }

    @Override
    public int getSlotLimit(int slot) {
        return itemContainer.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return true;
    }
}
