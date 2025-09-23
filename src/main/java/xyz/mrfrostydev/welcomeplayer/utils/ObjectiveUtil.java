package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.mrfrostydev.welcomeplayer.data.*;
import xyz.mrfrostydev.welcomeplayer.events.ObjectiveEndEvent;
import xyz.mrfrostydev.welcomeplayer.events.ObjectiveStartedEvent;
import xyz.mrfrostydev.welcomeplayer.network.ServerShowHostMessagePacket;
import xyz.mrfrostydev.welcomeplayer.network.SyncAudienceDataLargePacket;
import xyz.mrfrostydev.welcomeplayer.network.SyncAudienceDataSmallPacket;
import xyz.mrfrostydev.welcomeplayer.network.SyncObjectiveDataPacket;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;

import java.util.List;
import java.util.Random;

public class ObjectiveUtil {
    public static Random RANDOM = new Random();

    /**
     * Create and return the {@link ObjectiveManagerData} if it does not exist within DataStorage.
     */
    public static ObjectiveManagerData computeObjectiveManagerData(ServerLevel svlevel){
        SavedData savedData = svlevel.getServer().overworld().getDataStorage().computeIfAbsent(ObjectiveManagerData.factory(), "objective_manager_data");
        if(savedData instanceof ObjectiveManagerData data){
            return data;
        }
        throw new ClassCastException("Saved data computed was not an instance of ObjectiveManagerData");
    }

    public static ObjectiveManagerData getObjectiveManager(ServerLevel svlevel){
        SavedData savedData = svlevel.getServer().overworld().getDataStorage().get(ObjectiveManagerData.factory(), "objective_manager_data");
        if(savedData instanceof ObjectiveManagerData data){
            return data;
        }
        throw new ClassCastException("Saved data get was not an instance of ObjectiveManagerData");
    }

    public static void pickObjective(ServerLevel svlevel){
        int pc = svlevel.getPlayers(s -> !s.isSpectator()).size();
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        AudienceData audData = AudienceUtil.getAudienceData(svlevel);
        data.setProgress(0);
        data.setTime(2000);

        NeoForge.EVENT_BUS.post(new ObjectiveEndEvent(svlevel, audData, data.getGoingObjective()));

        AudiencePhase phase = AudienceUtil.getPhase(svlevel);
        List<PlayerObjective> objList = svlevel
                .registryAccess()
                .registryOrThrow(DatapackRegistry.PLAYER_OBJECTIVES)
                .stream()
                .filter(o -> o.phase().is(phase))
                .toList();
        if(objList.size() <= 0){
            throw new RuntimeException("While picking an objective, no options were available. Datapack may not have enough enabled objectives for this phase.");
        }
        PlayerObjective obj = objList.get(RANDOM.nextInt(objList.size()));

        data.setMaxProgress(obj.playerScaling() ? obj.maxValue() * pc : obj.maxValue());

        sendEventDialog(obj);
        data.setGoingObjective(obj);
        data.setDirty();

        NeoForge.EVENT_BUS.post(new ObjectiveStartedEvent(svlevel, audData, obj));
    }

    public static void failObjective(ServerLevel svlevel){
        AudienceUtil.addInterestRaw(svlevel, -50);
        AudienceUtil.sendDialog(Component.translatable("dialog.welcomeplayer.fail.0"));

        pickObjective(svlevel);
    }

    public static void setGoingEvent(ServerLevel svlevel, PlayerObjective obj){
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        AudienceData audData = AudienceUtil.getAudienceData(svlevel);
        data.setProgress(0);

        NeoForge.EVENT_BUS.post(new ObjectiveEndEvent(svlevel, audData, data.getGoingObjective()));

        sendEventDialog(obj);
        data.setGoingObjective(obj);
        data.setDirty();

        NeoForge.EVENT_BUS.post(new ObjectiveStartedEvent(svlevel, audData, obj));

        syncToClients(svlevel);
    }


    public static PlayerObjective getGoingObjective(ServerLevel svlevel){
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        return data.getGoingObjective();
    }

    public static boolean isCurrentObjective(ServerLevel svlevel, ResourceKey<PlayerObjective> event){
        return ObjectiveUtil.getGoingObjective(svlevel).is(svlevel, event);
    }

    public static void doTick(ServerLevel svlevel){
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        data.doTick(svlevel);
        syncToClients(svlevel);
    }

    public static int getProgress(ServerLevel svlevel){
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        return data.getProgress();
    }

    public static void addProgress(ServerLevel svlevel, int add){
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        data.addProgress(add);
        if(data.getProgress() >= data.getMaxProgress()){
            pickObjective(svlevel);
        }
        syncToClients(svlevel);
    }

    public static void setProgress(ServerLevel svlevel, int value){
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        data.setProgress(value);
        syncToClients(svlevel);
    }

    public static void syncToClients(ServerLevel svlevel){
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        PacketDistributor.sendToAllPlayers(SyncObjectiveDataPacket.create(data));
    }

    public static void sendEventDialog(PlayerObjective obj){
        List<Component> eventDialog = obj.dialog();
        for(Component comp : eventDialog){
            PacketDistributor.sendToAllPlayers(ServerShowHostMessagePacket.create(comp));
        }
    }

    public static List<Holder.Reference<PlayerObjective>> getAllObjectivesAsReference(ServerLevel svlevel){
        return svlevel.registryAccess().registryOrThrow(DatapackRegistry.PLAYER_OBJECTIVES).holders().toList();
    }
}
