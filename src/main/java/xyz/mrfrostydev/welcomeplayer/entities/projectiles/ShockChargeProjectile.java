package xyz.mrfrostydev.welcomeplayer.entities.projectiles;

import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.damages.LaserDamageSource;
import xyz.mrfrostydev.welcomeplayer.damages.ShockDamageSource;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.ModDamageTypes;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ParticleRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.SoundEventRegistry;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ShockChargeProjectile extends Projectile implements GeoEntity {
    public static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(
            false, true, Optional.empty(), BuiltInRegistries.BLOCK.getTag(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
    );

    private static final float SHOCK_RANGE = 3.0F;
    private static final float SHOCK_DAMAGE = 5;
    private static final float TICK_COOLDOWN = 10;

    private int lifetime;
    private boolean isUnstable;

    public ShockChargeProjectile(EntityType<? extends ShockChargeProjectile> entityType, Level level) {
        super(EntityRegistry.SHOCK_CHARGE_PROJECTILE.get(), level);
        this.lifetime = 400;
    }

    public ShockChargeProjectile(Level level, @Nullable Entity owner, double x, double y, double z, int lifetime, boolean isUnstable) {
        super(EntityRegistry.SHOCK_CHARGE_PROJECTILE.get(), level);
        this.lifetime = lifetime;
        this.isUnstable = isUnstable;
        this.setOwner(owner);
        this.setPos(x, y, z);
    }

    public ShockChargeProjectile(Level level, @Nullable Entity owner, double x, double y, double z, boolean isUnstable) {
        super(EntityRegistry.SHOCK_CHARGE_PROJECTILE.get(), level);
        this.lifetime = 400;
        this.isUnstable = isUnstable;
        this.setOwner(owner);
        this.setPos(x, y, z);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        this.setDeltaMovement(new Vec3(x, y, z).normalize().scale(velocity));
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
            DamageSource damagesource = new ShockDamageSource(this.level().registryAccess().holderOrThrow(ModDamageTypes.SHOCK), this.getOwner());
            if (entity.hurt(damagesource, 12.0F) && entity instanceof LivingEntity target) {
                EnchantmentHelper.doPostAttackEffects((ServerLevel)this.level(), target, damagesource);
            }

            this.explode(this.position());
        }
    }

    protected void explode(Vec3 pos) {
        this.level()
                .explode(
                        this.getOwner(),
                        new LaserDamageSource(this.level().registryAccess().holderOrThrow(ModDamageTypes.SHOCK), this.getOwner()),
                        EXPLOSION_DAMAGE_CALCULATOR,
                        pos.x(), pos.y(), pos.z(),
                        2.5F,
                        false,
                        Level.ExplosionInteraction.TRIGGER,
                        ParticleTypes.EXPLOSION,
                        ParticleTypes.GLOW,
                        SoundEvents.BREEZE_WIND_CHARGE_BURST
                );
        if(this.level() instanceof ServerLevel svlevel){
            svlevel.sendParticles(ParticleRegistry.SHOCK_BLAST.get(),
                    pos.x, pos.y + 0.3, pos.z,
                    1,
                    0, 0, 0,
                    0);
        }
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide && this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            this.explode(this.position());
            this.discard();
            return;
        }

        lifetime--;
        if (lifetime <= 0) {
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

            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
            this.setXRot((float)(Mth.atan2(vec3.y, vec3.horizontalDistance()) * 180.0F / (float)Math.PI));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();

            this.setPos(d0, d1, d2);

            if(lifetime % TICK_COOLDOWN == 0){
                double x = this.position().x;
                double y = this.position().y;
                double z = this.position().z;

                AABB area = new AABB(x, y, z, x, y, z).inflate(SHOCK_RANGE);
                List<Entity> entities = this.level().getEntities(this.getOwner(), area,
                        isUnstable
                                ? e -> !e.isSpectator()
                                : e -> !e.getType().equals(EntityType.PLAYER)
                                && (!(e instanceof TamableAnimal) || ((TamableAnimal) e).getOwner() == null)
                                && !e.isSpectator());

                for(Entity e : entities){
                    if(!(e instanceof LivingEntity target))continue;
                    Vec3 shootPos = new Vec3(x, y, z);
                    ShockBoltProjectile shockBolt = new ShockBoltProjectile(this.level(), this.getOwner(), shootPos, new Vec3(e.getX(), e.getEyeY() - 0.2, e.getZ()));
                    this.level().addFreshEntity(shockBolt);

                    target.hurt(new ShockDamageSource(this.level().registryAccess().holderOrThrow(ModDamageTypes.SHOCK), this.getOwner()), SHOCK_DAMAGE);

                    if(!this.level().isClientSide){
                        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(this.level());
                        lightning.setDamage(0.0F);
                        target.thunderHit((ServerLevel) this.level(), lightning);
                    }
                }
            }

            if(lifetime % 2 == 0){
                this.level().addParticle(ParticleRegistry.ORB_TRAIL.get(),
                        d0, d1, d2,
                        0.0, 0.0, 0.0);
            }

            if(lifetime % 16 == 0){
                this.playSound(SoundEventRegistry.SHOCK_HUM.get(),
                        0.8F, 1.0F);
            }
        }
        else{
            this.discard();
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

    @Override
    protected boolean canHitEntity(Entity target) {
        return this.getOwner() == null || !this.getOwner().is(target);
    }

    @Override
    protected AABB makeBoundingBox() {
        float f = this.getType().getDimensions().width() / 2.0F;
        float f1 = this.getType().getDimensions().height();
        float f2 = 0.15F;
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
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize() * 4.0;
        if (Double.isNaN(d0)) d0 = 4.0;
        d0 *= 64.0;
        return distance < d0 * d0;
    }

    @Override
    public boolean displayFireAnimation() {
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

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("lifetime", this.lifetime);
        tag.putBoolean("isUnstable", this.isUnstable);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.lifetime = tag.getInt("lifetime");
        this.isUnstable = tag.getBoolean("isUnstable");
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
