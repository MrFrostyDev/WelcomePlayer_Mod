package xyz.mrfrostydev.welcomeplayer.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.mrfrostydev.welcomeplayer.network.StartShowIntroductionPacket;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;

public class ShowActivatorBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    public ShowActivatorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVATED, false));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(level instanceof ServerLevel svlevel){
            if(AudienceUtil.isActive(svlevel) && !state.getValue(ACTIVATED)) {
                level.setBlockAndUpdate(pos, state.setValue(ACTIVATED, Boolean.valueOf(true)));
            }
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);

        if(level instanceof ServerLevel svlevel){
            if (level.hasNeighborSignal(pos) && !state.getValue(ACTIVATED) && !AudienceUtil.isActive(svlevel)) {
                level.setBlockAndUpdate(pos, state.setValue(ACTIVATED, Boolean.valueOf(true)));
                PacketDistributor.sendToAllPlayers(new StartShowIntroductionPacket());
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(ACTIVATED, false);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(ShowActivatorBlock::new);
    }
}
