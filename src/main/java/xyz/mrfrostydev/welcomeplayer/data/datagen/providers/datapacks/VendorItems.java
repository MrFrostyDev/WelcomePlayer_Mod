package xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.VendorItem;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;

public class VendorItems {

    public static final ResourceKey<VendorItem> COMMON_APPLE = registerKey("common_apple");
    public static final ResourceKey<VendorItem> COMMON_GRASS_BLOCK = registerKey("common_grass_block");
    public static final ResourceKey<VendorItem> COMMON_COBBLESTONE = registerKey("common_cobblestone");
    public static final ResourceKey<VendorItem> COMMON_OAK_LOG = registerKey("common_oak_log");
    public static final ResourceKey<VendorItem> COMMON_JUNGLE_SAPLING = registerKey("common_jungle_sapling");
    public static final ResourceKey<VendorItem> COMMON_SPRUCE_SAPLING = registerKey("common_spruce_sapling");
    public static final ResourceKey<VendorItem> COMMON_OAK_SAPLING = registerKey("common_oak_sapling");
    public static final ResourceKey<VendorItem> COMMON_PUFFER_FISH = registerKey("common_puffer_fish");
    public static final ResourceKey<VendorItem> COMMON_RAW_COD = registerKey("common_raw_cod");
    public static final ResourceKey<VendorItem> COMMON_RAW_SALMON = registerKey("common_raw_salmon");
    public static final ResourceKey<VendorItem> COMMON_RAW_CHICKEN = registerKey("common_raw_chicken");
    public static final ResourceKey<VendorItem> COMMON_IRON_INGOT = registerKey("common_iron_ingot");
    public static final ResourceKey<VendorItem> COMMON_COAL = registerKey("common_coal");
    public static final ResourceKey<VendorItem> COMMON_BREAD = registerKey("common_bread");
    public static final ResourceKey<VendorItem> COMMON_BOUNCE_PAD = registerKey("common_bounce_pad");
    public static final ResourceKey<VendorItem> COMMON_XP_BOTTLE = registerKey("common_xp_bottle");
    public static final ResourceKey<VendorItem> COMMON_SURVEILLANCE_PAD = registerKey("common_surveillance_pad");
    public static final ResourceKey<VendorItem> COMMON_COPPER_INGOT = registerKey("common_copper_ingot");
    public static final ResourceKey<VendorItem> COMMON_LAPIS_LAZULI = registerKey("common_lapis_lazuli");
    public static final ResourceKey<VendorItem> COMMON_STRING = registerKey("common_string");
    public static final ResourceKey<VendorItem> COMMON_SHIELD = registerKey("common_shield");
    public static final ResourceKey<VendorItem> COMMON_BOOK = registerKey("common_book");
    public static final ResourceKey<VendorItem> COMMON_LEATHER = registerKey("common_leather");
    public static final ResourceKey<VendorItem> COMMON_SMALL_BATTERY = registerKey("common_small_battery");
    public static final ResourceKey<VendorItem> COMMON_CLAY = registerKey("common_clay");
    public static final ResourceKey<VendorItem> COMMON_REDSTONE_DUST = registerKey("common_redstone_dust");

    public static final ResourceKey<VendorItem> UNCOMMON_STASIS_STICK = registerKey("uncommon_stasis_stick");
    public static final ResourceKey<VendorItem> UNCOMMON_IRON_INGOT = registerKey("uncommon_iron_ingot");
    public static final ResourceKey<VendorItem> UNCOMMON_GOLD_INGOT = registerKey("uncommon_gold_ingot");
    public static final ResourceKey<VendorItem> UNCOMMON_DIAMOND = registerKey("uncommon_diamond");
    public static final ResourceKey<VendorItem> UNCOMMON_EMERALD = registerKey("uncommon_emerald");
    public static final ResourceKey<VendorItem> UNCOMMON_REDSTONE_BLOCK = registerKey("uncommon_redstone_block");
    public static final ResourceKey<VendorItem> UNCOMMON_BRICK = registerKey("uncommon_brick");
    public static final ResourceKey<VendorItem> UNCOMMON_RAW_BEEF = registerKey("uncommon_raw_beef");
    public static final ResourceKey<VendorItem> UNCOMMON_BATTERY = registerKey("uncommon_battery");
    public static final ResourceKey<VendorItem> UNCOMMON_QUARTZ = registerKey("uncommon_quartz");
    public static final ResourceKey<VendorItem> UNCOMMON_GHAST_TEAR = registerKey("uncommon_ghast_tear");
    public static final ResourceKey<VendorItem> UNCOMMON_HONEYCOMB = registerKey("uncommon_honeycomb");


    public static final ResourceKey<VendorItem> RARE_PHASE_LINK = registerKey("rare_phase_link");
    public static final ResourceKey<VendorItem> RARE_STASIS_STICK = registerKey("rare_stasis_stick");
    public static final ResourceKey<VendorItem> RARE_DIAMOND = registerKey("rare_diamond");
    public static final ResourceKey<VendorItem> RARE_ANCIENT_DEBRIS = registerKey("rare_ancient_debris");
    public static final ResourceKey<VendorItem> RARE_CRYING_OBSIDIAN = registerKey("rare_crying_obsidian");
    public static final ResourceKey<VendorItem> RARE_ENCHATING_TABLE = registerKey("rare_enchanting_table");
    public static final ResourceKey<VendorItem> RARE_HEART_OF_THE_SEA = registerKey("rare_heart_of_the_sea");
    public static final ResourceKey<VendorItem> RARE_TRIDENT = registerKey("rare_trident");
    public static final ResourceKey<VendorItem> RARE_MACE = registerKey("rare_mace");

    public static void bootstrap(BootstrapContext<VendorItem> context){
        register(context, COMMON_APPLE, new ItemStack(Items.APPLE, 1), 3, 0);
        register(context, COMMON_GRASS_BLOCK, new ItemStack(Items.GRASS_BLOCK, 1), 1, 0);
        register(context, COMMON_COBBLESTONE, new ItemStack(Items.COBBLESTONE, 16), 2, 0);
        register(context, COMMON_OAK_LOG, new ItemStack(Items.OAK_LOG, 12), 2, 0);
        register(context, COMMON_JUNGLE_SAPLING, new ItemStack(Items.JUNGLE_SAPLING, 1), 5, 0);
        register(context, COMMON_SPRUCE_SAPLING, new ItemStack(Items.SPRUCE_SAPLING, 1), 4, 0);
        register(context, COMMON_OAK_SAPLING, new ItemStack(Items.OAK_SAPLING, 1), 4, 0);
        register(context, COMMON_PUFFER_FISH, new ItemStack(Items.PUFFERFISH, 1), 8, 0);
        register(context, COMMON_RAW_COD, new ItemStack(Items.COD, 4), 1, 0);
        register(context, COMMON_RAW_SALMON, new ItemStack(Items.SALMON, 4), 1, 0);
        register(context, COMMON_RAW_CHICKEN, new ItemStack(Items.CHICKEN, 3), 1, 0);
        register(context, COMMON_IRON_INGOT, new ItemStack(Items.IRON_INGOT, 2), 5, 0);
        register(context, COMMON_COAL, new ItemStack(Items.COAL, 5), 3, 0);
        register(context, COMMON_BREAD, new ItemStack(Items.BREAD, 16), 4, 0);
        register(context, COMMON_BOUNCE_PAD, new ItemStack(ItemRegistry.BOUNCE_PAD, 3), 12, 0);
        register(context, COMMON_XP_BOTTLE, new ItemStack(Items.EXPERIENCE_BOTTLE, 1), 4, 0);
        register(context, COMMON_SURVEILLANCE_PAD, new ItemStack(ItemRegistry.SURVEILLANCE_PAD, 1), 20, 0);
        register(context, COMMON_COPPER_INGOT, new ItemStack(Items.COPPER_INGOT, 2), 4, 0);
        register(context, COMMON_LAPIS_LAZULI, new ItemStack(Items.LAPIS_LAZULI, 2), 6, 0);
        register(context, COMMON_STRING, new ItemStack(Items.STRING, 3), 2, 0);
        register(context, COMMON_SHIELD, new ItemStack(Items.SHIELD, 1), 8, 0);
        register(context, COMMON_BOOK, new ItemStack(Items.BOOK, 1), 2, 0);
        register(context, COMMON_LEATHER, new ItemStack(Items.LEATHER, 1), 2, 0);
        register(context, COMMON_SMALL_BATTERY, new ItemStack(ItemRegistry.SMALL_BATTERY, 1), 1, 0);
        register(context, COMMON_CLAY, new ItemStack(Items.CLAY, 8), 2, 0);
        register(context, COMMON_REDSTONE_DUST, new ItemStack(Items.REDSTONE, 16), 5, 0);

        register(context, UNCOMMON_STASIS_STICK, new ItemStack(ItemRegistry.STASIS_STICK, 1), 70, 400);
        register(context, UNCOMMON_IRON_INGOT, new ItemStack(Items.IRON_INGOT, 4), 6, 400);
        register(context, UNCOMMON_GOLD_INGOT, new ItemStack(Items.GOLD_INGOT, 2), 10, 400);
        register(context, UNCOMMON_DIAMOND, new ItemStack(Items.DIAMOND, 1), 20, 400);
        register(context, UNCOMMON_EMERALD, new ItemStack(Items.EMERALD, 6), 12, 400);
        register(context, UNCOMMON_REDSTONE_BLOCK, new ItemStack(Items.REDSTONE_BLOCK, 2), 5, 400);
        register(context, UNCOMMON_BRICK, new ItemStack(Items.BRICK, 10), 2, 400);
        register(context, UNCOMMON_RAW_BEEF, new ItemStack(Items.BEEF, 8), 5, 400);
        register(context, UNCOMMON_BATTERY, new ItemStack(ItemRegistry.BATTERY, 1), 6, 400);
        register(context, UNCOMMON_QUARTZ, new ItemStack(Items.QUARTZ, 4), 2, 400);
        register(context, UNCOMMON_GHAST_TEAR, new ItemStack(Items.GHAST_TEAR, 1), 10, 400);
        register(context, UNCOMMON_HONEYCOMB, new ItemStack(Items.HONEYCOMB, 3), 18, 400);

        register(context, RARE_PHASE_LINK, new ItemStack(ItemRegistry.PHASE_LINK, 1), 150, 1000);
        register(context, RARE_STASIS_STICK, new ItemStack(ItemRegistry.STASIS_STICK, 1), 60, 1000);
        register(context, RARE_DIAMOND, new ItemStack(Items.GHAST_TEAR, 3), 50, 1000);
        register(context, RARE_ANCIENT_DEBRIS, new ItemStack(Items.ANCIENT_DEBRIS, 1), 60, 1000);
        register(context, RARE_CRYING_OBSIDIAN, new ItemStack(Items.CRYING_OBSIDIAN, 1), 25, 1000);
        register(context, RARE_ENCHATING_TABLE, new ItemStack(Items.ENCHANTING_TABLE, 1), 40, 1000);
        register(context, RARE_HEART_OF_THE_SEA, new ItemStack(Items.HEART_OF_THE_SEA, 1), 60, 1000);
        register(context, RARE_TRIDENT, new ItemStack(Items.TRIDENT, 1), 80, 1000);
        register(context, RARE_MACE, new ItemStack(Items.MACE, 1), 160, 1000);

    }

    public static ResourceKey<VendorItem> registerKey(String name){
        return ResourceKey.create(DatapackRegistry.VENDOR_ITEMS, ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, name));
    }

    private static void register(BootstrapContext<VendorItem> context,
                                 ResourceKey<VendorItem> key,
                                 ItemStack stack, int price, int minInterest){

        context.register(key, new VendorItem(stack, price, minInterest));
    }

}
