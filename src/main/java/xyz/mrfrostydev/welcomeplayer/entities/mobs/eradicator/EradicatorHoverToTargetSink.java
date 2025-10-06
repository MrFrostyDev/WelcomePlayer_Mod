package xyz.mrfrostydev.welcomeplayer.entities.mobs.eradicator;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import xyz.mrfrostydev.welcomeplayer.registries.MemoryModuleRegistry;

public class EradicatorHoverToTargetSink extends MoveToTargetSink {
    public EradicatorHoverToTargetSink(int minDuration, int maxDuration) {
        super(minDuration, maxDuration);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel svlevel, Mob owner) {
        if(owner instanceof EradicatorEntity entity){
             return super.checkExtraStartConditions(svlevel, owner) &&
                     entity.getBrain().getMemory(MemoryModuleRegistry.ERADICATOR_SHOOTING.get()).isEmpty();
        }

        return super.checkExtraStartConditions(svlevel, owner);
    }

    @Override
    protected void start(ServerLevel svlevel, Mob entity, long gameTime) {
        super.start(svlevel, entity, gameTime);
        entity.playSound(SoundEvents.BREEZE_SLIDE);
        entity.setPose(Pose.SLIDING);
    }

    @Override
    protected void stop(ServerLevel svlevel, Mob entity, long gameTime) {
        super.stop(svlevel, entity, gameTime);
        entity.setPose(Pose.STANDING);
    }
}
