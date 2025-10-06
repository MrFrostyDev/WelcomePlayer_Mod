package xyz.mrfrostydev.welcomeplayer.blocks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.VendorBlockEntity;
import xyz.mrfrostydev.welcomeplayer.network.ServerVendorMenuPacket;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.VendorUtil;

public class VendorBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<VendorBlock> CODEC = RecordCodecBuilder.mapCodec(in -> in.group(
            BlockBehaviour.Properties.CODEC.fieldOf("properties").forGetter(BlockBehaviour::properties),
            Codec.BOOL.fieldOf("top").forGetter(VendorBlock::isTop)
    ).apply(in, VendorBlock::new));

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty TOP = BooleanProperty.create("top");

    public VendorBlock(Properties properties, boolean isTop) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TOP, isTop));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TOP);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        super.useWithoutItem(state, level, pos, player, hitResult);

        if(state.getValue(TOP)){
            BlockPos neighbourBlockPos = pos.below();
            if(level.getBlockEntity(neighbourBlockPos) instanceof VendorBlockEntity neighbourBlockEntity){
                if(!neighbourBlockEntity.isActive()) return InteractionResult.FAIL;
                player.openMenu(neighbourBlockEntity, neighbourBlockPos);
            }
        }
        else {
            if (!(level.getBlockEntity(pos) instanceof VendorBlockEntity blockEntity)) return InteractionResult.FAIL;
            if(!blockEntity.isActive()) return InteractionResult.FAIL;
            player.openMenu(blockEntity, pos);
        }

        if(!level.isClientSide()){
            PacketDistributor.sendToPlayer(
                    (ServerPlayer) player,
                    new ServerVendorMenuPacket(
                            VendorUtil.getVendorShopItems((ServerLevel)level)
                    )
            );
        }

        player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME);
        return InteractionResult.CONSUME;
    }

    public boolean isTop() {
        return this.defaultBlockState().getValue(TOP);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    // |------------------------------------------------------------|
    // |------------------------Block Entity------------------------|
    // |------------------------------------------------------------|

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return !isTop() ? new VendorBlockEntity(pos, state) : null;
    }

    @SuppressWarnings("unchecked") // Due to generics, an unchecked cast is necessary here.
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == BlockRegistry.VENDOR_ENTITY.get() ? (BlockEntityTicker<T>) VendorBlockEntity::tick : null;
    }
}
