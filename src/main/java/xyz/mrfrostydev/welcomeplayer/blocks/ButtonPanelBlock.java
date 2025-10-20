package xyz.mrfrostydev.welcomeplayer.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ButtonPanelBlock extends ButtonBlock {

    protected static final VoxelShape CEILING_AABB = Block.box(2.0, 14.0, 2.0, 14.0, 16.0, 14.0);
    protected static final VoxelShape FLOOR_AABB = Block.box(2.0, 0.0, 2.0, 14.0, 2.0, 14.0);
    protected static final VoxelShape NORTH_AABB = Block.box(2.0, 2.0, 14.0, 14.0, 14.0, 16.0);
    protected static final VoxelShape SOUTH_AABB = Block.box(2.0, 2.0, 0.0, 14.0, 14.0, 2.0);
    protected static final VoxelShape WEST_AABB = Block.box(14.0, 2.0, 2.0, 16.0, 14.0, 14.0);
    protected static final VoxelShape EAST_AABB = Block.box(0.0, 2.0, 2.0, 2.0, 14.0, 14.0);

    public ButtonPanelBlock(BlockSetType type, int ticksToStayPressed, Properties properties) {
        super(type, ticksToStayPressed, properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        switch ((AttachFace)state.getValue(FACE)) {
            case FLOOR:
                return FLOOR_AABB;
            case WALL:
                return switch (direction) {
                    case EAST -> EAST_AABB;
                    case WEST -> WEST_AABB;
                    case SOUTH -> SOUTH_AABB;
                    case NORTH, UP, DOWN -> NORTH_AABB;
                };
            case CEILING:
            default:
                return CEILING_AABB;
        }
    }

}
