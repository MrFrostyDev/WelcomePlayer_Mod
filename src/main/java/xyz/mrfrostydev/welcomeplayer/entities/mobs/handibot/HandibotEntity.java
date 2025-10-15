package xyz.mrfrostydev.welcomeplayer.entities.mobs.handibot;

import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.entities.EntityHitboxSet;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class HandibotEntity extends Monster implements GeoEntity {
    public static final double TARGETING_RANGE = 20;
    public static final double ATTACK_RANGE = 2;
    public static final int ATTACK_COOLDOWN = 40;

    public HandibotEntity(EntityType<HandibotEntity> entityType, Level level) {
        super(EntityRegistry.HANDIBOT.get(), level);
    }

    @Override
    public void tick() {
        super.tick();
        if(isPassenger()) removeVehicle();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 18)
                .add(Attributes.STEP_HEIGHT, 1)
                .add(Attributes.FOLLOW_RANGE, TARGETING_RANGE)
                .add(Attributes.MOVEMENT_SPEED, 0.32F)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.ARMOR, 15.0)
                .add(Attributes.ARMOR_TOUGHNESS, 3.0);
    }

    // |------------------------------------------------------------|
    // |----------------------------AI------------------------------|
    // |------------------------------------------------------------|

    @Override
    protected Brain.Provider<HandibotEntity> brainProvider() {
        return Brain.provider(HandibotAI.MEMORY_TYPES, HandibotAI.SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        this.hitboxSets = registerHitboxSets();
        return HandibotAI.makeBrain(this, this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<HandibotEntity> getBrain() {
        return (Brain<HandibotEntity>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        this.getBrain().tick((ServerLevel)this.level(), this);
        HandibotAI.updateActivity(this);
        super.customServerAiStep();
    }

    public Optional<LivingEntity> getHurtBy() {
        return this.getBrain()
                .getMemory(MemoryModuleType.HURT_BY)
                .map(DamageSource::getEntity)
                .filter(e -> e instanceof LivingEntity)
                .map(e -> (LivingEntity)e);
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.getTargetFromBrain();
    }

    @Override
    public boolean canAttackType(EntityType<?> type) {
        return type == EntityType.PLAYER || type == EntityType.IRON_GOLEM;
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity entity) {
        return this.getBoundingBox().inflate(ATTACK_RANGE).intersects(entity.getHitbox());
    }

    // |------------------------------------------------------------|
    // |-------------------------Hitboxes---------------------------|
    // |------------------------------------------------------------|
    private List<EntityHitboxSet> hitboxSets;

    public List<EntityHitboxSet> registerHitboxSets(){
        EntityHitboxSet attackHitbox = EntityHitboxSet.Builder.create()
                .add(EntityHitboxSet.SequenceSetBuilder
                        .create(0.6, 0.6, 0.6, HandibotEntity::onHit)
                        .transition(ATTACK_RANGE, 1.2, 0, 9)
                )
                .build(this);

        return List.of(attackHitbox);
    }

    public List<EntityHitboxSet> getHitboxSets() {
        return hitboxSets;
    }

    private static <T extends Entity> void onHit(ServerLevel serverLevel, List<Entity> entities, T inflictor) {
        if(!(inflictor instanceof LivingEntity livingEntity)) return;
        for(Entity target : entities){
            if(target.is(inflictor)) continue;
            target.hurt(
                    new DamageSource(serverLevel.registryAccess().holderOrThrow(DamageTypes.MOB_ATTACK), inflictor, inflictor),
                    (float)livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE)
            );
        }
    }

    // |------------------------------------------------------------|
    // |-------------------------Animations-------------------------|
    // |------------------------------------------------------------|

    private final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("handibot.animation.idle");
    private static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenPlay("handibot.animation.attack");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::handleAnimations));
    }

    private <T extends HandibotEntity> PlayState handleAnimations(AnimationState<T> state) {
        HandibotEntity entity = state.getAnimatable();
        if(entity.swinging){
            state.setAnimation(ATTACK_ANIM);
        }
        else if(state.getController().getAnimationState() == AnimationController.State.PAUSED
                || state.getController().getAnimationState() == AnimationController.State.STOPPED){
            state.setAnimation(IDLE_ANIM);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animCache;
    }

    // |------------------------------------------------------------|
    // |----------------------Data Handling-------------------------|
    // |------------------------------------------------------------|

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        ListTag hitboxesList = new ListTag();
        for(EntityHitboxSet set : hitboxSets){
            hitboxesList.add(set.saveData());
        }
        tag.put("hitboxSets", hitboxesList);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        ListTag hitboxesList = tag.getList("hitboxSets", tag.getTagType("hitboxSets"));
        for(int i=0; i<hitboxSets.size(); i++){
            EntityHitboxSet set = hitboxSets.get(i);
            set.loadData(hitboxesList.getCompound(i));
        }
    }

    // |------------------------------------------------------------|
    // |----------------------------Misc----------------------------|
    // |------------------------------------------------------------|

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    public int getMaxHeadYRot() {
        return 30;
    }

    @Override
    public int getHeadRotSpeed() {
        return 25;
    }
}
