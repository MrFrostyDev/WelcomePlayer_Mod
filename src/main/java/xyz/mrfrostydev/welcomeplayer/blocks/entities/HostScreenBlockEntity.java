package xyz.mrfrostydev.welcomeplayer.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;

public class HostScreenBlockEntity extends BlockEntity implements GeoBlockEntity {
    private boolean isActive;

    public HostScreenBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.HOST_SCREEN_ENTITY.get(), pos, blockState);
        this.isActive = false;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof HostScreenBlockEntity blockEntity)) return;

        if(blockEntity.isActive()) return;
        if(level instanceof ServerLevel svlevel){
            if(AudienceUtil.isActive(svlevel)){
                blockEntity.isActive = true;
            }
            BlockState newState = level.getBlockState(pos);
            blockEntity.setChanged();
            level.setBlockAndUpdate(pos, state);
            level.sendBlockUpdated(pos, newState, newState, Block.UPDATE_ALL);
        }
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
    // |-------------------------Animations-------------------------|
    // |------------------------------------------------------------|

    private final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation INACTIVE_ANIM = RawAnimation.begin().thenPlay("host_screen.animation.inactive");
    private static final RawAnimation ACTIVE_ANIM = RawAnimation.begin().thenPlay("host_screen.animation.active");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::handleAnimation));
    }

    private <T extends GeoBlockEntity> PlayState handleAnimation(AnimationState<T> state) {
        if (this.isActive) {
            return state.setAndContinue(ACTIVE_ANIM);
        } else {
            return state.setAndContinue(INACTIVE_ANIM);
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animCache;
    }
}
