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
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;

public class AudienceRewards {

    public static final ResourceKey<AudienceReward> LOTS_DIRT = registerKey("lots_dirt");
    public static final ResourceKey<AudienceReward> LITTLE_STRING = registerKey("little_string");
    public static final ResourceKey<AudienceReward> LITTLE_BREAD = registerKey("little_bread");
    public static final ResourceKey<AudienceReward> LITTLE_COAL = registerKey("little_coal");
    public static final ResourceKey<AudienceReward> LITTLE_SMALL_BATTERY_4 = registerKey("little_small_battery_4");
    public static final ResourceKey<AudienceReward> LITTLE_SMALL_BATTERY_5 = registerKey("little_small_battery_5");
    public static final ResourceKey<AudienceReward> LITTLE_SMALL_BATTERY_6 = registerKey("little_small_battery_6");

    public static final ResourceKey<AudienceReward> COMMON_APPLE = registerKey("common_apple");
    public static final ResourceKey<AudienceReward> COMMON_BREAD = registerKey("common_bread");
    public static final ResourceKey<AudienceReward> COMMON_COAL = registerKey("common_coal");
    public static final ResourceKey<AudienceReward> COMMON_STRING = registerKey("common_string");
    public static final ResourceKey<AudienceReward> LITTLE_EMERALD = registerKey("little_emerald");
    public static final ResourceKey<AudienceReward> COMMON_SMALL_BATTERY_12 = registerKey("common_small_battery_12");
    public static final ResourceKey<AudienceReward> COMMON_SMALL_BATTERY_15 = registerKey("common_small_battery_15");
    public static final ResourceKey<AudienceReward> LITTLE_BATTERY_4 = registerKey("little_battery_4");
    public static final ResourceKey<AudienceReward> LITTLE_BATTERY_5 = registerKey("little_battery_5");

    public static final ResourceKey<AudienceReward> LOTS_BREAD = registerKey("lots_bread");
    public static final ResourceKey<AudienceReward> LOTS_STRING = registerKey("lots_string");
    public static final ResourceKey<AudienceReward> COMMON_EMERALD = registerKey("common_emerald");
    public static final ResourceKey<AudienceReward> LOTS_SMALL_BATTERY_26 = registerKey("lots_small_battery_26");
    public static final ResourceKey<AudienceReward> LOTS_SMALL_BATTERY_30 = registerKey("lots_small_battery_30");
    public static final ResourceKey<AudienceReward> COMMON_BATTERY_8 = registerKey("common_battery_8");
    public static final ResourceKey<AudienceReward> COMMON_BATTERY_10 = registerKey("common_battery_10");
    public static final ResourceKey<AudienceReward> LITTLE_GOLD = registerKey("little_gold");
    public static final ResourceKey<AudienceReward> LITTLE_IRON = registerKey("little_iron");
    public static final ResourceKey<AudienceReward> LITTLE_DIAMOND = registerKey("little_diamond");

    public static final ResourceKey<AudienceReward> LOTS_COOKED_BEEF = registerKey("lots_cooked_beef");
    public static final ResourceKey<AudienceReward> COMMON_GOLD = registerKey("common_gold");
    public static final ResourceKey<AudienceReward> COMMON_IRON = registerKey("common_iron");
    public static final ResourceKey<AudienceReward> COMMON_DIAMOND = registerKey("common_diamond");
    public static final ResourceKey<AudienceReward> COMMON_BOUNCE_PAD = registerKey("common_bounce_pad");
    public static final ResourceKey<AudienceReward> LOTS_BATTERY_15 = registerKey("lots_battery_15");
    public static final ResourceKey<AudienceReward> LOTS_BATTERY_17 = registerKey("common_battery_17");
    public static final ResourceKey<AudienceReward> LITTLE_GOLDEN_APPLE = registerKey("little_golden_apple");
    public static final ResourceKey<AudienceReward> LITTLE_LARGE_BATTERY_2 = registerKey("little_large_battery_2");
    public static final ResourceKey<AudienceReward> LITTLE_LARGE_BATTERY_3 = registerKey("little_large_battery_3");

    public static final ResourceKey<AudienceReward> COMMON_LARGE_BATTERY_8 = registerKey("common_large_battery_8");
    public static final ResourceKey<AudienceReward> COMMON_LARGE_BATTERY_10 = registerKey("common_large_battery_10");

    public static void bootstrap(BootstrapContext<AudienceReward> context){
        // |------------------------------------------------------------|
        // |-------------------------Furious----------------------------|
        // |------------------------------------------------------------|

        register(context, LITTLE_STRING, new ItemStack(Items.STRING, 10), AudiencePhase.FURIOUS);
        register(context, LOTS_DIRT, new ItemStack(Items.DIRT, 32), AudiencePhase.FURIOUS);
        register(context, LITTLE_BREAD, new ItemStack(Items.BREAD, 10), AudiencePhase.FURIOUS);
        register(context, LITTLE_COAL, new ItemStack(Items.COAL, 8), AudiencePhase.FURIOUS);
        register(context, LITTLE_SMALL_BATTERY_4, new ItemStack(ItemRegistry.SMALL_BATTERY, 4), AudiencePhase.BORED);
        register(context, LITTLE_SMALL_BATTERY_5, new ItemStack(ItemRegistry.SMALL_BATTERY, 5), AudiencePhase.BORED);
        register(context, LITTLE_SMALL_BATTERY_6, new ItemStack(ItemRegistry.SMALL_BATTERY, 6), AudiencePhase.BORED);

        // |------------------------------------------------------------|
        // |--------------------------Bored-----------------------------|
        // |------------------------------------------------------------|

        register(context, COMMON_STRING, new ItemStack(Items.STRING, 24), AudiencePhase.BORED);
        register(context, COMMON_APPLE, new ItemStack(Items.APPLE, 20), AudiencePhase.BORED);
        register(context, COMMON_BREAD, new ItemStack(Items.BREAD, 24), AudiencePhase.BORED);
        register(context, COMMON_COAL, new ItemStack(Items.COAL, 16), AudiencePhase.BORED);
        register(context, LITTLE_EMERALD, new ItemStack(Items.EMERALD, 6), AudiencePhase.BORED);
        register(context, COMMON_SMALL_BATTERY_12, new ItemStack(ItemRegistry.SMALL_BATTERY, 12), AudiencePhase.BORED);
        register(context, COMMON_SMALL_BATTERY_15, new ItemStack(ItemRegistry.SMALL_BATTERY, 15), AudiencePhase.BORED);
        register(context, LITTLE_BATTERY_4, new ItemStack(ItemRegistry.BATTERY, 4), AudiencePhase.BORED);
        register(context, LITTLE_BATTERY_5, new ItemStack(ItemRegistry.BATTERY, 5), AudiencePhase.BORED);

        // |------------------------------------------------------------|
        // |-------------------------Neutral----------------------------|
        // |------------------------------------------------------------|

        register(context, LOTS_BREAD, new ItemStack(Items.BREAD, 50), AudiencePhase.NEUTRAL);
        register(context, LOTS_STRING, new ItemStack(Items.STRING, 60), AudiencePhase.NEUTRAL);
        register(context, LITTLE_IRON, new ItemStack(Items.IRON_INGOT, 5), AudiencePhase.NEUTRAL);
        register(context, LITTLE_GOLD, new ItemStack(Items.GOLD_INGOT, 3), AudiencePhase.NEUTRAL);
        register(context, LITTLE_DIAMOND, new ItemStack(Items.DIAMOND, 1), AudiencePhase.NEUTRAL);
        register(context, COMMON_EMERALD, new ItemStack(Items.EMERALD, 16), AudiencePhase.NEUTRAL);
        register(context, LOTS_SMALL_BATTERY_26, new ItemStack(ItemRegistry.SMALL_BATTERY, 26), AudiencePhase.NEUTRAL);
        register(context, LOTS_SMALL_BATTERY_30, new ItemStack(ItemRegistry.SMALL_BATTERY, 30), AudiencePhase.NEUTRAL);
        register(context, COMMON_BATTERY_8, new ItemStack(ItemRegistry.BATTERY, 8), AudiencePhase.NEUTRAL);
        register(context, COMMON_BATTERY_10, new ItemStack(ItemRegistry.BATTERY, 10), AudiencePhase.NEUTRAL);

        // |------------------------------------------------------------|
        // |------------------------Interested--------------------------|
        // |------------------------------------------------------------|

        register(context, LOTS_COOKED_BEEF, new ItemStack(Items.COOKED_BEEF, 32), AudiencePhase.INTERESTED);
        register(context, COMMON_BOUNCE_PAD, new ItemStack(ItemRegistry.BOUNCE_PAD, 6), AudiencePhase.INTERESTED);
        register(context, COMMON_IRON, new ItemStack(Items.GOLDEN_APPLE, 12), AudiencePhase.INTERESTED);
        register(context, COMMON_GOLD, new ItemStack(Items.GOLDEN_APPLE, 8), AudiencePhase.INTERESTED);
        register(context, COMMON_DIAMOND, new ItemStack(Items.GOLDEN_APPLE, 3), AudiencePhase.INTERESTED);
        register(context, LITTLE_GOLDEN_APPLE, new ItemStack(Items.GOLDEN_APPLE, 1), AudiencePhase.INTERESTED);
        register(context, LOTS_BATTERY_15, new ItemStack(ItemRegistry.BATTERY, 15), AudiencePhase.INTERESTED);
        register(context, LOTS_BATTERY_17, new ItemStack(ItemRegistry.BATTERY, 17), AudiencePhase.INTERESTED);
        register(context, LITTLE_LARGE_BATTERY_2, new ItemStack(ItemRegistry.LARGE_BATTERY, 2), AudiencePhase.INTERESTED);
        register(context, LITTLE_LARGE_BATTERY_3, new ItemStack(ItemRegistry.LARGE_BATTERY, 3), AudiencePhase.INTERESTED);

        // |------------------------------------------------------------|
        // |-------------------------Thrilled---------------------------|
        // |------------------------------------------------------------|
        register(context, COMMON_LARGE_BATTERY_8, new ItemStack(ItemRegistry.LARGE_BATTERY, 8), AudiencePhase.THRILLED);
        register(context, COMMON_LARGE_BATTERY_10, new ItemStack(ItemRegistry.LARGE_BATTERY, 10), AudiencePhase.THRILLED);
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
