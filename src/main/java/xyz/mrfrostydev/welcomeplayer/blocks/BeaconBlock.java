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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.BeaconBlockEntity;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.PlayerObjectives;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

public class BeaconBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<BeaconBlock> CODEC = RecordCodecBuilder.mapCodec(in -> in.group(
            BlockBehaviour.Properties.CODEC.fieldOf("properties").forGetter(BeaconBlock::properties)
    ).apply(in, BeaconBlock::new));

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");
    public static final BooleanProperty TOP = BooleanProperty.create("top");

    protected static final VoxelShape SHAPE_BASE = Block.box(0, 0, 0, 16, 12, 16);
    protected static final VoxelShape SHAPE_BASE_2_N = Block.box(0, 12, 0, 16, 20, 8);
    protected static final VoxelShape SHAPE_BASE_2_S = Block.box(0, 12, 8, 16, 20, 16);
    protected static final VoxelShape SHAPE_BASE_2_E = Block.box(8, 12, 0, 16, 20, 16);
    protected static final VoxelShape SHAPE_BASE_2_W = Block.box(0, 12, 0, 8, 20, 16);

    public BeaconBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ACTIVATED, false)
                .setValue(TOP, false));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(state.getValue(TOP)) return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        if(state.getValue(ACTIVATED)){
            if(level.isClientSide){
                Minecraft.getInstance().gui.setOverlayMessage(
                        Component.translatable("message.welcomeplayer.beacon.already_active"), false);
            }
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }

        PlayerObjective objective = ObjectiveUtil.getGoingObjective(level);
        if(objective.is(level, PlayerObjectives.EXPLORER) || objective.is(level, PlayerObjectives.SURVEYOR)){
            if(stack.is(ItemRegistry.BATTERY)){
                if(level instanceof ServerLevel svlevel){
                    ObjectiveUtil.addProgress(svlevel, 1);
                }
                stack.consume(1, player);
                level.setBlockAndUpdate(pos, state.setValue(ACTIVATED, true));
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                return ItemInteractionResult.SUCCESS;
            }
            else if (level.isClientSide){
                Minecraft.getInstance().gui.setOverlayMessage(
                        Component.translatable("message.welcomeplayer.beacon.need_battery"), false);
            }
        }
        else if (level.isClientSide) {
            Minecraft.getInstance().gui.setOverlayMessage(
                    Component.translatable("message.welcomeplayer.beacon.no_event"), false);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVATED, TOP);
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

        Direction direction = state.getValue(FACING);
        VoxelShape shape_2 = switch (direction) {
            case SOUTH -> SHAPE_BASE_2_S;
            case EAST -> SHAPE_BASE_2_E;
            case WEST -> SHAPE_BASE_2_W;
            default -> SHAPE_BASE_2_N;
        };

        return Shapes.or(SHAPE_BASE, shape_2);
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
        return !state.getValue(TOP) ? new BeaconBlockEntity(pos, state) : null;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == BlockRegistry.BEACON_ENTITY.get() ? (BlockEntityTicker<T>) BeaconBlockEntity::tick : null;
    }
}
