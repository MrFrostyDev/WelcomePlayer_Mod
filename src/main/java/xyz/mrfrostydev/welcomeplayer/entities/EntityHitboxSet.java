package xyz.mrfrostydev.welcomeplayer.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class EntityHitboxSet {
    public static final int MAX_TARGETS = 32;

    public Predicate<Entity> predicate = new Predicate<>(){
        @Override
        public boolean test(Entity entity) {
            return !hitUUIDList.contains(entity.getUUID());
        }
    };

    private final Entity entity;
    private final boolean damagePerBox;
    private boolean isActive;
    private final List<SequenceSet> sequenceSets;
    private List<UUID> hitUUIDList = new ArrayList<>(MAX_TARGETS);
    private final int time;
    private int tick;

    private EntityHitboxSet(Entity entity, List<SequenceSet> sequenceSets, boolean damagePerBox){
        this.entity = entity;
        this.isActive = false;
        this.sequenceSets = sequenceSets;
        this.damagePerBox = damagePerBox;

        int longestTime = 0;
        for(SequenceSet set : sequenceSets){
            int timestamp = set.getLastTimestamp();
            if(timestamp > longestTime) longestTime = timestamp;
        }

        this.time = longestTime + 2;
        this.tick = 0;
    }

    public void start(){
        this.isActive = true;
        this.tick = 0;
        for(SequenceSet set : sequenceSets){
            set.reset();
        }
        hitUUIDList.clear();
    }

    public void stop(){
        this.isActive = false;
        this.tick = 0;
        for(SequenceSet set : sequenceSets){
            set.reset();
        }
        hitUUIDList.clear();
    }

    public void tick(ServerLevel level){
        if(!this.isActive) return;

        if(tick >= time){
            this.isActive = false;
            return;
        }

        for(SequenceSet set : sequenceSets){
            int nextTimestamp = set.getNextTimestamp();
            if((nextTimestamp != -1) && (nextTimestamp == tick)){
                set.increment();
                if(damagePerBox){
                    hitUUIDList.clear();
                }
            }

            Sequence sequence = set.getActiveSequence();
            AABB relativeAABB = relativeAABB(entity, sequence);
            List<Entity> entities = level.getEntities(entity, relativeAABB, predicate);
            entities.forEach(e -> hitUUIDList.add(e.getUUID()));
            set.getAction().applyOnHit(level, entities, entity);

        }
        tick++;
    }

    /**
     * Return a new AABB with a new position. Position obtained by using sequence data to
     * then calculate the offsets relative to the direction the entity is looking.
     */
    public AABB relativeAABB(Entity entity, Sequence sequence){
        double sizeX = sequence.sizeX / 2;
        double sizeY = sequence.sizeY / 2;
        double sizeZ = sequence.sizeZ / 2;

        Vec3 lookVec3 = entity.getViewVector(1.0F).scale(sequence.forwardOffset);
        Vec3 lookVec3PerpendicularOffset = new Vec3(-lookVec3.z, lookVec3.y, lookVec3.x).scale(sequence.rightOffset);
        Vec3 position = entity.getPosition(1.0F)
                .add(lookVec3)
                .add(lookVec3PerpendicularOffset)
                .add(0, sequence.upOffset, 0);

        AABB newAABB = new AABB(
                position.x - sizeX, position.y - sizeY, position.z - sizeZ,
                position.x + sizeX, position.y + sizeY, position.z + sizeZ);
        /*
        if(entity.level() instanceof ServerLevel svlevel){
            svlevel.sendParticles(ParticleTypes.FLAME, newAABB.minX, newAABB.minY, newAABB.minZ, 1, 0, 0, 0, 0);
            svlevel.sendParticles(ParticleTypes.FLAME, newAABB.getCenter().x, newAABB.getCenter().y, newAABB.getCenter().z, 1, 0, 0, 0, 0);
            svlevel.sendParticles(ParticleTypes.FLAME, newAABB.maxX, newAABB.maxY, newAABB.maxZ, 1, 0, 0, 0, 0);
        }
        */

        return newAABB;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isFinished(){
        return this.tick >= this.time && !this.isActive;
    }

    public int getTime() {
        return time;
    }

    public CompoundTag saveData(){
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("isActive", isActive);

        ListTag uuidtaglist = new ListTag(MAX_TARGETS);
        for(UUID uuid : hitUUIDList){
            CompoundTag uuidTag = new CompoundTag();
            uuidTag.putUUID("uuid", uuid);
            uuidtaglist.add(uuidTag);
        }
        tag.put("uuidList", uuidtaglist);

        ListTag sequenceNums = new ListTag();
        for(SequenceSet set : sequenceSets){
            sequenceNums.add(set.saveData());
        }
        tag.put("sequenceNums", sequenceNums);

        tag.putInt("tick", tick);
        return tag;
    }

    public void loadData(CompoundTag tag){
        this.isActive = tag.getBoolean("isActive");

        ListTag uuidtaglist = tag.getList("uuidList", tag.getTagType("uuidList"));
        List<UUID> uuidList = new ArrayList<>(MAX_TARGETS);
        for(int i=0; i<uuidtaglist.size(); i++){
            CompoundTag t = uuidtaglist.getCompound(i);
            uuidList.add(t.getUUID("uuid"));
        }

        ListTag sequenceNums = tag.getList("sequenceNums", tag.getTagType("sequenceNums"));
        for(int i=0; i<sequenceNums.size(); i++){
            CompoundTag t = sequenceNums.getCompound(i);
            this.sequenceSets.get(i).loadData(t);
        }

        this.tick = tag.getInt("tick");
    }

    public record Sequence(EntityHitbox hitbox,
                           double forwardOffset, double upOffset, double rightOffset,
                           double sizeX, double sizeY, double sizeZ,
                           int timestamp){}

    public static class SequenceSet{
        private int sequenceNum = 0;
        private final List<Sequence> sequences;
        private final EntityHitboxAction action;

        private SequenceSet(List<Sequence> sequences, EntityHitboxAction action){
            this.sequences = sequences;
            this.action = action;
        }

        public Sequence getActiveSequence(){
            return sequences.get(sequenceNum);
        }

        public void increment(){
            sequenceNum++;
        }

        public void reset(){
            sequenceNum = 0;
        }

        public EntityHitboxAction getAction() {
            return action;
        }

        public int getSequenceNum() {
            return sequenceNum;
        }

        public int getNextTimestamp(){
            return sequenceNum + 1 < sequences.size() ? sequences.get(sequenceNum + 1).timestamp : -1;
        }

        public int getLastTimestamp(){
            return sequences.getLast().timestamp;
        }

        public void loadData(CompoundTag tag){
            this.sequenceNum = tag.getInt("sequenceNum");
        }

        public CompoundTag saveData(){
            CompoundTag tag = new CompoundTag();
            tag.putInt("sequenceNum", this.sequenceNum);
            return tag;
        }
    }

    public static class SequenceSetBuilder {
        private double sizeX;
        private double sizeY;
        private double sizeZ;

        private EntityHitbox hitbox;
        private LinkedList<Sequence> sequences = new LinkedList<>();
        private EntityHitboxAction action;

        private SequenceSetBuilder(EntityHitbox hitbox, EntityHitboxAction action){
            AABB aabb = hitbox.getAABB();
            Vec3 center = aabb.getCenter();
            sizeX = center.x;
            sizeY = center.y;
            sizeZ = center.z;
            this.hitbox = hitbox;
            this.sequences.clear();
            this.sequences.add(new Sequence(new EntityHitbox(0, 0, 0), 0, 0 ,0, 0, 0 ,0, 0));
            this.action = action;
        }

        private SequenceSetBuilder(double sizeX, double sizeY, double sizeZ, EntityHitboxAction action) {
            this.hitbox = new EntityHitbox(sizeX, sizeY, sizeZ);

            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.sizeZ = sizeZ;
            this.sequences.clear();
            this.sequences.add(new Sequence(new EntityHitbox(0, 0, 0), 0, 0 ,0, 0, 0 ,0, 0));
            this.action = action;
        }

        public static SequenceSetBuilder create(double sizeX, double sizeY, double sizeZ,
                                                EntityHitboxAction hitboxAction) {
            return new SequenceSetBuilder(sizeX, sizeY, sizeZ, hitboxAction);
        }

        public SequenceSetBuilder transition(double forwardOffset, double upOffset, double rightOffset, int timestamp){
            sequences.add(new Sequence(hitbox, forwardOffset, upOffset, rightOffset, sizeX, sizeY, sizeZ, timestamp));
            return this;
        }

        public SequenceSetBuilder transition(double forwardOffset, double upOffset, double rightOffset,
                                             double sizeX, double sizeY, double sizeZ, int timestamp){
            sequences.add(new Sequence(hitbox, forwardOffset, upOffset, rightOffset, sizeX, sizeY, sizeZ, timestamp));
            return this;
        }

        public SequenceSet build(){
            return new SequenceSet(sequences, action);
        }
    }

    public static class Builder {
        List<SequenceSetBuilder> builders = new ArrayList<>();
        static boolean isDamagePerBox = false;

        public static Builder create() {
            isDamagePerBox = false;
            return new Builder();
        }

        public static Builder create(boolean damagePerBox) {
            isDamagePerBox = damagePerBox;
            return new Builder();
        }

        public Builder add(SequenceSetBuilder builder){
            builders.add(builder);
            return this;
        }

        public EntityHitboxSet build(Entity entity){
            return new EntityHitboxSet(entity, builders.stream().map(SequenceSetBuilder::build).toList(), isDamagePerBox);
        }
    }
}
