package xyz.mrfrostydev.welcomeplayer.entities.projectiles;

import net.minecraft.core.HolderSet;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.Nullable;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ParticleRegistry;

import java.util.Optional;

public class SeismicBlastProjectile extends Projectile {
    private static final float BLAST_COOLDOWN = 5;
    private static final int DURATION = 50;


    private int lifetime;

    public SeismicBlastProjectile(EntityType<? extends SeismicBlastProjectile> entityType, Level level) {
        super(EntityRegistry.SEISMIC_BLAST_PROJECTILE.get(), level);
        this.lifetime = DURATION;
    }

    public SeismicBlastProjectile(Level level, @Nullable Entity owner, double x, double y, double z) {
        super(EntityRegistry.SEISMIC_BLAST_PROJECTILE.get(), level);
        this.lifetime = DURATION;
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
            this.explode();
        }
    }

    protected void explode() {
        BlockState block = Blocks.COARSE_DIRT.defaultBlockState();
        this.level().explode(
                this.getOwner(),
                null,
                new SeismicExplosionDamagerCalculator(false, true, Optional.of(3.0F), Optional.empty()),
                this.getX(), this.getY(), this.getZ(),
                3.5F,
                false,
                Level.ExplosionInteraction.TRIGGER,
                new BlockParticleOption(ParticleTypes.BLOCK, block),
                new BlockParticleOption(ParticleTypes.BLOCK, block),
                SoundEvents.WIND_CHARGE_BURST
        );
        for(int i=0; i<40; i++){
            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, block),
                    this.getRandomX(3.0),
                    this.getRandomY() - 0.7,
                    this.getRandomZ(3.0),
                    0, 0, 0);
        }
        this.level().addParticle(ParticleRegistry.SEISMIC_BLAST.get(),
                this.getX(), this.getY() + 0.4, this.getZ(),
                0, 0, 0);
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide && this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            this.explode();
            this.discard();
            return;
        }

        lifetime--;
        if (lifetime <= 0) {
            this.explode();
            this.discard();
            return;
        }

        if (this.level().isClientSide || this.level().hasChunkAt(this.blockPosition())) {
            super.tick();

            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity, ClipContext.Block.COLLIDER);
            if (hitresult.getType() != HitResult.Type.MISS && !net.neoforged.neoforge.event.EventHooks.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;

            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
            this.setXRot((float)(Mth.atan2(vec3.y, vec3.horizontalDistance()) * 180.0F / (float)Math.PI));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();

            this.setPos(d0, d1, d2);

            if(lifetime % BLAST_COOLDOWN == 0){
                this.explode();
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
            this.explode();
            this.discard();
        }
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return false;
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
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
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
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.lifetime = tag.getInt("lifetime");
    }

    public static class SeismicExplosionDamagerCalculator extends SimpleExplosionDamageCalculator{
        public SeismicExplosionDamagerCalculator(boolean explodesBlocks, boolean damagesEntities, Optional<Float> knockbackMultiplier, Optional<HolderSet<Block>> immuneBlocks) {
            super(explodesBlocks, damagesEntities, knockbackMultiplier, immuneBlocks);
        }

        @Override
        public float getEntityDamageAmount(Explosion explosion, Entity entity) {
            float f = explosion.radius() * 2.0F;
            Vec3 vec3 = explosion.center();
            double distFromExplosion = Math.sqrt(entity.distanceToSqr(vec3)) / (double)f;
            double damage = (1.0 - distFromExplosion) * (double)Explosion.getSeenPercent(vec3, entity);
            return (float)((damage * damage + damage) / 2.0 * (double)f + 1.0);
        }
    }
}
