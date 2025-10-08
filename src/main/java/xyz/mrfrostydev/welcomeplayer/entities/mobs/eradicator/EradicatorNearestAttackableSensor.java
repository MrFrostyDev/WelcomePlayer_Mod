package xyz.mrfrostydev.welcomeplayer.entities.mobs.eradicator;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestLivingEntitySensor;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.Collection;
import java.util.Set;

public class EradicatorNearestAttackableSensor extends NearestLivingEntitySensor<EradicatorEntity> {
    private static final TargetingConditions TARGET_CONDITIONS = TargetingConditions.forCombat().range(EradicatorEntity.TARGETING_RANGE);
    private static final TargetingConditions TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING = TargetingConditions.forCombat()
            .range(EradicatorEntity.TARGETING_RANGE)
            .ignoreInvisibilityTesting();

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.NEAREST_LIVING_ENTITIES,
                MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES
        );
    }

    protected void doTick(ServerLevel svlevel, EradicatorEntity entity) {
        super.doTick(svlevel, entity);
        entity.getBrain()
                .getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES)
                .stream()
                .flatMap(Collection::stream)
                .filter(EntitySelector.NO_CREATIVE_OR_SPECTATOR)
                .filter(e -> isAttackable(entity, e))
                .findFirst()
                .ifPresentOrElse(
                        e -> entity.getBrain().setMemory(MemoryModuleType.NEAREST_ATTACKABLE, e),
                        () -> entity.getBrain().eraseMemory(MemoryModuleType.NEAREST_ATTACKABLE)
                );
    }

    private static boolean isAttackable(LivingEntity attacker, LivingEntity target) {
        return attacker.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, target)
                ? TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING.test(attacker, target)
                : TARGET_CONDITIONS.test(attacker, target);
    }

    @Override
    protected int radiusXZ() {
        return (int)EradicatorEntity.TARGETING_RANGE;
    }

    @Override
    protected int radiusY() {
        return (int)EradicatorEntity.TARGETING_RANGE;
    }
}
