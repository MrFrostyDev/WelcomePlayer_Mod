package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;
import xyz.mrfrostydev.welcomeplayer.data.VendorItem;
import xyz.mrfrostydev.welcomeplayer.data.VendorShopData;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;

import java.util.List;

public class VendorUtil {
    public static final int SMALL_BATTERY_VALUE = 1;
    public static final int BATTERY_VALUE = 6;
    public static final int LARGE_BATTERY_VALUE = 24;

    public static final int MAX_COMMON = 8;
    public static final int MAX_UNCOMMON = 5;
    public static final int MAX_RARE = 3;

    /* Server Specific Handling */
    public static VendorShopData computeVendorData(ServerLevel svlevel){
        SavedData savedData = svlevel.getServer().overworld().getDataStorage().computeIfAbsent(VendorShopData.factory(), "vendor_data");
        if(savedData instanceof VendorShopData VendorShopData){
            return VendorShopData;
        }
        throw new ClassCastException("Saved data computed was not an instance of VendorShop");
    }

    public static VendorShopData getVendorData(ServerLevel svlevel){
        SavedData savedData = svlevel.getServer().overworld().getDataStorage().get(VendorShopData.factory(), "vendor_data");
        if(savedData instanceof VendorShopData VendorShopData){
            return VendorShopData;
        }
        throw new ClassCastException("Saved data get was not an instance of VendorShop");
    }

    public static List<VendorItem> getVendorShopItems(ServerLevel svlevel){
        VendorShopData data = getVendorData(svlevel);
        return data.getStockList();
    }

    public static int getRefreshTime(ServerLevel svlevel){
        VendorShopData data = getVendorData(svlevel);
        return data.getRestockTime();
    }

    public static void doTick(ServerLevel svlevel){
        VendorShopData data = getVendorData(svlevel);
        data.restockTick(svlevel);
    }

    public static int getBatteryBalance(Inventory inv){
        int fValue = 0;

        for(ItemStack stack : inv.items){
            if(stack.is(ItemRegistry.SMALL_BATTERY)){
                fValue += stack.getCount() * SMALL_BATTERY_VALUE;
            }
            if(stack.is(ItemRegistry.BATTERY)){
                fValue += stack.getCount() * BATTERY_VALUE;
            }
            if(stack.is(ItemRegistry.LARGE_BATTERY)){
                fValue += stack.getCount() * LARGE_BATTERY_VALUE;
            }
        }

        return fValue;
    }

    public static boolean tryPurchase(Inventory inv, int cost){
        int fCost = cost;
        int balance = getBatteryBalance(inv);

        if(balance >= fCost){
            for(int i=0; i<3; i++){
                int valMult = switch(i){
                    case 0 -> SMALL_BATTERY_VALUE;
                    case 1 -> BATTERY_VALUE;
                    case 2 -> LARGE_BATTERY_VALUE;
                    default -> 1;
                };

                for(ItemStack stack : inv.items){
                    if(i == 0 && !stack.is(ItemRegistry.SMALL_BATTERY)) continue;
                    if(i == 1 && !stack.is(ItemRegistry.BATTERY)) continue;
                    if(i == 2 && !stack.is(ItemRegistry.LARGE_BATTERY)) continue;

                    int r = fCost - stack.getCount() * valMult;
                    if(r >= 0){
                        fCost = r;
                        stack.setCount(0);
                        if(r == 0) return true;
                    }
                    else{
                        stack.setCount(Mth.floor(Math.abs(r) / (float)valMult));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
