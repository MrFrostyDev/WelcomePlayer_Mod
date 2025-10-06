package xyz.mrfrostydev.welcomeplayer.entities.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class HoverPathNavigation extends GroundPathNavigation {
    public HoverPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new WalkNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected boolean hasValidPathType(PathType pathType) {
        return (pathType != PathType.LAVA && pathType != PathType.DAMAGE_FIRE && pathType != PathType.DANGER_FIRE)
                && (pathType != PathType.WATER)
                ? super.hasValidPathType(pathType)
                : true;
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        return this.level.getBlockState(pos).is(Blocks.LAVA) || this.level.getBlockState(pos).is(Blocks.WATER) || super.isStableDestination(pos);
    }
}