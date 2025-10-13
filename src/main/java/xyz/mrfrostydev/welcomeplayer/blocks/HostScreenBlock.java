package xyz.mrfrostydev.welcomeplayer.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.HostScreenBlockEntity;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.MaterialTransitBlockEntity;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;

public class HostScreenBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<HostScreenBlock> CODEC = simpleCodec(HostScreenBlock::new);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public HostScreenBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
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
        return new HostScreenBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == BlockRegistry.HOST_SCREEN_ENTITY.get() ? (BlockEntityTicker<T>) HostScreenBlockEntity::tick : null;
    }
}
