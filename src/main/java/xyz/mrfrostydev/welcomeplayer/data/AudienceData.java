package xyz.mrfrostydev.welcomeplayer.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.List;

public class AudienceData extends SavedData {
    private static final int DEFAULT_CHANGE_COOLDOWN = 400; //(24000 / 2) * WorldTickEvents.DAYS_TILL_MOOD_CHANGE;
    public static final Codec<AudienceData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.fieldOf("active").forGetter(AudienceData::isActive),
                    Codec.INT.fieldOf("interest").forGetter(AudienceData::getInterest),
                    Codec.INT.fieldOf("changeCooldown").forGetter(AudienceData::getChangeCooldown),
                    Codec.BOOL.fieldOf("isPhaseShifting").forGetter(AudienceData::isPhaseShifting),
                    AudiencePhase.CODEC.fieldOf("phase").forGetter(AudienceData::getPhase),
                    AudienceMood.CODEC.fieldOf("mood").forGetter(AudienceData::getMood),
                    AudienceEventManager.CODEC.fieldOf("eventManager").forGetter(AudienceData::getEventManager)
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

    private AudienceData(boolean isActive, int interest, int changeCooldown,
                         boolean isPhaseShifting, AudiencePhase phase,
                         AudienceMood mood, AudienceEventManager eventManager) {
        this.dataSmall = new AudienceDataSmall(isActive, interest, changeCooldown, isPhaseShifting);
        this.dataLarge = new AudienceDataLarge(eventManager, phase, mood);
    }

    public static AudienceData create(){
        AudienceData data = new AudienceData();
        data.setDirty(true);
        return data;
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
    public void setInterest(int value) {
        this.dataSmall.interest = value;
        this.setDirty(true);
    }

    public void addInterest(int add){
        this.setInterest(this.dataSmall.interest + add);
    }

    public int getInterest() {
        return dataSmall.interest;
    }
    
    public void setMood(AudienceMood mood) {
        this.dataLarge.mood = mood;
        this.setDirty(true);
    }

    public AudienceMood getMood() {
        return dataLarge.mood;
    }

    public AudiencePhase getPhase() {
        int interest = getInterest();
        if (interest > 400) return AudiencePhase.THRILLED;
        if (interest > 100) return AudiencePhase.INTERESTED;
        if (interest > -100) return AudiencePhase.NEUTRAL;
        if (interest > -400) return AudiencePhase.BORED;
        return AudiencePhase.FURIOUS;
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

    public void setActive(boolean bool){
        dataSmall.isActive = bool;
        this.setDirty(true);
    }

    public boolean isActive(){
        return dataSmall.isActive;
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

    // |----------------------------------------------|
    // |-------------- Network Handling --------------|
    // |----------------------------------------------|

    // |----------------------------------------------|
    // |---------------- NBT Handling ----------------|
    // |----------------------------------------------|

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putBoolean("isActive", this.dataSmall.isActive);
        tag.putInt("interest", this.dataSmall.interest);
        tag.putInt("changeCooldown", this.dataSmall.changeCooldown);
        tag.putBoolean("isPhaseShifting", this.dataSmall.isPhaseShifting);

        tag.putString("phase", this.dataLarge.phase.phase());
        tag.putString("mood", this.dataLarge.mood.mood());

        tag.put("eventManager", AudienceEventManager.CODEC.encodeStart(NbtOps.INSTANCE, getEventManager()).getOrThrow());
        return tag;
    }

    public static AudienceData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        boolean isActive = tag.getBoolean("isActive");
        int interest = tag.getInt("interest");
        int changeCooldown = tag.getInt("changeCooldown");
        boolean isPhaseShifting = tag.getBoolean("isPhaseShifting");

        String phase = tag.getString("phase");
        String mood = tag.getString("mood");

        AudienceEventManager eventManager = AudienceEventManager.CODEC.parse(NbtOps.INSTANCE, tag.get("eventManager")).getOrThrow();

        AudienceData data = new AudienceData(
                isActive,
                interest,
                changeCooldown,
                isPhaseShifting,
                AudiencePhase.create(phase),
                AudienceMood.create(mood),
                eventManager
        );

        return data;
    }

    public static Factory<AudienceData> factory(){
        return new Factory<>(AudienceData::create, AudienceData::load);
    }

    public static class AudienceDataSmall {
        private boolean isActive;
        private int interest;
        private int changeCooldown;
        private boolean isPhaseShifting;

        public static final StreamCodec<RegistryFriendlyByteBuf, AudienceDataSmall> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public AudienceDataSmall decode(RegistryFriendlyByteBuf buffer) {
                boolean t1 = ByteBufCodecs.BOOL.decode(buffer);
                int t2 = ByteBufCodecs.INT.decode(buffer);
                int t3 = ByteBufCodecs.INT.decode(buffer);
                boolean t4 = ByteBufCodecs.BOOL.decode(buffer);
                return new AudienceDataSmall(t1, t2, t3, t4);
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, AudienceDataSmall value) {
                ByteBufCodecs.BOOL.encode(buffer, value.isActive);
                ByteBufCodecs.INT.encode(buffer, value.interest);
                ByteBufCodecs.INT.encode(buffer, value.changeCooldown);
                ByteBufCodecs.BOOL.encode(buffer, value.isPhaseShifting);
            }
        };

        private AudienceDataSmall(){
            isActive = false;
            interest = 0;
            changeCooldown = 0;
            isPhaseShifting = false;
        }

        public AudienceDataSmall(boolean isActive, int interest, int changeCooldown, boolean isPhaseShifting){
            this.isActive = isActive;
            this.interest = interest;
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
                return new AudienceDataLarge(t1, t2, t3);
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, AudienceDataLarge value) {
                AudienceEventManager.STREAM_CODEC.encode(buffer, value.eventManager);
                AudiencePhase.STREAM_CODEC.encode(buffer, value.phase);
                AudienceMood.STREAM_CODEC.encode(buffer, value.mood);
            }
        };

        private AudienceDataLarge(){
            eventManager = new AudienceEventManager(new HashMap<>());
            mood = AudienceMood.NEUTRAL;
            phase = AudiencePhase.NEUTRAL;
        }

        public AudienceDataLarge(AudienceEventManager eventManager, AudiencePhase phase, AudienceMood mood){
            this.eventManager = eventManager;
            this.phase = phase;
            this.mood = mood;
        }
    }
}
