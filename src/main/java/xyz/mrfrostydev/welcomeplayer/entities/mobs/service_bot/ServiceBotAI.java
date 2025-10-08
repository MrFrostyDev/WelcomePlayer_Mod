package xyz.mrfrostydev.welcomeplayer.entities.mobs.service_bot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.eradicator.EradicatorHoverToTargetSink;


public class ServiceBotAI {
    protected static final ImmutableList<SensorType<? extends Sensor<? super ServiceBotEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS
    );
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES
    );

    protected static Brain<?> makeBrain(ServiceBotEntity owner, Brain<ServiceBotEntity> brain) {
        initCoreActivity(owner, brain);
        initIdleActivity(owner, brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(ServiceBotEntity owner, Brain<ServiceBotEntity> brain) {
        brain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(new LookAtTargetSink(45, 90))
        );
    }

    private static void initIdleActivity(ServiceBotEntity owner, Brain<ServiceBotEntity> brain) {
        brain.addActivity(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, SetEntityLookTarget.create(EntityType.PLAYER, 8)),
                        Pair.of(1, new MoveToTargetSink()),
                        Pair.of(2,
                                new GateBehavior<>(
                                        ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT),
                                        ImmutableSet.of(),
                                        GateBehavior.OrderPolicy.ORDERED,
                                        GateBehavior.RunningPolicy.TRY_ALL,
                                        ImmutableList.of(
                                                Pair.of(new DoNothing(20, 100), 1),
                                                Pair.of(RandomStroll.stroll(1.0F), 2),
                                                Pair.of(new RandomLookAround(UniformInt.of(150, 250), 30.0F, 0.0F, 0.0F), 3)
                                        )
                                )
                        )
                )
        );
    }

    protected static void updateActivity(ServiceBotEntity owner) {
        Brain<ServiceBotEntity> brain = owner.getBrain();
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
    }

}
