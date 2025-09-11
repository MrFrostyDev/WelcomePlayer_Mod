package xyz.mrfrostydev.welcomeplayer.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AudienceData extends SavedData {
    public static final int MAX_FAVOUR_POOL = 1000;
    public static final int MIN_MAX_POWER = 100;
    private static final int DEFAULT_CHANGE_COOLDOWN = 400; //(24000 / 2) * WorldTickEvents.DAYS_TILL_MOOD_CHANGE;
    public static final Codec<AudienceData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("health").forGetter(AudienceData::getHealth),
                    Codec.INT.fieldOf("power").forGetter(AudienceData::getPower),
                    Codec.INT.fieldOf("maxPower").forGetter(AudienceData::getPower),
                    Codec.INT.fieldOf("globalFavour").forGetter(AudienceData::getGlobalFavour),
                    Codec.INT.fieldOf("favourPool").forGetter(AudienceData::getGlobalFavour),
                    Codec.INT.fieldOf("changeCooldown").forGetter(AudienceData::getChangeCooldown),
                    Codec.BOOL.fieldOf("isPhaseShifting").forGetter(AudienceData::isPhaseShifting),
                    AudiencePhase.CODEC.fieldOf("phase").forGetter(AudienceData::getPhase),
                    AudienceMood.CODEC.fieldOf("mood").forGetter(AudienceData::getMood),
                    AudienceEventManager.CODEC.fieldOf("eventManager").forGetter(AudienceData::getEventManager),
                    ResourceLocation.CODEC.listOf().fieldOf("likedItems").forGetter(AudienceData::getLikedItems),
                    ResourceLocation.CODEC.listOf().fieldOf("dislikedItems").forGetter(AudienceData::getDislikedItems)
            ).apply(instance, AudienceData::new));

    private AudienceDataSmall dataSmall;
    private AudienceDataLarge dataLarge;

    public AudienceData() {
        dataSmall = new AudienceDataSmall();
        dataLarge = new AudienceDataLarge();
    }

    public AudienceData(AudienceDataSmall dataSmall, AudienceDataLarge dataLarge){
        this.dataSmall = dataSmall;
        this.dataLarge = dataLarge;
    }

    private AudienceData(int health, int power, int maxPower,
                         int globalFavour, int favourPool,
                         int changeCooldown, boolean isPhaseShifting, AudiencePhase phase,
                         AudienceMood mood, AudienceEventManager eventManager,
                         List<ResourceLocation> likedItems, List<ResourceLocation> dislikedItems) {
        this.dataSmall = new AudienceDataSmall(health, power, maxPower, globalFavour, favourPool, changeCooldown, isPhaseShifting);
        this.dataLarge = new AudienceDataLarge(eventManager, phase, mood, likedItems, dislikedItems);
    }

    public static AudienceData create(){
        AudienceData data = new AudienceData();
        data.setDirty(true);
        return data;
    }

    public boolean isAlive() {
        return dataSmall.health > 0;
    }

    public int getHealth() {
        return dataSmall.health;
    }

    public void addHealth(int add) {
        this.setHealth(dataSmall.health + add);
    }

    public void setHealth(int value) {
        this.dataSmall.health = value;
        this.setDirty(true);
    }

    public AudienceDataSmall getDataSmall() {
        return dataSmall;
    }

    public AudienceDataLarge getDataLarge() {
        return dataLarge;
    }

    public void setDataSmall(AudienceDataSmall dataSmall){
        this.dataSmall = dataSmall;
    }

    public void setDataLarge(AudienceDataLarge dataLarge) {
        this.dataLarge = dataLarge;
    }

    // |-------------------------------------------------------|
    // |---------------- General Data Handling ----------------|
    // |-------------------------------------------------------|

    public void addPower(int add) {
        this.setPower(dataSmall.power + add);
    }

    public void setPower(int power) {
        dataSmall.power = Math.clamp(power, 0, dataSmall.maxPower);
        this.setDirty(true);
    }

    public int getPower() {
        return dataSmall.power;
    }

    public void addMaxPower(int add) {
        setMaxPower(this.dataSmall.maxPower + add);
    }

    public void setMaxPower(int maxPower) {
        this.dataSmall.maxPower = Math.max(maxPower, MIN_MAX_POWER);
        this.setDirty(true);
    }

    public int getMaxPower() {
        return dataSmall.maxPower;
    }

    public void setGlobalFavour(int value) {
        this.dataSmall.globalFavour = value;
        this.setDirty(true);
    }

    public void addGlobalFavour(int add){
        this.setGlobalFavour(this.dataSmall.globalFavour + add);
    }

    public int getGlobalFavour() {
        return dataSmall.globalFavour;
    }

    public int getFavourPool(){
        return dataSmall.favourPool;
    }

    /**
     * Remove favour from the favour pool. Add favour to pool if value is negative.
     * Returns the removed or added value that was applied with respect to an upper and lower bound.
     */
    public int removeFavourPool(int remove){
        int lastPool = dataSmall.favourPool;
        int total = dataSmall.favourPool - remove;
        int clamped = Math.clamp(total, 0, MAX_FAVOUR_POOL);
        setFavorPool(clamped);
        return clamped == total ? remove : lastPool - clamped;
    }

    /**
     * Set new a favour amount in the favour pool.
     */
    public void setFavorPool(int value){
        dataSmall.favourPool = value;
    }

    public void setMood(AudienceMood mood) {
        this.dataLarge.mood = mood;
        this.setDirty(true);
    }

    public AudienceMood getMood() {
        return dataLarge.mood;
    }

    public AudiencePhase getPhase() {
        int favour = getGlobalFavour();
        if (favour > 400) return AudiencePhase.ADORED;
        if (favour > 100) return AudiencePhase.LIKED;
        if (favour > -100) return AudiencePhase.NEUTRAL;
        if (favour > -400) return AudiencePhase.DISLIKED;
        return AudiencePhase.HATED;
    }

    public AudienceEventManager getEventManager() {
        return dataLarge.eventManager;
    }

    public int getChangeCooldown() {
        return dataSmall.changeCooldown;
    }

    public void startChangeCooldown(){
        dataSmall.changeCooldown = DEFAULT_CHANGE_COOLDOWN;
        this.setDirty(true);
    }

    public boolean isPhaseShifting() {
        return dataSmall.isPhaseShifting;
    }

    public void restartPhaseShift(){
        dataSmall.isPhaseShifting = false;
        dataLarge.phase = getPhase();
        this.setDirty(true);
    }

    public void tickChangeCooldown(){
        if(dataSmall.changeCooldown > 0){
            dataSmall.changeCooldown--;
            this.setDirty(true);
        }
    }

    public void tickCheckPhaseShift(){
        if(!dataSmall.isPhaseShifting && dataLarge.phase != getPhase()){
            dataSmall.isPhaseShifting = true;
            this.setDirty(true);
        }
    }

    // |-----------------------------------------------------|
    // |----------------- Cravings Handling -----------------|
    // |-----------------------------------------------------|

    public boolean likes(ItemStack stack) {
        ResourceLocation resourceLocation = stack.getItemHolder()
                .unwrapKey()
                .orElseThrow()
                .location();
        return dataLarge.likedItems.contains(resourceLocation);
    }

    public boolean dislikes(ItemStack stack) {
        ResourceLocation resourceLocation = stack.getItemHolder()
                .unwrapKey()
                .orElseThrow()
                .location();
        return dataLarge.dislikedItems.contains(resourceLocation);
    }

    public void addLikeItem(Item item) {
        dataLarge.likedItems.add(item.getDefaultInstance().getItemHolder().unwrapKey()
                .orElseThrow()
                .location());
        this.setDirty(true);
    }

    public void addDislikeItem(Item item) {
        dataLarge.dislikedItems.add(item.getDefaultInstance().getItemHolder().unwrapKey()
                .orElseThrow()
                .location());
        this.setDirty(true);
    }

    public boolean clearLikesAndDislikes(){
        if (dataLarge.likedItems.isEmpty() && dataLarge.dislikedItems.isEmpty()) return false;
        dataLarge.likedItems.clear();
        dataLarge.dislikedItems.clear();
        this.setDirty(true);
        return true;
    }

    public List<ResourceLocation> getLikedItems() {
        return dataLarge.likedItems;
    }

    public List<ResourceLocation> getDislikedItems() {
        return dataLarge.dislikedItems;
    }

    // |----------------------------------------------|
    // |-------------- Network Handling --------------|
    // |----------------------------------------------|

    public void syncLite(int health, int power, int maxPower, int globalFavour, int favourPool, int changeCooldown, boolean isPhaseShifting){
        this.dataSmall.health = health;
        this.dataSmall.power = power;
        this.dataSmall.maxPower = maxPower;
        this.dataSmall.globalFavour = globalFavour;
        this.dataSmall.favourPool = favourPool;
        this.dataSmall.changeCooldown = changeCooldown;
        this.dataSmall.isPhaseShifting = isPhaseShifting;
    }

    // |----------------------------------------------|
    // |---------------- NBT Handling ----------------|
    // |----------------------------------------------|

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putInt("health", this.dataSmall.health);
        tag.putInt("power", this.dataSmall.power);
        tag.putInt("maxPower", this.dataSmall.maxPower);
        tag.putInt("globalFavour", this.dataSmall.globalFavour);
        tag.putInt("favourPool", this.dataSmall.favourPool);
        tag.putInt("changeCooldown", this.dataSmall.changeCooldown);
        tag.putBoolean("isPhaseShifting", this.dataSmall.isPhaseShifting);

        tag.putString("phase", this.dataLarge.phase.phase());
        tag.putString("mood", this.dataLarge.mood.mood());

        tag.put("eventManager", AudienceEventManager.CODEC.encodeStart(NbtOps.INSTANCE, getEventManager()).getOrThrow());

        ListTag likedListTag = new ListTag();
        for(ResourceLocation r : this.dataLarge.likedItems){
            likedListTag.add(StringTag.valueOf(r.toString()));
        }

        ListTag dislikedListTag = new ListTag();
        for(ResourceLocation r : this.dataLarge.dislikedItems){
            dislikedListTag.add(StringTag.valueOf(r.toString()));
        }

        tag.put("likedItems", likedListTag);
        tag.put("dislikedItems", dislikedListTag);
        return tag;
    }

    public static AudienceData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        int health = tag.getInt("health");
        int power = tag.getInt("power");
        int maxPower = tag.getInt("maxPower");
        int favour = tag.getInt("globalFavour");
        int favourPool = tag.getInt("favourPool");
        int changeCooldown = tag.getInt("changeCooldown");
        boolean isPhaseShifting = tag.getBoolean("isPhaseShifting");

        String phase = tag.getString("phase");
        String mood = tag.getString("mood");

        AudienceEventManager eventManager = AudienceEventManager.CODEC.parse(NbtOps.INSTANCE, tag.get("eventManager")).getOrThrow();

        ListTag likedListTag = tag.getList("likedItems", Tag.TAG_STRING);
        ListTag dislikedListTag = tag.getList("dislikedItems", Tag.TAG_STRING);

        List<ResourceLocation> likedItems = new ArrayList<>();
        for(Tag t : likedListTag){
            likedItems.add(ResourceLocation.parse(t.getAsString()));
        }
        List<ResourceLocation> dislikedItems = new ArrayList<>();
        for(Tag t : dislikedListTag){
            dislikedItems.add(ResourceLocation.parse(t.getAsString()));
        }

        AudienceData data = new AudienceData(
                health,
                power,
                maxPower,
                favour,
                favourPool,
                changeCooldown,
                isPhaseShifting,
                AudiencePhase.create(phase),
                AudienceMood.create(mood),
                eventManager,
                likedItems,
                dislikedItems
        );

        return data;
    }

    public static Factory<AudienceData> factory(){
        return new Factory<>(AudienceData::create, AudienceData::load);
    }

    public static class AudienceDataSmall {
        private int health;
        private int power;
        private int maxPower;
        private int favourPool;
        private int globalFavour;
        private int changeCooldown;
        private boolean isPhaseShifting;

        public static final StreamCodec<RegistryFriendlyByteBuf, AudienceDataSmall> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public AudienceDataSmall decode(RegistryFriendlyByteBuf buffer) {
                int t1 = ByteBufCodecs.INT.decode(buffer);
                int t2 = ByteBufCodecs.INT.decode(buffer);
                int t3 = ByteBufCodecs.INT.decode(buffer);
                int t4 = ByteBufCodecs.INT.decode(buffer);
                int t5 = ByteBufCodecs.INT.decode(buffer);
                int t6 = ByteBufCodecs.INT.decode(buffer);
                boolean t7 = ByteBufCodecs.BOOL.decode(buffer);
                return new AudienceDataSmall(t1, t2, t3, t4, t5, t6, t7);
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, AudienceDataSmall value) {
                ByteBufCodecs.INT.encode(buffer, value.health);
                ByteBufCodecs.INT.encode(buffer, value.power);
                ByteBufCodecs.INT.encode(buffer, value.maxPower);
                ByteBufCodecs.INT.encode(buffer, value.favourPool);
                ByteBufCodecs.INT.encode(buffer, value.globalFavour);
                ByteBufCodecs.INT.encode(buffer, value.changeCooldown);
                ByteBufCodecs.BOOL.encode(buffer, value.isPhaseShifting);
            }
        };

        private AudienceDataSmall(){
            health = 5000;
            maxPower = 100;
            favourPool = MAX_FAVOUR_POOL;
            globalFavour = 0;
            isPhaseShifting = false;
        }

        public AudienceDataSmall(int health, int power, int maxPower, int globalFavour, int favourPool, int changeCooldown, boolean isPhaseShifting){
            this.health = health;
            this.power = power;
            this.maxPower = maxPower;
            this.globalFavour = globalFavour;
            this.favourPool = favourPool;
            this.changeCooldown = changeCooldown;
            this.isPhaseShifting = isPhaseShifting;
        }
    }

    public static class AudienceDataLarge {
        private final AudienceEventManager eventManager;
        private AudiencePhase phase;
        private AudienceMood mood;
        private List<ResourceLocation> likedItems;
        private List<ResourceLocation> dislikedItems;

        public static final StreamCodec<RegistryFriendlyByteBuf, AudienceDataLarge> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public AudienceDataLarge decode(RegistryFriendlyByteBuf buffer) {
                AudienceEventManager t1 = AudienceEventManager.STREAM_CODEC.decode(buffer);
                AudiencePhase t2 = AudiencePhase.STREAM_CODEC.decode(buffer);
                AudienceMood t3 = AudienceMood.STREAM_CODEC.decode(buffer);
                List<ResourceLocation> t4 = ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list(256)).decode(buffer);
                List<ResourceLocation> t5 = ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list(256)).decode(buffer);
                return new AudienceDataLarge(t1, t2, t3, t4, t5);
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, AudienceDataLarge value) {
                AudienceEventManager.STREAM_CODEC.encode(buffer, value.eventManager);
                AudiencePhase.STREAM_CODEC.encode(buffer, value.phase);
                AudienceMood.STREAM_CODEC.encode(buffer, value.mood);
                ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list(256)).encode(buffer, value.likedItems);
                ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list(256)).encode(buffer, value.dislikedItems);
            }
        };

        private AudienceDataLarge(){
            eventManager = new AudienceEventManager(new HashMap<>());
            mood = AudienceMood.NEUTRAL;
            phase = AudiencePhase.NEUTRAL;
            likedItems = new ArrayList<>();
            dislikedItems = new ArrayList<>();
        }

        public AudienceDataLarge(AudienceEventManager eventManager, AudiencePhase phase, AudienceMood mood, List<ResourceLocation> likedItems, List<ResourceLocation> dislikedItems){
            this.eventManager = eventManager;
            this.phase = phase;
            this.mood = mood;
            this.likedItems = likedItems;
            this.dislikedItems = dislikedItems;
        }
    }
}
