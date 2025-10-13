package xyz.mrfrostydev.welcomeplayer.entities.projectiles;

import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.phys.*;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.damages.LaserDamageSource;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.ModDamageTypes;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ParticleRegistry;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

public class LaserBlastProjectile extends Projectile implements GeoEntity {
    public static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(
            true, true, Optional.empty(), BuiltInRegistries.BLOCK.getTag(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
    );
    public LaserBlastProjectile(EntityType<? extends LaserBlastProjectile> entityType, Level level) {
        super(EntityRegistry.LASER_BLAST_PROJECTILE.get(), level);
    }

    public LaserBlastProjectile(Level level, Entity owner, double x, double y, double z) {
        super(EntityRegistry.LASER_BLAST_PROJECTILE.get(), level);
        this.setOwner(owner);
        this.setPos(x, y, z);
    }

    @Override
    protected AABB makeBoundingBox() {
        float f = this.getType().getDimensions().width() / 2.0F;
        float f1 = this.getType().getDimensions().height();
        return new AABB(
                this.position().x - (double)f,
                this.position().y - 0.15F,
                this.position().z - (double)f,
                this.position().x + (double)f,
                this.position().y - 0.15F + (double)f1,
                this.position().z + (double)f
        );
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            LivingEntity livingentity = this.getOwner() instanceof LivingEntity livingentity1 ? livingentity1 : null;
            Entity entity = result.getEntity();
            if (livingentity != null) {
                livingentity.setLastHurtMob(entity);
            }
            DamageSource damagesource = new LaserDamageSource(this.level().registryAccess().holderOrThrow(ModDamageTypes.LASER), this.getOwner());
            if (entity.hurt(damagesource, 20.0F) && entity instanceof LivingEntity target) {
                EnchantmentHelper.doPostAttackEffects((ServerLevel)this.level(), target, damagesource);
            }

            this.explode(this.position());
        }
    }

    protected void explode(Vec3 pos) {
        this.level()
                .explode(
                        this.getOwner(),
                        new LaserDamageSource(this.level().registryAccess().holderOrThrow(ModDamageTypes.LASER), this.getOwner()),
                        EXPLOSION_DAMAGE_CALCULATOR,
                        pos.x(), pos.y(), pos.z(),
                        3.0F,
                        true,
                        Level.ExplosionInteraction.TRIGGER,
                        ParticleTypes.EXPLOSION,
                        ParticleTypes.GLOW,
                        SoundEvents.BREEZE_WIND_CHARGE_BURST
                );
        if(this.level() instanceof ServerLevel svlevel){
            svlevel.sendParticles(ParticleRegistry.LASER_BLAST.get(),
                    pos.x, pos.y + 0.3, pos.z,
                    1,
                    0, 0, 0,
                    0);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            Vec3i vec3i = result.getDirection().getNormal();
            Vec3 vec3 = Vec3.atLowerCornerOf(vec3i).multiply(0.25, 0.25, 0.25);
            Vec3 vec31 = result.getLocation().add(vec3);
            this.explode(vec31);
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Nullable
    protected ParticleOptions getTrailParticle() {
        return ParticleRegistry.LASER_TRAIL.get();
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
        if (!this.level().isClientSide && this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            this.explode(this.position());
            this.discard();
            return;
        }

        Entity entity = this.getOwner();
        if (this.level().isClientSide || (entity == null || !entity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
            super.tick();

            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity, ClipContext.Block.COLLIDER);
            if (hitresult.getType() != HitResult.Type.MISS && !net.neoforged.neoforge.event.EventHooks.onProjectileImpact(this, hitresult)) {
                this.hitTargetOrDeflectSelf(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;

            if (this.isInWater()) {
                for (int i = 0; i < 4; i++) {
                    this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25, d1 - vec3.y * 0.25, d2 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
                }
            }
            ParticleOptions particleoptions = this.getTrailParticle();
            int particleCount = 4;
            double particleOffset = 0.1;
            if (particleoptions != null) {
                for(int i=0; i<particleCount; i++){
                    this.level().addParticle(particleoptions,
                            (this.level().random.nextGaussian() * particleOffset) + d0,
                            (this.level().random.nextGaussian() * particleOffset) + d1 + 0.1,
                            (this.level().random.nextGaussian() * particleOffset) + d2,
                            0.0, 0.0, 0.0);
                }
            }

            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
            this.setXRot((float)(Mth.atan2(vec3.y, vec3.horizontalDistance()) * 180.0F / (float)Math.PI));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();

            this.setPos(d0, d1, d2);
        }
        else{
            this.discard();
        }
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

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return super.canCollideWith(entity);
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return super.canHitEntity(target);
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

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
