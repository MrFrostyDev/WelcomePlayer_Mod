package xyz.mrfrostydev.welcomeplayer.entities.mobs.handibot;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.Collection;
import java.util.Set;

public class HandibotNearestAttackableSensor extends NearestLivingEntitySensor<HandibotEntity> {
    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.NEAREST_LIVING_ENTITIES,
                MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
    }

    protected void doTick(ServerLevel svlevel, HandibotEntity entity) {
        super.doTick(svlevel, entity);
        entity.getBrain()
                .getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES)
                .stream()
                .flatMap(Collection::stream)
                .filter(EntitySelector.NO_CREATIVE_OR_SPECTATOR)
                .filter(e -> Sensor.isEntityAttackable(entity, e))
                .findFirst()
                .ifPresentOrElse(
                        e -> entity.getBrain().setMemory(MemoryModuleType.NEAREST_ATTACKABLE, e),
                        () -> entity.getBrain().eraseMemory(MemoryModuleType.NEAREST_ATTACKABLE)
                );
    }

    @Override
    protected int radiusXZ() {
        return (int)HandibotEntity.TARGETING_RANGE;
    }

    @Override
    protected int radiusY() {
        return (int)HandibotEntity.TARGETING_RANGE;
    }
}
