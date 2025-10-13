package xyz.mrfrostydev.welcomeplayer.data;

import com.mojang.serialization.DataResult;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import xyz.mrfrostydev.welcomeplayer.blocks.entities.VendorBlockEntity;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.VendorUtil;

import java.util.*;
import java.util.stream.Collectors;

public class VendorShopData extends SavedData {
    public static final int COMMON_MIN_FAVOUR = 0;
    public static final int UNCOMMON_MIN_FAVOUR = 400;
    public static final int RARE_MIN_FAVOUR = 1000;

    public static final int DEFAULT_RESTOCK_TIME = 2000;

    private final List<VendorItem> stockList;
    private int restockTime;
    private static final Comparator<VendorItem> FLESH_MERCHANT_COMPARATOR = new Comparator<VendorItem>() {
        @Override
        public int compare(VendorItem c1, VendorItem c2) {
            return Integer.compare(c1.minInterest(), c2.minInterest());
        }
    };

    private VendorShopData(int restockTime){
        this.stockList = new ArrayList<>(VendorBlockEntity.CONTAINER_SIZE);
        this.restockTime = restockTime;
    }

    public static VendorShopData create(){
        return new VendorShopData(0);
    }

    public VendorShopData(List<VendorItem> list, int restockTime){
        this.stockList = list;
        this.restockTime = restockTime;
    }

    public List<VendorItem> getStockList() {
        return stockList.stream().toList();
    }

    public int getRestockTime() {
        return restockTime;
    }

    public boolean isEmpty(){
        return stockList.isEmpty();
    }

    public void restockTick(ServerLevel svlevel){
        restockTime--;
        if(restockTime <= 0){
            restockTime = DEFAULT_RESTOCK_TIME;
            this.randomizeSelection(svlevel);
        }
        setDirty();
    }

    public void randomizeSelection(Level level){
        List<VendorItem> allMerchantItems = level.registryAccess().registryOrThrow(DatapackRegistry.VENDOR_ITEMS).stream().collect(Collectors.toList());
        List<VendorItem> commonItems = new ArrayList<>(VendorUtil.MAX_COMMON);
        List<VendorItem> uncommonItems = new ArrayList<>(VendorUtil.MAX_UNCOMMON);
        List<VendorItem> rareItems = new ArrayList<>(VendorUtil.MAX_RARE);

        Collections.shuffle(allMerchantItems);

        for(VendorItem item : allMerchantItems){
            boolean commonFull = commonItems.size() >= VendorUtil.MAX_COMMON;
            boolean uncommonFull = uncommonItems.size() >= VendorUtil.MAX_UNCOMMON;
            boolean rareFull = rareItems.size() >= VendorUtil.MAX_RARE;

            if(item.minInterest() >= COMMON_MIN_FAVOUR && item.minInterest() < UNCOMMON_MIN_FAVOUR && !commonFull){
                commonItems.add(item);
            }
            else if(item.minInterest() >= UNCOMMON_MIN_FAVOUR && item.minInterest() < RARE_MIN_FAVOUR && !uncommonFull){
                uncommonItems.add(item);
            }
            else if(item.minInterest() >= RARE_MIN_FAVOUR && !rareFull){
                rareItems.add(item);
            }

            if(commonFull && uncommonFull && rareFull) break;
        }

        stockList.clear();
        stockList.addAll(commonItems);
        stockList.addAll(uncommonItems);
        stockList.addAll(rareItems);
        stockList.sort(FLESH_MERCHANT_COMPARATOR);
        setDirty();
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
