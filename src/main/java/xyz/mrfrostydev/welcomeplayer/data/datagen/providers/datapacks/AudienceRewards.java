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

    public static final ResourceKey<AudienceReward> DIRT_32 = registerKey("dirt_32");
    public static final ResourceKey<AudienceReward> STRING_10 = registerKey("string_10");
    public static final ResourceKey<AudienceReward> BREAD_10 = registerKey("bread_10");
    public static final ResourceKey<AudienceReward> COAL_8 = registerKey("coal_8");
    public static final ResourceKey<AudienceReward> COAL_10 = registerKey("coal_10");
    public static final ResourceKey<AudienceReward> SMALL_BATTERY_4 = registerKey("small_battery_4");
    public static final ResourceKey<AudienceReward> SMALL_BATTERY_5 = registerKey("small_battery_5");
    public static final ResourceKey<AudienceReward> SMALL_BATTERY_6 = registerKey("small_battery_6");
    public static final ResourceKey<AudienceReward> SMALL_BATTERY_7 = registerKey("small_battery_7");

    public static final ResourceKey<AudienceReward> STRING_20 = registerKey("string_20");
    public static final ResourceKey<AudienceReward> APPLE_14 = registerKey("apple_14");
    public static final ResourceKey<AudienceReward> BREAD_24 = registerKey("bread_24");
    public static final ResourceKey<AudienceReward> COAL_16 = registerKey("coal_16");
    public static final ResourceKey<AudienceReward> IRON_INGOT_3 = registerKey("iron_ingot_3");
    public static final ResourceKey<AudienceReward> EMERALD_6 = registerKey("emerald_6");
    public static final ResourceKey<AudienceReward> SMALL_BATTERY_12 = registerKey("small_battery_12");
    public static final ResourceKey<AudienceReward> SMALL_BATTERY_15 = registerKey("small_battery_15");
    public static final ResourceKey<AudienceReward> BATTERY_4 = registerKey("battery_4");
    public static final ResourceKey<AudienceReward> BATTERY_5 = registerKey("battery_5");

    public static final ResourceKey<AudienceReward> COBBLESTONE_32 = registerKey("cobblestone_32");
    public static final ResourceKey<AudienceReward> APPLE_20 = registerKey("apple_20");
    public static final ResourceKey<AudienceReward> BREAD_50 = registerKey("bread_50");
    public static final ResourceKey<AudienceReward> STRING_36 = registerKey("string_36");
    public static final ResourceKey<AudienceReward> EMERALD_16 = registerKey("emerald_16");
    public static final ResourceKey<AudienceReward> SMALL_BATTERY_20 = registerKey("small_battery_20");
    public static final ResourceKey<AudienceReward> SMALL_BATTERY_26 = registerKey("small_battery_26");
    public static final ResourceKey<AudienceReward> BATTERY_8 = registerKey("battery_8");
    public static final ResourceKey<AudienceReward> BATTERY_9 = registerKey("battery_9");
    public static final ResourceKey<AudienceReward> GOLD_INGOT_3 = registerKey("gold_ingot_3");
    public static final ResourceKey<AudienceReward> IRON_INGOT_5 = registerKey("iron_ingot_5");
    public static final ResourceKey<AudienceReward> DIAMOND_1 = registerKey("diamond_1");
    public static final ResourceKey<AudienceReward> STASIS_STICK_NEUTRAL = registerKey("stasis_stick_neutral");
    public static final ResourceKey<AudienceReward> LASER_CUTTER_NEUTRAL = registerKey("laser_cutter_neutral");
    
    public static final ResourceKey<AudienceReward> COOKED_BEEF_32 = registerKey("cooked_beef_32");
    public static final ResourceKey<AudienceReward> GOLD_INGOT_8 = registerKey("gold_ingot_8");
    public static final ResourceKey<AudienceReward> IRON_INGOT_12 = registerKey("iron_ingot_12");
    public static final ResourceKey<AudienceReward> DIAMOND_4 = registerKey("diamond_4");
    public static final ResourceKey<AudienceReward> BOUNCE_PAD_6 = registerKey("bounce_pad_6");
    public static final ResourceKey<AudienceReward> BATTERY_15 = registerKey("battery_15");
    public static final ResourceKey<AudienceReward> BATTERY_17 = registerKey("battery_17");
    public static final ResourceKey<AudienceReward> BATTERY_19 = registerKey("battery_19");
    public static final ResourceKey<AudienceReward> GOLDEN_APPLE_2 = registerKey("golden_apple_2");
    public static final ResourceKey<AudienceReward> LARGE_BATTERY_1 = registerKey("large_battery_1");
    public static final ResourceKey<AudienceReward> STASIS_STICK_INTERESTED = registerKey("stasis_stick_interested");
    public static final ResourceKey<AudienceReward> PHASE_LINK_INTERESTED = registerKey("phase_link_interested");
    public static final ResourceKey<AudienceReward> LASER_CUTTER_INTERESTED = registerKey("laser_cutter_interested");

    public static final ResourceKey<AudienceReward> IRON_30 = registerKey("iron_30");
    public static final ResourceKey<AudienceReward> IRON_36 = registerKey("iron_36");
    public static final ResourceKey<AudienceReward> GOLD_INGOT_20 = registerKey("gold_ingot_20");
    public static final ResourceKey<AudienceReward> DIAMOND_10 = registerKey("diamond_10");
    public static final ResourceKey<AudienceReward> NETHERITE_SCRAP_2 = registerKey("netherite_scrap_2");
    public static final ResourceKey<AudienceReward> GOLDEN_APPLE_5 = registerKey("golden_apple_5");
    public static final ResourceKey<AudienceReward> BATTERY_26 = registerKey("battery_26");
    public static final ResourceKey<AudienceReward> BATTERY_28 = registerKey("battery_28");
    public static final ResourceKey<AudienceReward> BATTERY_32 = registerKey("battery_32");
    public static final ResourceKey<AudienceReward> LARGE_BATTERY_4 = registerKey("large_battery_4");
    public static final ResourceKey<AudienceReward> LARGE_BATTERY_5 = registerKey("large_battery_5");
    public static final ResourceKey<AudienceReward> PHASE_LINK_THRILLED = registerKey("phase_link_thrilled");
    public static final ResourceKey<AudienceReward> LASER_CUTTER_THRILLED = registerKey("laser_cutter_thrilled");
    public static final ResourceKey<AudienceReward> SHOCK_ORB_LAUNCHER_THRILLED = registerKey("shock_orb_launcher_thrilled");


    public static void bootstrap(BootstrapContext<AudienceReward> context){
        // |------------------------------------------------------------|
        // |-------------------------Furious----------------------------|
        // |------------------------------------------------------------|
        register(context, STRING_10, new ItemStack(Items.STRING, 10), AudiencePhase.FURIOUS);
        register(context, DIRT_32, new ItemStack(Items.DIRT, 32), AudiencePhase.FURIOUS);
        register(context, BREAD_10, new ItemStack(Items.BREAD, 10), AudiencePhase.FURIOUS);
        register(context, COAL_8, new ItemStack(Items.COAL, 8), AudiencePhase.FURIOUS);
        register(context, COAL_10, new ItemStack(Items.COAL, 10), AudiencePhase.FURIOUS);
        register(context, SMALL_BATTERY_4, new ItemStack(ItemRegistry.SMALL_BATTERY, 4), AudiencePhase.FURIOUS);
        register(context, SMALL_BATTERY_5, new ItemStack(ItemRegistry.SMALL_BATTERY, 5), AudiencePhase.FURIOUS);
        register(context, SMALL_BATTERY_6, new ItemStack(ItemRegistry.SMALL_BATTERY, 6), AudiencePhase.FURIOUS);
        register(context, SMALL_BATTERY_7, new ItemStack(ItemRegistry.SMALL_BATTERY, 7), AudiencePhase.FURIOUS);

        // |------------------------------------------------------------|
        // |--------------------------Bored-----------------------------|
        // |------------------------------------------------------------|
        register(context, STRING_20, new ItemStack(Items.STRING, 20), AudiencePhase.BORED);
        register(context, APPLE_14, new ItemStack(Items.APPLE, 14), AudiencePhase.BORED);
        register(context, BREAD_24, new ItemStack(Items.BREAD, 24), AudiencePhase.BORED);
        register(context, COAL_16, new ItemStack(Items.COAL, 16), AudiencePhase.BORED);
        register(context, IRON_INGOT_3, new ItemStack(Items.IRON_INGOT, 3), AudiencePhase.BORED);
        register(context, EMERALD_6, new ItemStack(Items.EMERALD, 6), AudiencePhase.BORED);
        register(context, SMALL_BATTERY_12, new ItemStack(ItemRegistry.SMALL_BATTERY, 12), AudiencePhase.BORED);
        register(context, SMALL_BATTERY_15, new ItemStack(ItemRegistry.SMALL_BATTERY, 15), AudiencePhase.BORED);
        register(context, BATTERY_4, new ItemStack(ItemRegistry.BATTERY, 4), AudiencePhase.BORED);
        register(context, BATTERY_5, new ItemStack(ItemRegistry.BATTERY, 5), AudiencePhase.BORED);

        // |------------------------------------------------------------|
        // |-------------------------Neutral----------------------------|
        // |------------------------------------------------------------|
        register(context, COBBLESTONE_32, new ItemStack(Items.COBBLESTONE, 32), AudiencePhase.NEUTRAL);
        register(context, APPLE_20, new ItemStack(Items.APPLE, 20), AudiencePhase.NEUTRAL);
        register(context, BREAD_50, new ItemStack(Items.BREAD, 50), AudiencePhase.NEUTRAL);
        register(context, STRING_36, new ItemStack(Items.STRING, 36), AudiencePhase.NEUTRAL);
        register(context, IRON_INGOT_5, new ItemStack(Items.IRON_INGOT, 5), AudiencePhase.NEUTRAL);
        register(context, GOLD_INGOT_3, new ItemStack(Items.GOLD_INGOT, 3), AudiencePhase.NEUTRAL);
        register(context, DIAMOND_1, new ItemStack(Items.DIAMOND, 1), AudiencePhase.NEUTRAL);
        register(context, EMERALD_16, new ItemStack(Items.EMERALD, 16), AudiencePhase.NEUTRAL);
        register(context, SMALL_BATTERY_20, new ItemStack(ItemRegistry.SMALL_BATTERY, 20), AudiencePhase.NEUTRAL);
        register(context, SMALL_BATTERY_26, new ItemStack(ItemRegistry.SMALL_BATTERY, 26), AudiencePhase.NEUTRAL);
        register(context, BATTERY_8, new ItemStack(ItemRegistry.BATTERY, 8), AudiencePhase.NEUTRAL);
        register(context, BATTERY_9, new ItemStack(ItemRegistry.BATTERY, 9), AudiencePhase.NEUTRAL);
        register(context, STASIS_STICK_NEUTRAL, new ItemStack(ItemRegistry.STASIS_STICK, 1), AudiencePhase.NEUTRAL);
        register(context, LASER_CUTTER_NEUTRAL, new ItemStack(ItemRegistry.LASER_CUTTER, 1), AudiencePhase.NEUTRAL);

        // |------------------------------------------------------------|
        // |------------------------Interested--------------------------|
        // |------------------------------------------------------------|
        register(context, COOKED_BEEF_32, new ItemStack(Items.COOKED_BEEF, 32), AudiencePhase.INTERESTED);
        register(context, BOUNCE_PAD_6, new ItemStack(ItemRegistry.BOUNCE_PAD, 6), AudiencePhase.INTERESTED);
        register(context, IRON_INGOT_12, new ItemStack(Items.IRON_INGOT, 12), AudiencePhase.INTERESTED);
        register(context, GOLD_INGOT_8, new ItemStack(Items.GOLD_INGOT, 8), AudiencePhase.INTERESTED);
        register(context, DIAMOND_4, new ItemStack(Items.DIAMOND, 4), AudiencePhase.INTERESTED);
        register(context, GOLDEN_APPLE_2, new ItemStack(Items.GOLDEN_APPLE, 2), AudiencePhase.INTERESTED);
        register(context, BATTERY_15, new ItemStack(ItemRegistry.BATTERY, 15), AudiencePhase.INTERESTED);
        register(context, BATTERY_17, new ItemStack(ItemRegistry.BATTERY, 17), AudiencePhase.INTERESTED);
        register(context, BATTERY_19, new ItemStack(ItemRegistry.BATTERY, 19), AudiencePhase.INTERESTED);
        register(context, LARGE_BATTERY_1, new ItemStack(ItemRegistry.LARGE_BATTERY, 1), AudiencePhase.INTERESTED);
        register(context, STASIS_STICK_INTERESTED, new ItemStack(ItemRegistry.STASIS_STICK, 1), AudiencePhase.INTERESTED);
        register(context, PHASE_LINK_INTERESTED, new ItemStack(ItemRegistry.PHASE_LINK, 1), AudiencePhase.INTERESTED);
        register(context, LASER_CUTTER_INTERESTED, new ItemStack(ItemRegistry.LASER_CUTTER, 1), AudiencePhase.INTERESTED);
        
        // |------------------------------------------------------------|
        // |-------------------------Thrilled---------------------------|
        // |------------------------------------------------------------|
        register(context, IRON_30, new ItemStack(Items.IRON_INGOT, 30), AudiencePhase.THRILLED);
        register(context, IRON_36, new ItemStack(Items.IRON_INGOT, 36), AudiencePhase.THRILLED);
        register(context, GOLD_INGOT_20, new ItemStack(Items.GOLD_INGOT, 20), AudiencePhase.THRILLED);
        register(context, DIAMOND_10, new ItemStack(Items.DIAMOND, 10), AudiencePhase.THRILLED);
        register(context, NETHERITE_SCRAP_2, new ItemStack(Items.NETHERITE_SCRAP, 2), AudiencePhase.THRILLED);
        register(context, GOLDEN_APPLE_5, new ItemStack(Items.GOLDEN_APPLE, 5), AudiencePhase.THRILLED);
        register(context, BATTERY_26, new ItemStack(ItemRegistry.BATTERY, 26), AudiencePhase.THRILLED);
        register(context, BATTERY_28, new ItemStack(ItemRegistry.BATTERY, 28), AudiencePhase.THRILLED);
        register(context, BATTERY_32, new ItemStack(ItemRegistry.BATTERY, 32), AudiencePhase.THRILLED);
        register(context, LARGE_BATTERY_4, new ItemStack(ItemRegistry.LARGE_BATTERY, 4), AudiencePhase.THRILLED);
        register(context, LARGE_BATTERY_5, new ItemStack(ItemRegistry.LARGE_BATTERY, 5), AudiencePhase.THRILLED);
        register(context, LASER_CUTTER_THRILLED, new ItemStack(ItemRegistry.LASER_CUTTER, 1), AudiencePhase.THRILLED);
        register(context, PHASE_LINK_THRILLED, new ItemStack(ItemRegistry.PHASE_LINK, 1), AudiencePhase.THRILLED);
        register(context, SHOCK_ORB_LAUNCHER_THRILLED, new ItemStack(ItemRegistry.SHOCK_ORB_LAUNCHER, 1), AudiencePhase.THRILLED);
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
