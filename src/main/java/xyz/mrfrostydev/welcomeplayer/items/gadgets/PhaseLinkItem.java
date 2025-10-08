package xyz.mrfrostydev.welcomeplayer.items.gadgets;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.client.renderers.item.PhaseLinkItemRenderer;

import java.util.ArrayDeque;
import java.util.Deque;import java.util.function.Consumer;

public class PhaseLinkItem extends Item implements GeoItem {
    int maxBlinkDistance;

    public PhaseLinkItem(Properties properties, int blinkDistance) {
        super(properties);
        this.maxBlinkDistance = blinkDistance;

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack usedStack = player.getItemInHand(usedHand);

        BlockPos curPos = player.blockPosition();
        Direction dir = player.getDirection();
        BlockPos newPos = curPos.relative(dir);

        if(usedHand == InteractionHand.MAIN_HAND)
            triggerAnim(player, GeoItem.getId(usedStack), "Use", "use");
        else
            triggerAnim(player, GeoItem.getId(usedStack), "Use", "use_offhand");

        Deque<BlockPos> freeSpaces = new ArrayDeque<>();
        for(int i=0; i<maxBlinkDistance; i++){
            if(safeToBlink(level, newPos)){
                freeSpaces.push(newPos);
            }
            newPos = newPos.relative(dir);
        }
        if(!freeSpaces.isEmpty() && safeToBlink(level, freeSpaces.peek())){
            newPos = freeSpaces.pop();
            player.teleportTo(newPos.getX() + 0.5, newPos.getY(), newPos.getZ() + 0.5);
            player.getCooldowns().addCooldown(this, 100);
            return InteractionResultHolder.consume(usedStack);
        }
        else{
            return InteractionResultHolder.fail(usedStack);
        }
    }

    private static boolean safeToBlink(Level level, BlockPos checkPos){
        return level.getBlockState(checkPos).isAir() && level.getBlockState(checkPos.above()).isAir();
    }

    // |------------------------------------------------------------|
    // |-------------------------Animations-------------------------|
    // |------------------------------------------------------------|

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation USE_ANIM = RawAnimation.begin().thenPlay("phase_link.animation.use");
    private static final RawAnimation USE_OFFHAND_ANIM = RawAnimation.begin().thenPlay("phase_link.animation.use_offhand");

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private PhaseLinkItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new PhaseLinkItemRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Use", 0, this::handleAnim)
                .triggerableAnim("use", USE_ANIM).triggerableAnim("use_offhand", USE_OFFHAND_ANIM).receiveTriggeredAnimations());
    }

    public PlayState handleAnim(AnimationState<PhaseLinkItem> state){
        if(state.getData(DataTickets.ITEM_RENDER_PERSPECTIVE).firstPerson()){
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public boolean isPerspectiveAware() {
        return true;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
