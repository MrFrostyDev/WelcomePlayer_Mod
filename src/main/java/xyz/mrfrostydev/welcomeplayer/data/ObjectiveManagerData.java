package xyz.mrfrostydev.welcomeplayer.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

public class ObjectiveManagerData extends SavedData {
    public static final StreamCodec<RegistryFriendlyByteBuf, ObjectiveManagerData> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public ObjectiveManagerData decode(RegistryFriendlyByteBuf buffer) {
            PlayerObjective t1 = PlayerObjective.STREAM_CODEC.decode(buffer);
            int t2 = ByteBufCodecs.INT.decode(buffer);
            int t3 = ByteBufCodecs.INT.decode(buffer);
            int t4 = ByteBufCodecs.INT.decode(buffer);
            return new ObjectiveManagerData(t1, t2, t3, t4);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buffer, ObjectiveManagerData value) {
            PlayerObjective.STREAM_CODEC.encode(buffer, value.goingObjective);
            ByteBufCodecs.INT.encode(buffer, value.progress);
            ByteBufCodecs.INT.encode(buffer, value.maxProgress);
            ByteBufCodecs.INT.encode(buffer, value.time);
        }
    };

    PlayerObjective goingObjective;
    int progress;
    int maxProgress;
    int time;

    public ObjectiveManagerData(){
        this.goingObjective = PlayerObjective.NOTHING;
        this.progress = 0;
        this.maxProgress = 1;
        this.time = 0;
    }

    public ObjectiveManagerData(PlayerObjective goingObjective, int progress, int maxProgress, int time){
        this.goingObjective = goingObjective;
        this.progress = progress;
        this.maxProgress = maxProgress;
        this.time = time;
    }

    public static ObjectiveManagerData create(){
        ObjectiveManagerData data = new ObjectiveManagerData();
        data.setDirty();
        return data;
    }

    public void setGoingObjective(PlayerObjective goingObjective) {
        this.goingObjective = goingObjective;
        this.setDirty();
    }

    public void addProgress(int add) {
        this.setProgress(this.progress + add);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        this.setDirty();
    }

    public PlayerObjective getGoingObjective() {
        return goingObjective;
    }

    public int getProgress() {
        return progress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setTime(int value){
        this.time = value;
    }

    public int getTime() {
        return time;
    }

    public void doTick(ServerLevel svlevel){
        if(!AudienceUtil.isActive(svlevel)
                || ObjectiveUtil.getGoingObjective(svlevel).isNothing()
        )return;

        if(time > 0){
            time--;
            if(time <= 0){
                ObjectiveUtil.failObjective(svlevel);
            }
            setDirty();
        }
    }

    public static Factory<ObjectiveManagerData> factory(){
        return new Factory<>(ObjectiveManagerData::create, ObjectiveManagerData::load);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.put("goingObjective", PlayerObjective.CODEC.encodeStart(NbtOps.INSTANCE, this.goingObjective).getOrThrow());
        tag.putInt("progress", this.progress);
        tag.putInt("maxProgress", this.maxProgress);
        tag.putInt("time", this.time);

        return tag;
    }

    public static ObjectiveManagerData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        PlayerObjective goingObjective = PlayerObjective.CODEC.parse(NbtOps.INSTANCE, tag.get("goingObjective")).getOrThrow();
        int progress = tag.getInt("progress");
        int maxProgress = tag.getInt("maxProgress");
        int time = tag.getInt("time");

        return new ObjectiveManagerData(goingObjective, progress, maxProgress, time);
    }
}
