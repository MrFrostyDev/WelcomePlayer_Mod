package xyz.mrfrostydev.welcomeplayer.items.gadgets;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.client.renderers.item.StasisStickItemRenderer;
import xyz.mrfrostydev.welcomeplayer.registries.DataComponentRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.MobEffectRegistry;

import java.util.function.Consumer;

public class StasisStickItem extends Item implements GeoItem {
    private static final int COOLDOWN = 200;

    public StasisStickItem(Properties properties) {
        super(properties);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public static ItemAttributeModifiers createAttributes(float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(entity instanceof Player player){
            if(!stack.getOrDefault(DataComponentRegistry.CHARGED, false)){
                if(!player.getCooldowns().isOnCooldown(this)){
                    stack.set(DataComponentRegistry.CHARGED, true);
                }
            }
            else if(stack.getOrDefault(DataComponentRegistry.CHARGED, false)){
                if(player.getCooldowns().isOnCooldown(this)){
                    stack.set(DataComponentRegistry.CHARGED, false);
                }
            }
        }

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity target){
            if(!player.getCooldowns().isOnCooldown(this)){
                player.getCooldowns().addCooldown(this, COOLDOWN);
                target.addEffect(new MobEffectInstance(MobEffectRegistry.STASIS, 80, 0));
            }
        }
        return false;
    }

    // |------------------------------------------------------------|
    // |-------------------------Animations-------------------------|
    // |------------------------------------------------------------|

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation ACTIVE_ANIM = RawAnimation.begin().thenPlay("stasis_stick.animation.active");
    private static final RawAnimation DEACTIVE_ANIM = RawAnimation.begin().thenPlay("stasis_stick.animation.deactive");

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private StasisStickItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new StasisStickItemRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::handleAnimation));
    }

    private <T extends GeoItem> PlayState handleAnimation(AnimationState<T> state) {
        ItemStack stack = state.getData(DataTickets.ITEMSTACK);
        if(stack == null) return PlayState.STOP;

        if(stack.getOrDefault(DataComponentRegistry.CHARGED, false)){
            return state.setAndContinue(ACTIVE_ANIM);
        }
        else{
            return state.setAndContinue(DEACTIVE_ANIM);
        }

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
