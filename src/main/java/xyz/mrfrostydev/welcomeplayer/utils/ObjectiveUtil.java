package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import xyz.mrfrostydev.welcomeplayer.data.ObjectiveManagerData;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;

public class ObjectiveUtil {

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

    public static PlayerObjective setGoingObjective(ServerLevel svlevel){
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        svlevel.registryAccess().registryOrThrow(DatapackRegistry.PLAYER_OBJECTIVES).

        return data.getGoingObjective();
    }

    public static PlayerObjective getGoingObjective(ServerLevel svlevel){
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        return data.getGoingObjective();
    }

    public static int getProgress(ServerLevel svlevel){
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        return data.getProgress();
    }

    public static void addProgress(ServerLevel svlevel, int add){
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        data.addProgress(add);
    }
}
