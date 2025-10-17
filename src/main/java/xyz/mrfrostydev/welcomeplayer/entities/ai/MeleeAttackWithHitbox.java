package xyz.mrfrostydev.welcomeplayer.entities.ai;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import xyz.mrfrostydev.welcomeplayer.entities.EntityHitboxSet;

import java.util.List;

public class MeleeAttackWithHitbox extends Behavior<Mob> {
    protected EntityHitboxSet hitboxSet;
    protected int cooldownBetweenAttacks;

    public MeleeAttackWithHitbox(int cooldownBetweenAttacks, EntityHitboxSet hitboxSet) {
        super(
                ImmutableMap.of(
                        MemoryModuleType.LOOK_TARGET,
                        MemoryStatus.REGISTERED,
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModuleType.ATTACK_COOLING_DOWN,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
                        MemoryStatus.VALUE_PRESENT
                ),
                hitboxSet.getTime()
        );
        this.hitboxSet = hitboxSet;
        this.cooldownBetweenAttacks = cooldownBetweenAttacks;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel svlevel, Mob owner) {
        LivingEntity target = owner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
        List<LivingEntity> nearestLivingEntity = owner.getBrain().getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).get();
        return owner.isWithinMeleeAttackRange(target)
                && nearestLivingEntity.contains(target)
                && !hitboxSet.isActive();
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Mob owner, long gameTime) {
        Brain<?> brain = owner.getBrain();
        return brain.hasMemoryValue(MemoryModuleType.NEAREST_LIVING_ENTITIES) && brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET);
    }

    @Override
    protected void start(ServerLevel level, Mob owner, long gameTime) {
        hitboxSet.start();
    }

    @Override
    protected void tick(ServerLevel svlevel, Mob owner, long gameTime) {
        LivingEntity target = owner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
        owner.getLookControl().setLookAt(target, 30.0F, 30.0F);
        owner.swing(InteractionHand.MAIN_HAND);
        hitboxSet.tick(svlevel);
    }

    @Override
    protected void stop(ServerLevel level, Mob owner, long gameTime) {
        owner.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, cooldownBetweenAttacks);
        hitboxSet.stop();
    }
}
