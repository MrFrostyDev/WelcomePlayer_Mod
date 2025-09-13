package xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudiencePhase;
import xyz.mrfrostydev.welcomeplayer.data.AudienceReward;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;

public class AudienceRewards {

    public static final ResourceKey<AudienceReward> COMMON_APPLES = registerKey("common_apples");

    public static void bootstrap(BootstrapContext<AudienceReward> context){
        register(context, COMMON_APPLES, new ItemStack(Items.APPLE, 3), AudiencePhase.NEUTRAL);
    }

    public static ResourceKey<AudienceReward> registerKey(String name){
        return ResourceKey.create(DatapackRegistry.AUDIENCE_REWARDS, ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, name));
    }

    private static void register(BootstrapContext<AudienceReward> context,
                                 ResourceKey<AudienceReward> key,
                                 ItemStack stack, AudiencePhase phase){

        context.register(key, new AudienceReward(stack, phase));
    }

}
