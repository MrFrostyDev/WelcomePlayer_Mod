package xyz.mrfrostydev.welcomeplayer.data;

import com.mojang.serialization.DataResult;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;

import java.util.*;
import java.util.stream.Collectors;

public class VendorShopData extends SavedData {
    public static final int MAX_COMMON = 6;
    public static final int MAX_UNCOMMON = 4;
    public static final int MAX_RARE = 2;

    public static final int COMMON_MIN_FAVOUR = 0;
    public static final int UNCOMMON_MIN_FAVOUR = 100;
    public static final int RARE_MIN_FAVOUR = 500;

    public static final int DEFAULT_RESTOCK_TIME = 36000;

    private TreeSet<VendorItem> stockTreeSet;
    private int restockTime;
    private static final Comparator<VendorItem> FLESH_MERCHANT_COMPARATOR = new Comparator<VendorItem>() {
        @Override
        public int compare(VendorItem c1, VendorItem c2) {
            return Integer.compare(c1.minFavour(), c2.minFavour());
        }
    };

    private VendorShopData(int restockTime){
        this.stockTreeSet = new TreeSet<>(FLESH_MERCHANT_COMPARATOR);
        this.restockTime = restockTime;
    }

    public static VendorShopData create(){
        return new VendorShopData(0);
    }

    public VendorShopData(List<VendorItem> list, int restockTime){
        this.stockTreeSet = new TreeSet<VendorItem>(FLESH_MERCHANT_COMPARATOR);
        stockTreeSet.addAll(list);
        this.restockTime = restockTime;
    }

    public List<VendorItem> getStockList() {
        return stockTreeSet.stream().toList();
    }

    public int getRestockTime() {
        return restockTime;
    }

    public boolean isEmpty(){
        return stockTreeSet.isEmpty();
    }

    public void restockTick(ServerLevel svlevel){
        restockTime--;
        if(restockTime <= 0){
            restockTime = DEFAULT_RESTOCK_TIME;
            this.randomizeSelection(svlevel);
        }
    }

    public void randomizeSelection(Level level){
        List<VendorItem> allMerchantItems = level.registryAccess().registryOrThrow(DatapackRegistry.VENDOR_ITEMS).stream().collect(Collectors.toList());
        List<VendorItem> commonItems = new ArrayList<>();
        List<VendorItem> uncommonItems = new ArrayList<>(MAX_UNCOMMON);
        List<VendorItem> rareItems = new ArrayList<>(MAX_RARE);

        Collections.shuffle(allMerchantItems);

        for(VendorItem item : allMerchantItems){
            boolean commonFull = commonItems.size() >= MAX_COMMON;
            boolean uncommonFull = uncommonItems.size() >= MAX_UNCOMMON;
            boolean rareFull = rareItems.size() >= MAX_RARE;

            if(item.minFavour() >= COMMON_MIN_FAVOUR && !commonFull){
                commonItems.add(item);
            }
            else if(item.minFavour() >= UNCOMMON_MIN_FAVOUR && !uncommonFull){
                uncommonItems.add(item);
            }
            else if(item.minFavour() >= RARE_MIN_FAVOUR && !rareFull){
                rareItems.add(item);
            }

            if(commonFull && uncommonFull && rareFull) break;
        }

        stockTreeSet.clear();
        stockTreeSet.addAll(allMerchantItems);
    }

    // |----------------------------------------------|
    // |---------------- NBT Handling ----------------|
    // |----------------------------------------------|
    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        VendorItem.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.getStockList());
        tag.putInt("restockTime", restockTime);
        return tag;
    }

    public static VendorShopData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
       DataResult<List<VendorItem>> stockList = VendorItem.CODEC.listOf().parse(NbtOps.INSTANCE, tag);
       int restockTime = tag.getInt("restockTime");
       return new VendorShopData(stockList.getOrThrow(), restockTime);
    }

    public static Factory<VendorShopData> factory(){
        return new Factory<>(VendorShopData::create, VendorShopData::load);
    }
}
