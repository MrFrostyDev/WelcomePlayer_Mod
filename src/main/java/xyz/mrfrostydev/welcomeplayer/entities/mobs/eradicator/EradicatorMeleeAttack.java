package xyz.mrfrostydev.welcomeplayer.entities.mobs.eradicator;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Mob;
import xyz.mrfrostydev.welcomeplayer.entities.EntityHitboxSet;
import xyz.mrfrostydev.welcomeplayer.entities.ai.MeleeAttackWithHitbox;
import xyz.mrfrostydev.welcomeplayer.registries.MemoryModuleRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.SoundEventRegistry;

public class EradicatorMeleeAttack extends MeleeAttackWithHitbox {
    public EradicatorMeleeAttack(int cooldownBetweenAttacks, EntityHitboxSet hitboxSet) {
        super(cooldownBetweenAttacks, hitboxSet);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel svlevel, Mob owner) {
        return owner instanceof EradicatorEntity
                && super.checkExtraStartConditions(svlevel, owner)
                && !owner.getBrain().hasMemoryValue(MemoryModuleRegistry.ERADICATOR_SHOOTING.get());
    }

    @Override
    protected void start(ServerLevel svlevel, Mob owner, long gameTime) {
        super.start(svlevel, owner, gameTime);
        owner.getBrain().setMemoryWithExpiry(MemoryModuleRegistry.ERADICATOR_SAWING.get(), Unit.INSTANCE, this.hitboxSet.getTime());
        owner.playSound(SoundEventRegistry.BUZZ_SAW.get(), 3.0F, 0.8F + svlevel.random.nextFloat() * 0.2F);
    }

    @Override
    protected void stop(ServerLevel svlevel, Mob owner, long gameTime) {
        super.stop(svlevel, owner, gameTime);
        owner.getBrain().eraseMemory(MemoryModuleRegistry.ERADICATOR_SAWING.get());
    }
}
