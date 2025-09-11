package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.AudienceMood;
import xyz.mrfrostydev.welcomeplayer.data.AudiencePhase;
import xyz.mrfrostydev.welcomeplayer.network.SyncAudienceDataLargePacket;
import xyz.mrfrostydev.welcomeplayer.network.SyncAudienceDataSmallPacket;
import xyz.mrfrostydev.welcomeplayer.registries.DataAttachmentRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.DataComponentRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;

import java.util.*;
import java.util.stream.Stream;

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
        SavedData savedData = svlevel.getServer().overworld().getDataStorage().computeIfAbsent(AudienceData.factory(), "flesh_lords_data");
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
            if(e.phase().equals(AudiencePhase.ADORED)){
                adoredEvents.add(e);
            }
            else if(e.phase().equals(AudiencePhase.LIKED)){
                likedEvents.add(e);
            }
            else if(e.phase().equals(AudiencePhase.DISLIKED)){
                dislikedEvents.add(e);
            }
            else if(e.phase().equals(AudiencePhase.HATED)){
                hatedEvents.add(e);
            }
            else{
                neutralEvents.add(e);
            }
        }

        Map<AudiencePhase, List<AudienceEvent>> eventMap = new HashMap<>();
        eventMap.put(AudiencePhase.ADORED, adoredEvents);
        eventMap.put(AudiencePhase.LIKED, likedEvents);
        eventMap.put(AudiencePhase.NEUTRAL, neutralEvents);
        eventMap.put(AudiencePhase.DISLIKED, dislikedEvents);
        eventMap.put(AudiencePhase.HATED, hatedEvents);

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
        SavedData savedData = svlevel.getServer().overworld().getDataStorage().get(AudienceData.factory(), "flesh_lords_data");
        if(savedData instanceof AudienceData AudienceData){
            return AudienceData;
        }
        throw new ClassCastException("Saved data get was not an instance of AudienceData");
    }

    public static void startChangeCooldown(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        data.startChangeCooldown();
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

        if(phase.equals(AudiencePhase.ADORED) || phase.equals(AudiencePhase.LIKED)){
            possibleMoods.add(AudienceMood.HAPPY);
            possibleMoods.add(AudienceMood.SAD);
            possibleMoods.add(AudienceMood.BORED);
            possibleMoods.add(AudienceMood.ANGRY);
        }
        else if(phase.equals(AudiencePhase.DISLIKED)){
            possibleMoods.add(AudienceMood.SAD);
            possibleMoods.add(AudienceMood.ANGRY);
        }
        else if(phase.equals(AudiencePhase.HATED)){
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

    public static int getGlobalFavour(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        return data.isAlive() ? data.getGlobalFavour() : 0;
    }

    public static void setGlobalFavour(ServerLevel svlevel, int set){
        AudienceData data = getAudienceData(svlevel);
        if(data.isAlive()){
            data.setGlobalFavour(modifyWithMood(svlevel, set));
        }
    }

    public static void setGlobalFavourRaw(ServerLevel svlevel, int set){
        AudienceData data = getAudienceData(svlevel);
        data.setGlobalFavour(set);
    }

    /**
     * Add global favour to AudienceData.
     * <p>
     * Modifiers are added in the following order:
     * <p>
     * THRESHOLD, MOOD
     */
    public static void addGlobalFavour(ServerLevel svlevel, int add){
        AudienceData data = getAudienceData(svlevel);
        if(data.isAlive()){
            int addModified = add;
            addModified = modifyWithThreshold(svlevel, addModified);
            addModified = modifyWithMood(svlevel, addModified);
            data.addGlobalFavour(addModified);
        }
    }

    public static void addGlobalFavourRaw(ServerLevel svlevel, int add){
        AudienceData data = getAudienceData(svlevel);
        if(data.isAlive()){
            data.addGlobalFavour(add);
        }
    }

    public static AudiencePhase getPhase(ServerLevel svlevel){
        int favour = getGlobalFavour(svlevel);
        if (favour > 400) return AudiencePhase.ADORED;
        if (favour > 100) return AudiencePhase.LIKED;
        if (favour > -100) return AudiencePhase.NEUTRAL;
        if (favour > -400) return AudiencePhase.DISLIKED;
        return AudiencePhase.HATED;
    }

    public static void setMood(ServerLevel svlevel, AudienceMood set){
        AudienceData data = getAudienceData(svlevel);
        data.setMood(set);
    }

    public static AudienceMood getMood(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        return data.getMood();
    }

    public static void setPower(ServerLevel svlevel, int set){
        AudienceData data = getAudienceData(svlevel);
        data.setPower(set);
    }

    public static void addPower(ServerLevel svlevel, int add){
        AudienceData data = getAudienceData(svlevel);
        data.addPower(add);
    }

    public static int getPower(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        return data.getPower();
    }

    public static boolean isPowered(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        return data.getPower() > 0;
    }

    public static void setMaxPower(ServerLevel svlevel, int set){
        AudienceData data = getAudienceData(svlevel);
        data.setMaxPower(set);
    }

    public static void addMaxPower(ServerLevel svlevel, int add){
        AudienceData data = getAudienceData(svlevel);
        data.addMaxPower(add);
    }

    public static int getMaxPower(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        return data.getMaxPower();
    }

    public static void syncToClients(ServerLevel svlevel){
        AudienceData data = AudienceUtil.getAudienceData(svlevel);
        PacketDistributor.sendToAllPlayers(SyncAudienceDataSmallPacket.create(data));
        PacketDistributor.sendToAllPlayers(SyncAudienceDataLargePacket.create(data));
    }

    /* ======================== */
    /* Player Specific Handling */
    /* ======================== */

    public static int getFavourPool(ServerLevel svlevel){
        return AudienceUtil.getAudienceData(svlevel).getFavourPool();
    }

    public static void setFavourPool(ServerLevel svlevel, int value){
        AudienceData data = AudienceUtil.getAudienceData(svlevel);
        data.setFavorPool(Math.clamp(value, 0, AudienceData.MAX_FAVOUR_POOL));
    }

    public static FleshLordsPlayerData getPlayerData(Player player){
        return player.getData(DataAttachmentRegistry.PLAYER_FLESHLORDS_FAVOUR);
    }

    public static int getPlayerFavour(Player player){
        FleshLordsPlayerData data = player.getData(DataAttachmentRegistry.PLAYER_FLESHLORDS_FAVOUR);
        return data.getFavour();
    }

    public static List<ServerPlayer> getPlayersByFavorPercent(ServerLevel svlevel, double percent){
        Map<ServerPlayer, Integer> playerFavourMap = new HashMap<>(200);
        svlevel.players().forEach( svplayer -> playerFavourMap.put(svplayer, AudienceUtil.getPlayerFavour(svplayer)));

        int count = Mth.ceil(playerFavourMap.size() * (percent / 100));
        Stream<Map.Entry<ServerPlayer, Integer>> stream = playerFavourMap
                .entrySet()
                .stream()
                .sorted(Comparator.<Map.Entry<ServerPlayer, Integer>>comparingInt(Map.Entry::getValue).reversed())
                .limit(count);

        return stream.map(Map.Entry::getKey).toList();
    }

    /**
     * Add player favour to the player's FleshLordsPlayerData.
     * <p>
     * Modifiers are added in the following order:
     * <p>
     * THRESHOLD
     */
    public static void addPlayerFavour(ServerLevel svlevel, Player player, int add){
        AudienceData data = AudienceUtil.getAudienceData(svlevel);
        FleshLordsPlayerData plyData = player.getData(DataAttachmentRegistry.PLAYER_FLESHLORDS_FAVOUR);

        int addModified = add;
        if (add > 0) addModified = modifyWithThreshold(svlevel, addModified);

        if(addModified != 0){
            plyData.addFavour(data.removeFavourPool(addModified));
            player.setData(DataAttachmentRegistry.PLAYER_FLESHLORDS_FAVOUR, plyData);
        }
    }

    public static void addPlayerFavourRaw(Player player, int add){
        FleshLordsPlayerData data = player.getData(DataAttachmentRegistry.PLAYER_FLESHLORDS_FAVOUR);
        data.addFavour(add);
        player.setData(DataAttachmentRegistry.PLAYER_FLESHLORDS_FAVOUR, data);
    }

    public static void setPlayerFavour(ServerLevel svlevel, Player player, int value){
        FleshLordsPlayerData data = player.getData(DataAttachmentRegistry.PLAYER_FLESHLORDS_FAVOUR);
        data.setFavour(modifyWithMood(svlevel, value));
        player.setData(DataAttachmentRegistry.PLAYER_FLESHLORDS_FAVOUR, data);
    }

    public static void setPlayerFavourRaw(Player player, int value){
        FleshLordsPlayerData data = player.getData(DataAttachmentRegistry.PLAYER_FLESHLORDS_FAVOUR);
        data.setFavour(value);
        player.setData(DataAttachmentRegistry.PLAYER_FLESHLORDS_FAVOUR, data);
    }

    public static double getAvgPlayerFavour(ServerLevel svlevel){
        List<Integer> values = new ArrayList<>();
        svlevel.getPlayers(ServerPlayer::isAddedToLevel).forEach(player ->
                values.add(AudienceUtil.getPlayerFavour(player))
        );

        return values.stream().mapToDouble(i -> (double)i).average().orElseThrow();
    }

    public static void doTick(ServerLevel svlevel){
        AudienceData data = getAudienceData(svlevel);
        data.tickChangeCooldown();
        data.tickCheckPhaseShift();
    }

    /* =============== */
    /* Value Modifiers */
    /* =============== */

    private static int modifyWithBias(ServerLevel svlevel, int add){
        int addModified = add;
        double avg = getAvgPlayerFavour(svlevel);
        double multiplier = (AudienceData.MAX_FAVOUR_POOL - avg) / (AudienceData.MAX_FAVOUR_POOL / 2.00);
        if (add > 0) addModified = Mth.floor(add * multiplier);
        return addModified;
    }

    private static int modifyWithThreshold(ServerLevel svlevel, int add){
        boolean isNeg = add < 0;
        AudiencePhase phase = getPhase(svlevel);
        if(phase == AudiencePhase.ADORED){
            return isNeg ? Mth.floor(add * 1.75F) : Mth.floor(add * 0.25F);
        }
        if(phase == AudiencePhase.LIKED){
            return isNeg ? Mth.floor(add * 1.5F) : Mth.floor(add * 0.5F);
        }
        if(phase == AudiencePhase.DISLIKED){
            return  Mth.floor(add * 1.5F);
        }
        if(phase == AudiencePhase.HATED){
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
        if(mood == AudienceMood.BORED) modifier = isNeg ? 1.15F : 0.85F;
        if(mood == AudienceMood.SAD) modifier = isNeg ? 1.25F : 0.75F;
        if(mood == AudienceMood.ANGRY) modifier = isNeg ? 1.5F : 0.5F;
        if(mood == AudienceMood.CRUEL) modifier = isNeg ? 1.75F : 0.25F;


        return Mth.ceil(value * modifier);
    }
}
