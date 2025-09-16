package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.AudienceMood;
import xyz.mrfrostydev.welcomeplayer.data.AudiencePhase;
import xyz.mrfrostydev.welcomeplayer.network.SyncAudienceDataLargePacket;
import xyz.mrfrostydev.welcomeplayer.network.SyncAudienceDataSmallPacket;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;

import java.util.*;

public class AudienceUtil {
    private static Random RANDOM = new Random();

    /* ======================== */
    /* Server Specific Handling */
    /* ======================== */
    /**
     * Create and return the {@link AudienceData} if it does not exist within DataStorage.
     * This is where {@link AudienceEvent}s are initialized into the newly created AudienceData from the datapack.
     */
    public static AudienceData computeAudienceData(ServerLevel svlevel){
        SavedData savedData = svlevel.getServer().overworld().getDataStorage().computeIfAbsent(AudienceData.factory(), "audience_data");
        if(savedData instanceof AudienceData AudienceData){
            initializeAudienceEvents(AudienceData, svlevel);
            return AudienceData;
        }
        throw new ClassCastException("Saved data computed was not an instance of AudienceData");
    }

    /**
     * Initialize events from {@link DatapackRegistry#AUDIENCE_EVENTS} registry and place them into
     * the {@link AudienceData}'s data manager.
     */
    public static void initializeAudienceEvents(AudienceData data, ServerLevel svlevel){
        List<AudienceEvent> events = svlevel.registryAccess().registryOrThrow(DatapackRegistry.AUDIENCE_EVENTS).stream().toList();

        List<AudienceEvent> adoredEvents = new ArrayList<>();
        List<AudienceEvent> likedEvents = new ArrayList<>();
        List<AudienceEvent> neutralEvents = new ArrayList<>();
        List<AudienceEvent> dislikedEvents = new ArrayList<>();
        List<AudienceEvent> hatedEvents = new ArrayList<>();

        for(AudienceEvent e : events){
            if(e.phase().equals(AudiencePhase.THRILLED)){
                adoredEvents.add(e);
            }
            else if(e.phase().equals(AudiencePhase.INTERESTED)){
                likedEvents.add(e);
            }
            else if(e.phase().equals(AudiencePhase.BORED)){
                dislikedEvents.add(e);
            }
            else if(e.phase().equals(AudiencePhase.FURIOUS)){
                hatedEvents.add(e);
            }
            else{
                neutralEvents.add(e);
            }
        }

        Map<AudiencePhase, List<AudienceEvent>> eventMap = new HashMap<>();
        eventMap.put(AudiencePhase.THRILLED, adoredEvents);
        eventMap.put(AudiencePhase.INTERESTED, likedEvents);
        eventMap.put(AudiencePhase.NEUTRAL, neutralEvents);
        eventMap.put(AudiencePhase.BORED, dislikedEvents);
        eventMap.put(AudiencePhase.FURIOUS, hatedEvents);

        // If some phases have no events, fill them with empty events.
        for(Map.Entry<AudiencePhase, List<AudienceEvent>> entry : eventMap.entrySet()){
            if(entry.getValue().isEmpty()){
                eventMap.put(entry.getKey(), List.of(AudienceEvent.NOTHING));
            }
        }

        data.getEventManager().setEventMap(eventMap);
        data.setDirty();
    }

    public static AudienceData getAudienceData(ServerLevel svlevel){
        SavedData savedData = svlevel.getServer().overworld().getDataStorage().get(AudienceData.factory(), "audience_data");
        if(savedData instanceof AudienceData AudienceData){
            return AudienceData;
        }
        throw new ClassCastException("Saved data get was not an instance of AudienceData");
    }

    public static void startChangeCooldown(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        data.startChangeCooldown();
    }

    public static boolean isActive(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        return data.isActive();
    }

    public static boolean isChangeCooldown(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        return data.getChangeCooldown() > 0;
    }

    public static boolean isPhaseShifting(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        return data.isPhaseShifting();
    }

    public static void restartPhaseShift(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        data.restartPhaseShift();
    }

    public static void pickMood(ServerLevel svlevel){
        AudiencePhase phase = getPhase(svlevel);

        List<AudienceMood> possibleMoods = new ArrayList<>();
        possibleMoods.add(AudienceMood.NEUTRAL);

        if(phase.equals(AudiencePhase.THRILLED) || phase.equals(AudiencePhase.INTERESTED)){
            possibleMoods.add(AudienceMood.HAPPY);
            possibleMoods.add(AudienceMood.SAD);
            possibleMoods.add(AudienceMood.ANGRY);
        }
        else if(phase.equals(AudiencePhase.BORED)){
            possibleMoods.add(AudienceMood.SAD);
            possibleMoods.add(AudienceMood.ANGRY);
        }
        else if(phase.equals(AudiencePhase.FURIOUS)){
            possibleMoods.add(AudienceMood.ANGRY);
            possibleMoods.add(AudienceMood.CRUEL);
        }
        else{
            possibleMoods.add(AudienceMood.HAPPY);
            possibleMoods.add(AudienceMood.SAD);
        }

        Collections.shuffle(possibleMoods);
        AudienceMood rndMood = possibleMoods.stream().findAny().orElseThrow();

        setMood(svlevel, rndMood);
    }

    public static int getInterest(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        return data.getInterest();
    }

    public static void setInterest(ServerLevel svlevel, int set){
        AudienceData data = getAudienceData(svlevel);
        data.setInterest(modifyWithMood(svlevel, set));
    }

    public static void setInterestRaw(ServerLevel svlevel, int set){
        AudienceData data = getAudienceData(svlevel);
        data.setInterest(set);
    }

    /**
     * Add interest to AudienceData.
     * <p>
     * Modifiers are added in the following order:
     * <p>
     * PHASE, MOOD
     */
    public static void addInterest(ServerLevel svlevel, int add){
        AudienceData data = getAudienceData(svlevel);
        int addModified = add;
        addModified = modifyWithThreshold(svlevel, addModified);
        addModified = modifyWithMood(svlevel, addModified);
        data.addInterest(addModified);

    }

    public static void addInterestRaw(ServerLevel svlevel, int add){
        AudienceData data = getAudienceData(svlevel);
        data.addInterest(add);

    }

    public static AudiencePhase getPhase(ServerLevel svlevel){
        int favour = getInterest(svlevel);
        if (favour > 400) return AudiencePhase.THRILLED;
        if (favour > 100) return AudiencePhase.INTERESTED;
        if (favour > -100) return AudiencePhase.NEUTRAL;
        if (favour > -400) return AudiencePhase.BORED;
        return AudiencePhase.FURIOUS;
    }

    public static void setMood(ServerLevel svlevel, AudienceMood set){
        AudienceData data = getAudienceData(svlevel);
        data.setMood(set);
    }

    public static AudienceMood getMood(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        return data.getMood();
    }

    public static void syncToClients(ServerLevel svlevel){
        AudienceData data = AudienceUtil.getAudienceData(svlevel);
        PacketDistributor.sendToAllPlayers(SyncAudienceDataSmallPacket.create(data));
        PacketDistributor.sendToAllPlayers(SyncAudienceDataLargePacket.create(data));
    }

    /* ======================== */
    /* Player Specific Handling */
    /* ======================== */

    public static void doTick(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        data.tickChangeCooldown();
        data.tickCheckPhaseShift();
    }

    /* =============== */
    /* Value Modifiers */
    /* =============== */

    private static int modifyWithThreshold(ServerLevel svlevel, int add){
        boolean isNeg = add < 0;
        AudiencePhase phase = getPhase(svlevel);
        if(phase == AudiencePhase.THRILLED){
            return isNeg ? Mth.floor(add * 1.75F) : Mth.floor(add * 0.25F);
        }
        if(phase == AudiencePhase.INTERESTED){
            return isNeg ? Mth.floor(add * 1.5F) : Mth.floor(add * 0.5F);
        }
        if(phase == AudiencePhase.BORED){
            return  Mth.floor(add * 1.5F);
        }
        if(phase == AudiencePhase.FURIOUS){
            return Mth.floor(add * 1.75F);
        }
        return Mth.floor(add * 1.0F);
    }

    private static int modifyWithMood(ServerLevel svlevel, int value){
        AudienceData data = getAudienceData(svlevel);

        boolean isNeg = value < 0;
        float modifier = 1.0F;
        AudienceMood mood = data.getMood();
        if(mood == AudienceMood.HAPPY) modifier = isNeg ? 0.5F : 1.5F;
        if(mood == AudienceMood.SAD) modifier = isNeg ? 1.25F : 0.75F;
        if(mood == AudienceMood.ANGRY) modifier = isNeg ? 1.5F : 0.5F;
        if(mood == AudienceMood.CRUEL) modifier = isNeg ? 1.75F : 0.25F;


        return Mth.ceil(value * modifier);
    }
}
