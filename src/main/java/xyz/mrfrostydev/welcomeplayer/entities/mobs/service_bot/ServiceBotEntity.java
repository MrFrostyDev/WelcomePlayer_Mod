package xyz.mrfrostydev.welcomeplayer.entities.mobs.service_bot;

import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.entities.ai.navigation.HoverPathNavigation;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;

import java.util.Optional;

public class ServiceBotEntity extends PathfinderMob implements GeoEntity {
    public static final double TARGETING_RANGE = 20;

    public ServiceBotEntity(EntityType<ServiceBotEntity> entityType, Level level) {
        super(EntityRegistry.SERVICE_BOT.get(), level);
    }

    @Override
    public void tick() {
        super.tick();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.STEP_HEIGHT, 1)
                .add(Attributes.FOLLOW_RANGE, TARGETING_RANGE)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 1.0)
                .add(Attributes.ARMOR, 10.0)
                .add(Attributes.ARMOR_TOUGHNESS, 1.0);
    }

    // |------------------------------------------------------------|
    // |----------------------------AI------------------------------|
    // |------------------------------------------------------------|

    @Override
    protected Brain.Provider<ServiceBotEntity> brainProvider() {
        return Brain.provider(ServiceBotAI.MEMORY_TYPES, ServiceBotAI.SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return ServiceBotAI.makeBrain(this, this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<ServiceBotEntity> getBrain() {
        return (Brain<ServiceBotEntity>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        this.getBrain().tick((ServerLevel)this.level(), this);
        ServiceBotAI.updateActivity(this);
        super.customServerAiStep();
    }

    public Optional<LivingEntity> getHurtBy() {
        return this.getBrain()
                .getMemory(MemoryModuleType.HURT_BY)
                .map(DamageSource::getEntity)
                .filter(e -> e instanceof LivingEntity)
                .map(e -> (LivingEntity)e);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new HoverPathNavigation(this, level);
    }

    // |------------------------------------------------------------|
    // |-------------------------Animations-------------------------|
    // |------------------------------------------------------------|

    private final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("service_bot.animation.idle");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::handleAnimations));
    }

    private <T extends ServiceBotEntity> PlayState handleAnimations(AnimationState<T> state) {
        if(state.getController().getAnimationState() == AnimationController.State.PAUSED
                || state.getController().getAnimationState() == AnimationController.State.STOPPED){
            if(this.level().random.nextInt(200) == 0){
                state.resetCurrentAnimation();
                state.setAnimation(IDLE_ANIM);
            }
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

    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
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
