package xyz.mrfrostydev.welcomeplayer.client.events;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.Nullable;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.client.ModArmPoses;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID, value = Dist.CLIENT)
public class ClientExtensionsRegisterEvent {

    @SubscribeEvent
    public static void onClientExtensionItemRegister(RegisterClientExtensionsEvent event){

        // |-------------------------------------------------------------------------------|
        // |------------------------------Shock Orb Launcher-------------------------------|
        // |-------------------------------------------------------------------------------|
        event.registerItem(new IClientItemExtensions(){
            @Override
            public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return ModArmPoses.ONE_HAND_PISTOL_ENUM_PROXY.getValue();
            }
        }, ItemRegistry.SHOCK_ORB_LAUNCHER.get());
    }
}

