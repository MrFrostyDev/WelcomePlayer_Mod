package xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.VendorItem;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;

public class VendorItems {

    public static final ResourceKey<VendorItem> COMMON_APPLES = registerKey("common_apples");
    public static final ResourceKey<VendorItem> RARE_IRON = registerKey("rare_iron");

    public static void bootstrap(BootstrapContext<VendorItem> context){
        register(context, COMMON_APPLES, new ItemStack(Items.APPLE, 3), 10, 0);
        register(context, RARE_IRON, new ItemStack(Items.IRON_INGOT, 1), 10, 200);
    }

    public static ResourceKey<VendorItem> registerKey(String name){
        return ResourceKey.create(DatapackRegistry.VENDOR_ITEMS, ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, name));
    }

    private static void register(BootstrapContext<VendorItem> context,
                                 ResourceKey<VendorItem> key,
                                 ItemStack stack, int price, int minFavour){

        context.register(key, new VendorItem(stack, price, minFavour));
    }

}
