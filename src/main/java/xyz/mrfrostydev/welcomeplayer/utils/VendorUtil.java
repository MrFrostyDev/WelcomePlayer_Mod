package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;
import xyz.mrfrostydev.welcomeplayer.data.VendorItem;
import xyz.mrfrostydev.welcomeplayer.data.VendorShop;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;

import java.util.List;

public class VendorUtil {

    /* Server Specific Handling */
    public static VendorShop computeMerchantShop(ServerLevel svlevel){
        SavedData savedData = svlevel.getServer().overworld().getDataStorage().computeIfAbsent(VendorShop.factory(), "vendor_data");
        if(savedData instanceof VendorShop VendorShopData){
            return VendorShopData;
        }
        throw new ClassCastException("Saved data computed was not an instance of VendorShop");
    }

    public static VendorShop getMerchantShop(ServerLevel svlevel){
        SavedData savedData = svlevel.getServer().overworld().getDataStorage().get(VendorShop.factory(), "vendor_data");
        if(savedData instanceof VendorShop VendorShopData){
            return VendorShopData;
        }
        throw new ClassCastException("Saved data get was not an instance of VendorShop");
    }

    public static List<VendorItem> getMerchantShopItems(ServerLevel svlevel){
        VendorShop data = getMerchantShop(svlevel);
        return data.getStockList();
    }

    public static int getRefreshTime(ServerLevel svlevel){
        VendorShop data = getMerchantShop(svlevel);
        return data.getRestockTime();
    }

    public static void doTick(ServerLevel svlevel){
        VendorShop data = getMerchantShop(svlevel);
        data.restockTick(svlevel);
    }

    public static boolean tryPurchase(Inventory inv, int cost){
        int fValue = 0;
        int fCost = cost;

        for(ItemStack stack : inv.items){
            if(stack.is(ItemRegistry.SMALL_BATTERY)){
                fValue += stack.getCount();
            }
            if(stack.is(ItemRegistry.BATTERY)){
                fValue += stack.getCount() * 4;
            }
            if(stack.is(ItemRegistry.LARGE_BATTERY)){
                fValue += stack.getCount() * 24;
            }
        }

        if(fValue >= fCost){
            for(ItemStack stack : inv.items){
                if(fCost <= 0) break;

                boolean useLargeBattery = !inv.hasAnyMatching(s -> s.is(ItemRegistry.SMALL_BATTERY))
                        && !inv.hasAnyMatching(s -> s.is(ItemRegistry.BATTERY))
                        && stack.is(ItemRegistry.LARGE_BATTERY);;

                boolean useNormalBattery = !inv.hasAnyMatching(s -> s.is(ItemRegistry.LARGE_BATTERY))
                        && stack.is(ItemRegistry.BATTERY);

                int multBasedOnItem = 1;
                if(useLargeBattery) multBasedOnItem = 4;
                else if(useNormalBattery) multBasedOnItem = 24;

                int r = fCost - stack.getCount() * multBasedOnItem;
                if(r >= 0){
                    fCost = r;
                    stack.setCount(0);
                }
                else{
                    fCost = 0;
                        stack.setCount(Math.abs(r));
                }

            }
            return true;
        }
        return false;
    }
}
