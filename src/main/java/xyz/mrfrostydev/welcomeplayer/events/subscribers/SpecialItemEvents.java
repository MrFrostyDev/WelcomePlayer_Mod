package xyz.mrfrostydev.welcomeplayer.events.subscribers;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.items.gadgets.LaserCutterItem;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class SpecialItemEvents {

    @SubscribeEvent
    public static void onDamageLaserCutter(LivingIncomingDamageEvent event){
        ItemStack weapon = event.getSource().getWeaponItem();
        if (weapon != null && weapon.getItem() instanceof LaserCutterItem){
            event.addReductionModifier(
                    DamageContainer.Reduction.ARMOR,
                    (container, reduction) ->
                            reduction * 0.5F
            );
        }
    }
}
