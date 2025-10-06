package xyz.mrfrostydev.welcomeplayer.entities.mobs.handibot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import xyz.mrfrostydev.welcomeplayer.entities.ai.MeleeAttackWithHitbox;
import xyz.mrfrostydev.welcomeplayer.registries.SensorRegistry;

import java.util.Set;

public class HandibotAI {
    protected static final ImmutableList<SensorType<? extends Sensor<? super HandibotEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.HURT_BY,
            SensorRegistry.HANDIBOT_NEAREST_ATTACKABLE_SENSOR.get()
    );
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY
    );

    protected static Brain<?> makeBrain(HandibotEntity handibot, Brain<HandibotEntity> brain) {
        initCoreActivity(handibot, brain);
        initIdleActivity(handibot, brain);
        initFightActivity(handibot, brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(HandibotEntity handibot, Brain<HandibotEntity> brain) {
        brain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(new Swim(0.8F), new LookAtTargetSink(45, 90))
        );
    }

    private static void initIdleActivity(HandibotEntity handibot, Brain<HandibotEntity> brain) {
        brain.addActivity(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, StartAttacking.create(e -> e.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
                        Pair.of(1, StartAttacking.create(HandibotEntity::getHurtBy)),
                        Pair.of(2, new MoveToTargetSink(20, 40)),
                        Pair.of(3, new RunOne<>(ImmutableList.of(Pair.of(new DoNothing(20, 100), 1), Pair.of(RandomStroll.stroll(0.5F), 2))))
                )
        );
    }

    private static void initFightActivity(HandibotEntity handibot, Brain<HandibotEntity> brain) {
        brain.addActivityWithConditions(
                Activity.FIGHT,
                ImmutableList.of(
                        Pair.of(0, StopAttackingIfTargetInvalid.create(e -> !Sensor.isEntityAttackable(handibot, e))),
                        Pair.of(1, new MeleeAttackWithHitbox(HandibotEntity.ATTACK_COOLDOWN, handibot.getHitboxSets().getFirst())),
                        Pair.of(2, SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F))
                ),
                Set.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
                        Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT)
                )
        );
    }

    protected static void updateActivity(HandibotEntity handibot) {
        Brain<HandibotEntity> brain = handibot.getBrain();
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

}
