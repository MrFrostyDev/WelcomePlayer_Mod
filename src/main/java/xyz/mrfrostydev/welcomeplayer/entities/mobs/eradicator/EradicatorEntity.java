package xyz.mrfrostydev.welcomeplayer.entities.mobs.eradicator;

import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.damages.SawDamageSource;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.ModDamageTypes;
import xyz.mrfrostydev.welcomeplayer.entities.EntityHitboxSet;
import xyz.mrfrostydev.welcomeplayer.entities.ai.navigation.HoverPathNavigation;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.TagRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class EradicatorEntity extends Monster implements GeoEntity {
    public static final double TARGETING_RANGE = 32;
    public static final double SAW_ATTACK_RANGE = 2;
    public static final int SAW_ATTACK_COOLDOWN = 40;

    public EradicatorEntity(EntityType<EradicatorEntity> entityType, Level level) {
        super(EntityRegistry.ERADICATOR.get(), level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.setPathfindingMalus(PathType.LAVA, 0.0F);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new HoverPathNavigation(this, level);
    }

    @Override
    public void tick() {
        super.tick();
        if(isPassenger()) removeVehicle();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 250.0F)
                .add(Attributes.STEP_HEIGHT, 1)
                .add(Attributes.FOLLOW_RANGE, TARGETING_RANGE)
                .add(Attributes.MOVEMENT_SPEED, 0.35F)
                .add(Attributes.ATTACK_DAMAGE, 32.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.9)
                .add(Attributes.ARMOR, 25.0)
                .add(Attributes.ARMOR_TOUGHNESS, 5.0);
    }

    // |------------------------------------------------------------|
    // |----------------------------AI------------------------------|
    // |------------------------------------------------------------|

    @Override
    protected Brain.Provider<EradicatorEntity> brainProvider() {
        return Brain.provider(EradicatorAI.MEMORY_TYPES, EradicatorAI.SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        this.hitboxSets = registerHitboxSets();
        return EradicatorAI.makeBrain(this, this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<EradicatorEntity> getBrain() {
        return (Brain<EradicatorEntity>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        this.getBrain().tick((ServerLevel)this.level(), this);
        EradicatorAI.updateActivity(this);
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
        return !type.is(TagRegistry.HOST_ROBOT);
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity entity) {
        return this.getBoundingBox().inflate(SAW_ATTACK_RANGE).intersects(entity.getHitbox());
    }

    // |------------------------------------------------------------|
    // |-------------------------Hitboxes---------------------------|
    // |------------------------------------------------------------|
    private List<EntityHitboxSet> hitboxSets;

    public List<EntityHitboxSet> registerHitboxSets(){
        EntityHitboxSet attackHitbox = EntityHitboxSet.Builder.create(true)
                .add(EntityHitboxSet.SequenceSetBuilder
                        .create(2.5, 0.5, 2.5, EradicatorEntity::onHit)
                        .transition(SAW_ATTACK_RANGE + 0.5, 1.5, 0.1, 15)
                        .transition(SAW_ATTACK_RANGE + 0.5, 1.5, 0.1, 20)
                        .transition(SAW_ATTACK_RANGE + 0.5, 1.5, 0.1, 25)
                        .transition(SAW_ATTACK_RANGE + 0.5, 1.5, 0.1, 30)
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
            if(target.is(livingEntity)) continue;
            target.hurt(
                    new SawDamageSource(serverLevel.registryAccess().holderOrThrow(ModDamageTypes.SAW), livingEntity),
                    (float)livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE) / 4
            );
        }
    }

    // |------------------------------------------------------------|
    // |-------------------------Animations-------------------------|
    // |------------------------------------------------------------|

    private final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("eradicator.animation.idle");
    private static final RawAnimation MOVE_ANIM = RawAnimation.begin().thenPlay("eradicator.animation.move");
    private static final RawAnimation CANNON_ATTACK_ANIM = RawAnimation.begin().thenPlay("eradicator.animation.cannon_attack");
    private static final RawAnimation SAW_ATTACK_ANIM = RawAnimation.begin().thenPlay("eradicator.animation.saw_attack");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::handleAnimations));
    }

    private <T extends EradicatorEntity> PlayState handleAnimations(AnimationState<T> state) {
        EradicatorEntity entity = state.getAnimatable();
        if(entity.swinging){
            state.setAnimation(SAW_ATTACK_ANIM);
        }
        else if(entity.getPose() == Pose.SHOOTING){
            state.setAnimation(CANNON_ATTACK_ANIM);
        }
        else if(state.isMoving() || entity.getPose() == Pose.SLIDING) {
            state.setAnimation(MOVE_ANIM);
        }
        else {
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

    @Override
    public int getCurrentSwingDuration() {
        return 40;
    }

    @Override
    public boolean canStandOnFluid(FluidState fluidState) {
        return fluidState.is(FluidTags.LAVA) || fluidState.is(FluidTags.WATER);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }


}
