package xyz.mrfrostydev.welcomeplayer.entities.mobs.eradicator;

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
import xyz.mrfrostydev.welcomeplayer.entities.ai.ChangeTargetIfCloser;
import xyz.mrfrostydev.welcomeplayer.registries.MemoryModuleRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.SensorRegistry;

import java.util.Set;

public class EradicatorAI {
    protected static final ImmutableList<SensorType<? extends Sensor<? super EradicatorEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.HURT_BY,
            SensorRegistry.ERADICATOR_NEAREST_ATTACKABLE_SENSOR.get()
    );
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleRegistry.ERADICATOR_SAWING.get(),
            MemoryModuleRegistry.ERADICATOR_SHOOT_CHARGING.get(),
            MemoryModuleRegistry.ERADICATOR_SHOOTING.get(),
            MemoryModuleRegistry.ERADICATOR_SHOOT_RECOVERING.get(),
            MemoryModuleRegistry.ERADICATOR_SHOOT_COOLDOWN.get(),
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY
    );

    protected static Brain<?> makeBrain(EradicatorEntity entity, Brain<EradicatorEntity> brain) {
        initCoreActivity(entity, brain);
        initIdleActivity(entity, brain);
        initFightActivity(entity, brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(EradicatorEntity entity, Brain<EradicatorEntity> brain) {
        brain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(new LookAtTargetSink(45, 90))
        );
    }

    private static void initIdleActivity(EradicatorEntity entity, Brain<EradicatorEntity> brain) {
        brain.addActivity(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, StartAttacking.create(e -> e.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
                        Pair.of(1, StartAttacking.create(EradicatorEntity::getHurtBy)),
                        Pair.of(2, new EradicatorHoverToTargetSink(20, 40)),
                        Pair.of(3, new RunOne<>(ImmutableList.of(Pair.of(new DoNothing(20, 100), 1), Pair.of(RandomStroll.stroll(0.5F), 2))))
                )
        );
    }

    private static void initFightActivity(EradicatorEntity entity, Brain<EradicatorEntity> brain) {
        brain.addActivityWithConditions(
                Activity.FIGHT,
                ImmutableList.of(
                        Pair.of(0, StopAttackingIfTargetInvalid.create((e, target) -> {})),
                        Pair.of(1, ChangeTargetIfCloser.create(0.7F, 20)),
                        Pair.of(2, new EradicatorMeleeAttack(EradicatorEntity.SAW_ATTACK_COOLDOWN, entity.getHitboxSets().getFirst())),
                        Pair.of(3, new EradicatorShoot()),
                        Pair.of(4, SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F))
                ),
                Set.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
                        Pair.of(MemoryModuleType.NEAREST_ATTACKABLE, MemoryStatus.VALUE_PRESENT),
                        Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT)
                )
        );
    }

    protected static void updateActivity(EradicatorEntity entity) {
        Brain<EradicatorEntity> brain = entity.getBrain();
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }
}
