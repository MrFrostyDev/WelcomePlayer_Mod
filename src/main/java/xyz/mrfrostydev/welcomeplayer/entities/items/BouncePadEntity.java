package xyz.mrfrostydev.welcomeplayer.entities.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.SoundEventRegistry;

import java.util.List;
import java.util.function.Predicate;

public class BouncePadEntity extends Entity implements GeoEntity {
    public static final float BOUNCE_FORCE = 1.1F;

    protected static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(BouncePadEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(BouncePadEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(BouncePadEntity.class, EntityDataSerializers.FLOAT);

    public int delay = 10;

    private static final Predicate<Entity> canBouncePredicate = e -> {
        return e.isPushable() && !e.isNoGravity();
    };

    public BouncePadEntity(Level level) {
        super(EntityRegistry.BOUNCE_PAD.get(), level);
    }

    public BouncePadEntity(Level level, Player player) {
        super(EntityRegistry.BOUNCE_PAD.get(), level);

        float f = player.getXRot();
        float f1 = player.getYRot();
        float f2 = Mth.cos(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f4 = -Mth.cos(-f * (float) (Math.PI / 180.0));
        float f5 = Mth.sin(-f * (float) (Math.PI / 180.0));
        double d0 = player.getX() - (double)f3 * 0.3;
        double d1 = player.getEyeY();
        double d2 = player.getZ() - (double)f2 * 0.3;
        this.moveTo(d0, d1, d2, f1, f);
        Vec3 vec3 = new Vec3((double)(-f3), (double)Mth.clamp(-(f5 / f4), -5.0F, 5.0F), (double)(-f2));
        double d3 = vec3.length();
        vec3 = vec3.multiply(
                0.6 / d3 + this.random.triangle(0.5, 0.0103365), 0.6 / d3 + this.random.triangle(0.5, 0.0103365), 0.6 / d3 + this.random.triangle(0.5, 0.0103365)
        );
        this.setDeltaMovement(vec3.scale(0.5F));
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
        this.setXRot((float)(Mth.atan2(vec3.y, vec3.horizontalDistance()) * 180.0F / (float)Math.PI));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }


    public BouncePadEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        if (this.getHurtTime() > 0) this.setHurtTime(this.getHurtTime() - 1);
        if (this.getDamage() > 0.0F) this.setDamage(this.getDamage() - 1.0F);
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }

        if(delay <= 0){
            AABB area = new AABB(this.position(), this.position()).inflate(0.5);
            List<Entity> entities = this.level().getEntities((Entity)null, area, canBouncePredicate);
            for(Entity e : entities){
                this.bounce(e);
            }
            delay = 3;
        }
        else delay--;

        this.handlePortal();
        this.applyGravity();
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        this.checkInsideBlocks();
        super.tick();
    }

    public void bounce(Entity entity){
        boolean grounded = entity.onGround();
        Vec3 entityVel = entity.getDeltaMovement();

        double velX = entityVel.x * 0.9;
        double velY = grounded ? Math.min(BOUNCE_FORCE, entityVel.y / 2.0 + BOUNCE_FORCE) : BOUNCE_FORCE;
        double velZ = entityVel.z * 0.9;

        // deltaMovement is handled by the client, which is strange but oh well.
        // No server instance checking.
        entity.setDeltaMovement(velX, velY, velZ);

        this.level().playSound(null,
                this.getX(), this.getY(), this.getZ(),
                SoundEventRegistry.BOUNCE_PAD_JUMP, this.getSoundSource(),
                1.0F, Mth.randomBetween(this.level().random, 0.8F, 1.2F));
    };

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level().isClientSide || this.isRemoved()) return true;
        if (this.isInvulnerableTo(source)) return false;

        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.markHurt();
        this.setDamage(this.getDamage() + amount * 15.0F);
        this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
        boolean flag = source.getEntity() instanceof Player && ((Player)source.getEntity()).getAbilities().instabuild;
        if ((flag || !(this.getDamage() > 30.0F)) && !this.shouldSourceDestroy(source)) {
            if (flag) this.discard();
        }
        else this.destroy(source);
        return true;
    }

    boolean shouldSourceDestroy(DamageSource source) {
        return false;
    }

    public void destroy(Item dropItem) {
        this.kill();
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            ItemStack itemstack = new ItemStack(dropItem);
            itemstack.set(DataComponents.CUSTOM_NAME, this.getCustomName());
            this.spawnAtLocation(itemstack);
        }
    }

    protected void destroy(DamageSource source) {
        this.destroy(ItemRegistry.BOUNCE_PAD.get());
    }

    public void setHurtTime(int hurtTime) {
        this.entityData.set(DATA_ID_HURT, hurtTime);
    }

    public void setHurtDir(int hurtDir) {
        this.entityData.set(DATA_ID_HURTDIR, hurtDir);
    }

    public void setDamage(float damage) {
        this.entityData.set(DATA_ID_DAMAGE, damage);
    }

    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }

    public int getHurtTime() {
        return this.entityData.get(DATA_ID_HURT);
    }

    public int getHurtDir() {
        return this.entityData.get(DATA_ID_HURTDIR);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ID_HURT, 0);
        builder.define(DATA_ID_HURTDIR, 1);
        builder.define(DATA_ID_DAMAGE, 0.0F);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {}

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
