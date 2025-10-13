package xyz.mrfrostydev.welcomeplayer.entities.projectiles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.event.EventHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;

public class ShockBoltProjectile extends Projectile implements GeoEntity {
    private int lifetime;

    private static final EntityDataAccessor<Integer> DATA_LENGTH = SynchedEntityData.defineId(
            ShockBoltProjectile.class, EntityDataSerializers.INT
    );

    public ShockBoltProjectile(EntityType<? extends ShockBoltProjectile> entityType, Level level) {
        super(EntityRegistry.SHOCK_BOLT_PROJECTILE.get(), level);
    }

    public ShockBoltProjectile(Level level, Vec3 originPos, Vec3 targetPos) {
        super(EntityRegistry.SHOCK_BOLT_PROJECTILE.get(), level);
        this.lifetime = 4;
        double targetVecX = targetPos.x - originPos.x;
        double targetVecY = targetPos.y - originPos.y;
        double targetVecZ = targetPos.z - originPos.z;

        this.setPos(originPos.x + targetVecX / 2, originPos.y + targetVecY / 2, originPos.z + targetVecZ / 2);
        this.setDeltaMovement(new Vec3(targetVecX, targetVecY, targetVecZ).normalize().scale(1.001));

        double dist = originPos.distanceTo(targetPos);
        int length = 0;
        if(dist > 2.5) length = 3;
        else if(dist < 1.5) length = 2;
        this.entityData.set(DATA_LENGTH, length);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize() * 4.0;
        if (Double.isNaN(d0)) d0 = 4.0;
        d0 *= 64.0;
        return distance < d0 * d0;
    }

    @Override
    public void tick() {
        if(lifetime > 0){
            lifetime--;
            if (lifetime <= 0) {
                this.discard();
                return;
            }
        }

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity, ClipContext.Block.COLLIDER);
        if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
            this.hitTargetOrDeflectSelf(hitresult);
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();

        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
        this.setXRot((float)(Mth.atan2(vec3.y, vec3.horizontalDistance()) * 180.0F / (float)Math.PI));
        this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
        this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
        super.tick();
    }

    protected static float lerpRotation(float currentRotation, float targetRotation) {
        while (targetRotation - currentRotation < -180.0F) {
            currentRotation -= 360.0F;
        }

        while (targetRotation - currentRotation >= 180.0F) {
            currentRotation += 360.0F;
        }

        return Mth.lerp(0.9F, currentRotation, targetRotation);
    }

    public int getLength() {
        return this.entityData.get(DATA_LENGTH);
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_LENGTH, 0);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity p_entity) {
        Entity entity = this.getOwner();
        int i = entity == null ? 0 : entity.getId();
        Vec3 vec3 = p_entity.getPositionBase();
        return new ClientboundAddEntityPacket(
                this.getId(),
                this.getUUID(),
                vec3.x(),
                vec3.y(),
                vec3.z(),
                p_entity.getLastSentXRot(),
                p_entity.getLastSentYRot(),
                this.getType(),
                i,
                p_entity.getLastSentMovement(),
                0.0
        );
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        Vec3 vec3 = new Vec3(packet.getXa(), packet.getYa(), packet.getZa());
        this.setDeltaMovement(vec3);
    }

    // |------------------------------------------------------------|
    // |-------------------------Animations-------------------------|
    // |------------------------------------------------------------|

    private final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animCache;
    }
}
