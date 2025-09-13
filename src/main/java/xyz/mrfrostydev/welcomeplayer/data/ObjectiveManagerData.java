package xyz.mrfrostydev.welcomeplayer.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.saveddata.SavedData;

public class ObjectiveManagerData extends SavedData {
    PlayerObjective goingObjective;
    int progress;

    public ObjectiveManagerData(){
        this.goingObjective = PlayerObjective.NOTHING;
        this.progress = 0;
    }

    public ObjectiveManagerData(PlayerObjective goingObjective, int progress){
        this.goingObjective = goingObjective;
        this.progress = progress;
    }

    public static ObjectiveManagerData create(){
        ObjectiveManagerData data = new ObjectiveManagerData();
        data.setDirty(true);
        return data;
    }

    public void setGoingObjective(PlayerObjective goingObjective) {
        this.goingObjective = goingObjective;
        this.setDirty(true);
    }

    public void addProgress(int add) {
        this.setProgress(this.progress + add);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        this.setDirty(true);
    }

    public PlayerObjective getGoingObjective() {
        return goingObjective;
    }

    public int getProgress() {
        return progress;
    }

    public static Factory<ObjectiveManagerData> factory(){
        return new Factory<>(ObjectiveManagerData::create, ObjectiveManagerData::load);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.put("goingObjective", PlayerObjective.CODEC.encodeStart(NbtOps.INSTANCE, this.goingObjective).getOrThrow());
        tag.putInt("progress", this.progress);

        return tag;
    }

    public static ObjectiveManagerData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        PlayerObjective goingObjective = PlayerObjective.CODEC.parse(NbtOps.INSTANCE, tag.get("eventManager")).getOrThrow();
        int progress = tag.getInt("progress");

        return new ObjectiveManagerData(goingObjective, progress);
    }
}
