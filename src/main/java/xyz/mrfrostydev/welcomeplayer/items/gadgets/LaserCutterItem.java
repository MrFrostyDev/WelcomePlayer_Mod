package xyz.mrfrostydev.welcomeplayer.items.gadgets;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.client.renderers.item.LaserCutterItemRenderer;
import xyz.mrfrostydev.welcomeplayer.registries.DataComponentRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.SoundEventRegistry;

import java.util.function.Consumer;

public class LaserCutterItem extends Item implements GeoItem {
    public LaserCutterItem(Properties properties) {
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
        if(level instanceof ServerLevel){
            int tickCount = level.getServer().getTickCount();
            if(stack.getOrDefault(DataComponentRegistry.CHARGING, false) && tickCount % 60 == 0){
                stack.setDamageValue(stack.getDamageValue() - 1);
                if(stack.getDamageValue() <= 0){
                    stack.set(DataComponentRegistry.CHARGING, false);
                }
            }
        }

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        int damage = stack.getOrDefault(DataComponents.DAMAGE, 0);
        if(damage < stack.getMaxDamage() && !stack.getOrDefault(DataComponentRegistry.CHARGING, false)){
            if(!player.hasInfiniteMaterials()){
                stack.setDamageValue(stack.getDamageValue() + 1);
            }
            player.level().playSound(null,
                    player.getX(), player.getY(), player.getZ(),
                    SoundEventRegistry.LASER_CUTTER_SWING,
                    player.getSoundSource(),
                    1.0F, player.level().getRandom().nextFloat() * 0.4F + 0.8F);
            return false;
        }
        else{
            stack.set(DataComponentRegistry.CHARGING, true);
            return true;
        }
    }

    public static boolean isCharging(ItemStack stack){
        return stack.getOrDefault(DataComponentRegistry.CHARGING, false);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
    }

    // |------------------------------------------------------------|
    // |-------------------------Animations-------------------------|
    // |------------------------------------------------------------|

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation DEPLOY_ANIM = RawAnimation.begin().thenPlay("laser_cutter.animation.deploy");
    private static final RawAnimation RETRACT_ANIM = RawAnimation.begin().thenPlay("laser_cutter.animation.retract");

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private LaserCutterItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new LaserCutterItemRenderer();

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

        if(stack.getOrDefault(DataComponentRegistry.CHARGING, false)){
            return state.setAndContinue(RETRACT_ANIM);
        }
        else{
            return state.setAndContinue(DEPLOY_ANIM);
        }

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
