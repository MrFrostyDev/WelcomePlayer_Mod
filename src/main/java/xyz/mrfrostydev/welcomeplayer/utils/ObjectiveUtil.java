package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.*;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.PlayerObjectives;
import xyz.mrfrostydev.welcomeplayer.events.ObjectiveEndEvent;
import xyz.mrfrostydev.welcomeplayer.events.ObjectiveStartedEvent;
import xyz.mrfrostydev.welcomeplayer.network.ServerShowHostMessagePacket;
import xyz.mrfrostydev.welcomeplayer.network.SyncObjectiveDataPacket;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ParticleRegistry;

import java.util.List;
import java.util.Random;

public class ObjectiveUtil {
    public static Random RANDOM = new Random();
    public static int OBJECTIVE_TIME_LIMIT = 72000;

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
        int rand = RANDOM.nextInt(objList.size());
        WelcomeplayerMain.LOGGER.debug("[Welcomeplayer] Random Index for Objectives: " + rand);
        PlayerObjective obj = objList.get(rand);
        setGoingObjective(svlevel, obj);
    }

    public static void setGoingObjective(ServerLevel svlevel, PlayerObjective obj){
        ObjectiveManagerData data = getObjectiveManager(svlevel);
        AudienceData audData = AudienceUtil.getAudienceData(svlevel);
        data.setProgress(0);
        data.setTime(OBJECTIVE_TIME_LIMIT);

        NeoForge.EVENT_BUS.post(new ObjectiveEndEvent(svlevel, audData, data.getGoingObjective()));

        int pc = svlevel.getPlayers(s -> !s.isSpectator()).size();
        data.setMaxProgress(
                obj.playerScaling()
                ? (int)(obj.maxValue() + (obj.maxValue() * 0.5 * (pc - 1)))
                : obj.maxValue()
        );

        sendEventDialog(obj);
        data.setGoingObjective(obj);
        data.setDirty();

        NeoForge.EVENT_BUS.post(new ObjectiveStartedEvent(svlevel, audData, obj));

        syncToClients(svlevel);
    }

    public static void failObjective(ServerLevel svlevel){
        AudienceUtil.addInterestRaw(svlevel, -50);
        AudienceUtil.sendDialog(Component.translatable("dialog.welcomeplayer.objective.fail.0"));

        pickObjective(svlevel);
    }

    public static void completeObjective(ServerLevel svlevel){
        AudiencePhase phase = AudienceUtil.getPhase(svlevel);
        AudienceUtil.addInterestRaw(svlevel, 100);
        AudienceUtil.sendDialog(Component.translatable("dialog.welcomeplayer.objective.success.0"));

        List<AudienceReward> rewardList = svlevel
                .registryAccess()
                .registryOrThrow(DatapackRegistry.AUDIENCE_REWARDS)
                .stream()
                .filter(r -> r.phase().is(phase))
                .toList();
        if(rewardList.size() <= 0){
            throw new RuntimeException("While picking a reward, no options were available. Datapack may not have enough enabled rewards for this phase.");
        }
        int rand = RANDOM.nextInt(rewardList.size());

        List<ServerPlayer> players = svlevel.getPlayers(p -> !p.isSpectator());
        for(ServerPlayer player : players){
            Vec3 playerPos = player.getPosition(1.0F).add(0, 1, 0);
            Vec3 playerDir = Vec3.directionFromRotation(player.getRotationVector()).scale(2.0);
            Vec3 placementPos = playerPos.add(playerDir);

            ItemEntity itemEntity = new ItemEntity(svlevel, placementPos.x, placementPos.y, placementPos.z, rewardList.get(rand).stack(), 0, 0, 0);
            svlevel.addFreshEntity(itemEntity);
            svlevel.sendParticles(ParticleRegistry.WARP_REWARD.get(), placementPos.x, placementPos.y + 0.3, placementPos.z, 1, 0, 0, 0, 0);
        }
        pickObjective(svlevel);
    }

    public static PlayerObjective getGoingObjective(Level level){
        if(level instanceof ServerLevel){
            ObjectiveManagerData data = getObjectiveManager((ServerLevel) level);
            return data.getGoingObjective();
        }
        else{
            return ClientObjectiveData.get().getGoingObjective();
        }
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
            completeObjective(svlevel);
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

    public static boolean compareStackWithObjective(Level level, PlayerObjective curObj, ItemStack stack){
        if(curObj.is(level, PlayerObjectives.DAY_LABOR) && stack.is(ItemRegistry.RAW_RETROSTEEL)) return true;
        if(curObj.is(level, PlayerObjectives.COAL_MINER) && (stack.is(Items.COAL) || stack.is(Items.CHARCOAL))) return true;
        if(curObj.is(level, PlayerObjectives.SHORT_FUSE) && stack.is(ItemRegistry.BATTERY)) return true;
        if(curObj.is(level, PlayerObjectives.HUMAN_CHARGER) && stack.is(ItemRegistry.BATTERY)) return true;
        if(curObj.is(level, PlayerObjectives.WONDER_EGGS) && stack.is(Items.GOLDEN_APPLE)) return true;
        if(curObj.is(level, PlayerObjectives.RICH_DISPLAY) && stack.is(Items.DIAMOND)) return true;
        if(curObj.is(level, PlayerObjectives.RATIONING) && stack.getComponents().has(DataComponents.FOOD)) return true;
        if(curObj.is(level, PlayerObjectives.CARNIFEROUS_CROWD) && stack.getComponents().has(DataComponents.FOOD)) return true;
        return false;
    }
}
