package xyz.mrfrostydev.welcomeplayer.entities.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class ChangeTargetIfCloser {
    public static <E extends Mob> BehaviorControl<E> create(float chance, int tickRate) {
        return BehaviorBuilder.create(
                inst -> inst.group(inst.present(MemoryModuleType.ATTACK_TARGET), inst.present(MemoryModuleType.NEAREST_ATTACKABLE))
                        .apply(
                                inst,
                                (attackTarget, nearestAttackable) -> (svlevel, owner, gameTime) -> {
                                    LivingEntity nearest = inst.get(nearestAttackable);
                                    LivingEntity target = inst.get(attackTarget);

                                    if(!nearest.is(target)
                                            && Math.random() <= chance
                                            && svlevel.getServer().getTickCount() % tickRate == 0){
                                        attackTarget.set(nearest);
                                    }
                                    return true;
                                }
                        )
        );
    }
}
