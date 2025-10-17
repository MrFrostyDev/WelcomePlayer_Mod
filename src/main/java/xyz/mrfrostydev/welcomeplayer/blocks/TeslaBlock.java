package xyz.mrfrostydev.welcomeplayer.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.BeaconBlockEntity;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.TeslaBlockEntity;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.PlayerObjectives;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

public class TeslaBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<TeslaBlock> CODEC = RecordCodecBuilder.mapCodec(in -> in.group(
            Properties.CODEC.fieldOf("properties").forGetter(TeslaBlock::properties)
    ).apply(in, TeslaBlock::new));

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty TOP = BooleanProperty.create("top");

    protected static final VoxelShape SHAPE_BASE = Block.box(0, 0, 0, 16, 12, 16);
    protected static final VoxelShape SHAPE_COIL = Block.box(4, 12, 4, 12, 20, 12);


    public TeslaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TOP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TOP);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if(state.getValue(TOP)){
            return false;
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if(state.getValue(TOP)){
            if(level.getBlockState(pos.below()).isEmpty()){
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), UPDATE_ALL);
            }
        }
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos blockPosUp = context.getClickedPos().above();
        return level.getBlockState(blockPosUp).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(blockPosUp)
                ? this.defaultBlockState().setValue(FACING, context.getHorizontalDirection())
                : null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            level.setBlock(pos, state.setValue(TOP, false), Block.UPDATE_ALL);
            level.setBlock(pos.above(), state.setValue(TOP, true), Block.UPDATE_ALL);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
        }
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        if(state.getValue(TOP)){
            return RenderShape.INVISIBLE;
        }
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(state.getValue(TOP)) return Shapes.empty();
        return Shapes.or(SHAPE_BASE, SHAPE_COIL);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(level instanceof ServerLevel svlevel
                && ObjectiveUtil.getGoingObjective(svlevel).is(level, PlayerObjectives.DISCHARGE)){
            ObjectiveUtil.addProgress(svlevel, 1);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    // |------------------------------------------------------------|
    // |------------------------Block Entity------------------------|
    // |------------------------------------------------------------|

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return !state.getValue(TOP) ? new TeslaBlockEntity(pos, state) : null;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == BlockRegistry.RETRO_TESLA_COIL_ENTITY.get() ? (BlockEntityTicker<T>) TeslaBlockEntity::tick : null;
    }
}
