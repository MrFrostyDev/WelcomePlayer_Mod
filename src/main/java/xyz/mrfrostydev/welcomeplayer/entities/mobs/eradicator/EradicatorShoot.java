package xyz.mrfrostydev.welcomeplayer.entities.mobs.eradicator;


import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import xyz.mrfrostydev.welcomeplayer.entities.projectiles.LaserBlastProjectile;
import xyz.mrfrostydev.welcomeplayer.registries.MemoryModuleRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ParticleRegistry;

public class EradicatorShoot extends Behavior<EradicatorEntity> {
    private static final int SHOOT_CHARGE_TIME = 20;
    private static final int SHOOT_FIRE_TIME = 5;
    private static final int SHOOT_RECOVER_TIME = 25;
    private static final int SHOOT_COOLDOWN = 120;

    public static final int SHOOT_DURATION = SHOOT_CHARGE_TIME + SHOOT_FIRE_TIME + SHOOT_RECOVER_TIME;

    public EradicatorShoot() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModuleRegistry.ERADICATOR_SAWING.get(),
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleRegistry.ERADICATOR_SHOOT_COOLDOWN.get(),
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleRegistry.ERADICATOR_SHOOT_CHARGING.get(),
                        MemoryStatus.REGISTERED,
                        MemoryModuleRegistry.ERADICATOR_SHOOTING.get(),
                        MemoryStatus.REGISTERED,
                        MemoryModuleRegistry.ERADICATOR_SHOOT_RECOVERING.get(),
                        MemoryStatus.REGISTERED
                ),
                SHOOT_DURATION
        );
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel svlevel, EradicatorEntity owner) {
        return owner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).map(target -> {
            if(isTargetWithinRange(owner, target)) return true;
            else{
                owner.getBrain().eraseMemory(MemoryModuleRegistry.ERADICATOR_SHOOTING.get());
                return false;
            }
        }).orElse(false);
    }

    @Override
    protected void start(ServerLevel svlevel, EradicatorEntity entity, long gameTime) {
        entity.getBrain().setMemoryWithExpiry(MemoryModuleRegistry.ERADICATOR_SHOOT_CHARGING.get(), Unit.INSTANCE, SHOOT_CHARGE_TIME);
        entity.getBrain().setMemoryWithExpiry(MemoryModuleRegistry.ERADICATOR_SHOOTING.get(), Unit.INSTANCE, SHOOT_DURATION);
        entity.setPose(Pose.SHOOTING);
        entity.playSound(SoundEvents.BREEZE_INHALE, 1.0F, 1.0F);

        LivingEntity target = entity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        if(target != null){
            entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
            Vec3 shootPos = getShootPos(entity);
            svlevel.sendParticles(ParticleRegistry.LASER_CHARGE.get(),
                    shootPos.x,  shootPos.y, shootPos.z,
                    1, 0, 0, 0, 0);
        }
    }

    @Override
    protected boolean canStillUse(ServerLevel svlevel, EradicatorEntity entity, long gameTime) {
        return entity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && entity.getBrain().hasMemoryValue(MemoryModuleRegistry.ERADICATOR_SHOOTING.get());
    }

    @Override
    protected void tick(ServerLevel svlevel, EradicatorEntity owner, long gameTime) {
        Brain<EradicatorEntity> brain = owner.getBrain();
        LivingEntity target = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        if (target != null) {
            owner.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
            if (brain.getMemory(MemoryModuleRegistry.ERADICATOR_SHOOT_CHARGING.get()).isEmpty() && brain.getMemory(MemoryModuleRegistry.ERADICATOR_SHOOT_RECOVERING.get()).isEmpty()) {
                brain.setMemoryWithExpiry(MemoryModuleRegistry.ERADICATOR_SHOOT_RECOVERING.get(), Unit.INSTANCE, SHOOT_RECOVER_TIME + SHOOT_FIRE_TIME);
                if (isFacingTarget(owner, target)) {

                    Vec3 shootPos = getShootPos(owner);
                    double targetVecX = target.getX() - (shootPos.x);
                    double targetVecY = target.getY(target.isPassenger() ? 0.8 : 0.3) - (shootPos.y);
                    double targetVecZ = target.getZ() - (shootPos.z);

                    LaserBlastProjectile laserBlastProjectile = new LaserBlastProjectile(svlevel, owner, shootPos.x, shootPos.y, shootPos.z);
                    laserBlastProjectile.setDeltaMovement(new Vec3(targetVecX, targetVecY, targetVecZ).normalize().scale(1.5));
                    svlevel.addFreshEntity(laserBlastProjectile);
                }
            }
        }
    }

    @Override
    protected void stop(ServerLevel svlevel, EradicatorEntity entity, long gameTime) {
        entity.getBrain().setMemoryWithExpiry(MemoryModuleRegistry.ERADICATOR_SHOOT_COOLDOWN.get(), Unit.INSTANCE, SHOOT_COOLDOWN);
        entity.getBrain().eraseMemory(MemoryModuleRegistry.ERADICATOR_SHOOT_CHARGING.get());
        entity.getBrain().eraseMemory(MemoryModuleRegistry.ERADICATOR_SHOOTING.get());
        entity.getBrain().eraseMemory(MemoryModuleRegistry.ERADICATOR_SHOOT_RECOVERING.get());
        entity.setPose(Pose.STANDING);
    }

    public static boolean isFacingTarget(EradicatorEntity entity, LivingEntity target) {
        Vec3 vec3 = entity.getViewVector(1.0F);
        Vec3 vec31 = target.position().subtract(entity.position()).normalize();
        return vec3.dot(vec31) > 0.5;
    }

    public static Vec3 getShootPos(EradicatorEntity entity){
        Vec3 forwardPos = entity.calculateViewVector(0, entity.getYRot()).normalize().scale(2.8);
        Vec3 shootPerpendicularOffset = new Vec3(-forwardPos.z, forwardPos.y, forwardPos.x).scale(-0.2);
        Vec3 shootVec = forwardPos.add(shootPerpendicularOffset);
        return new Vec3(entity.getX() + shootVec.x, entity.getEyeY() - 0.4 + shootVec.y, entity.getZ() + shootVec.z);

    }

    private static boolean isTargetWithinRange(EradicatorEntity entity, LivingEntity target) {
        double d0 = entity.position().distanceToSqr(target.position());
        return d0 > 4.0 && d0 < 512;
    }
}
