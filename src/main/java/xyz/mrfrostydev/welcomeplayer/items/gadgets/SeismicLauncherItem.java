package xyz.mrfrostydev.welcomeplayer.items.gadgets;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.client.renderers.item.SeismicLauncherItemRenderer;
import xyz.mrfrostydev.welcomeplayer.entities.projectiles.SeismicBlastProjectile;
import xyz.mrfrostydev.welcomeplayer.registries.DataComponentRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ParticleRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.SoundEventRegistry;

import java.util.function.Consumer;

@EventBusSubscriber
public class SeismicLauncherItem extends Item implements GeoItem {
    private static final int COOLDOWN = 100;
    private static final int CHARGE_TIME = 25;

    public SeismicLauncherItem(Properties properties) {
        super(properties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if(!player.isUsingItem()){
            player.startUsingItem(hand);
            stack.set(DataComponentRegistry.CHARGING, true);
            triggerAnim(player, GeoItem.getId(stack), "General", "charge");
        }

        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        stack.set(DataComponentRegistry.CHARGING, false);
        if(timeCharged <= 0){
            Vec3 lookPos = livingEntity.getEyePosition();
            Vec3 lookVec = livingEntity.getLookAngle().normalize().scale(1.3);
            Vec3 shootPos = lookPos.add(lookVec);

            SeismicBlastProjectile blast = new SeismicBlastProjectile(level, livingEntity,
                    shootPos.x, shootPos.y, shootPos.z);
            blast.shoot(lookVec.x, lookVec.y, lookVec.z, 0.5F, 0);
            level.addFreshEntity(blast);

            livingEntity.setDeltaMovement(lookVec.normalize().scale(-1.2));

            if(livingEntity instanceof Player player){
                player.getCooldowns().addCooldown(this, COOLDOWN);
                triggerAnim(player, GeoItem.getId(stack), "General", "fire");
            }

            level.playSound(null,
                    livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                    SoundEventRegistry.SEISMIC_SLAM,
                    livingEntity.getSoundSource(),
                    2.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if(remainingUseDuration == CHARGE_TIME - 1){
            level.playSound(null,
                    livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                    SoundEventRegistry.SEISMIC_CHARGE,
                    livingEntity.getSoundSource(),
                    0.8F, 1.0F);
        }
        if(remainingUseDuration % 10 == 0){
            Vec3 vec3 = livingEntity.getUpVector(1.0F);
            float angle = livingEntity.getXRot();
            Vec3 vec31 = livingEntity.getViewVector(1.0F);
            Quaternionf quaternionfoffset = new Quaternionf().setAngleAxis(((angle - 11.0F) * (float) (Math.PI / 180.0)), vec3.x, vec3.y, vec3.z);
            Vector3f vector3foffset = vec31.toVector3f().rotate(quaternionfoffset);

            level.addParticle(ParticleRegistry.SHOCK_CHARGE.get(),
                    livingEntity.getX() + vector3foffset.x, livingEntity.getEyeY() - 0.3F + vector3foffset.y, livingEntity.getZ() + vector3foffset.z,
                    1, 0, 0);
        }
    }

    @SubscribeEvent
    public static void onUsingWhileCharging(LivingEntityUseItemEvent.Tick event){
        if(event.getItem().is(ItemRegistry.SEISMIC_LAUNCHER) && event.getDuration() < 5){
            LivingEntity livingEntity = event.getEntity();
            if(livingEntity.level() instanceof ServerLevel svlevel && svlevel.getServer().getTickCount() % 12 == 0){
                svlevel.playSound(null,
                        livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ(),
                        SoundEventRegistry.SEISMIC_CHARGE_HOLD,
                        livingEntity.getSoundSource(),
                        0.6F, 1.0F);
            }
        }
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        stack.set(DataComponentRegistry.CHARGING, false);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return CHARGE_TIME;
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        return true;
    }

    // |------------------------------------------------------------|
    // |-------------------------Animations-------------------------|
    // |------------------------------------------------------------|

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation CHARGE_ANIM = RawAnimation.begin().thenPlay("seismic_launcher.animation.charge");
    private static final RawAnimation CHARGE_HOLD_ANIM = RawAnimation.begin().thenPlay("seismic_launcher.animation.charge_hold");
    private static final RawAnimation FIRE_ANIM = RawAnimation.begin().thenPlay("seismic_launcher.animation.fire");
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("seismic_launcher.animation.idle");

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private SeismicLauncherItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new SeismicLauncherItemRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "General", this::handleAnimation)
                .triggerableAnim("fire", FIRE_ANIM).triggerableAnim("charge", CHARGE_ANIM).receiveTriggeredAnimations());}

    private <T extends GeoItem> PlayState handleAnimation(AnimationState<T> state) {
        ItemStack stack = state.getData(DataTickets.ITEMSTACK);
        if(stack == null) return PlayState.STOP;

        if(stack.getOrDefault(DataComponentRegistry.CHARGING, false)) {
            if (state.isCurrentAnimation(CHARGE_ANIM) && state.getController().hasAnimationFinished()) {
                return state.setAndContinue(CHARGE_HOLD_ANIM);
            }
        }
        else if(!state.isCurrentAnimation(FIRE_ANIM) || state.getController().hasAnimationFinished()){
            return state.setAndContinue(IDLE_ANIM);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
