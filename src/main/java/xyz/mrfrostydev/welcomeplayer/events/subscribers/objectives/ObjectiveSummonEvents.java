package xyz.mrfrostydev.welcomeplayer.events.subscribers.objectives;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.PlayerObjectives;
import xyz.mrfrostydev.welcomeplayer.events.AudienceEventStartedEvent;
import xyz.mrfrostydev.welcomeplayer.events.ObjectiveStartedEvent;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class ObjectiveSummonEvents {

    @SubscribeEvent
    public static void onObjectiveStart(ObjectiveStartedEvent event){
        if(!(event.getLevel() instanceof ServerLevel svlevel))return;
        PlayerObjective obj = event.getStartedObjective();

        if(spawnForObjective(svlevel, obj, PlayerObjectives.SET_BATTLE, EntityRegistry.HANDIBOT.get(), 2)) return;
        if(spawnForObjectiveSinglePlayer(svlevel, obj, PlayerObjectives.GOLIATH, EntityRegistry.ERADICATOR.get(), 1)) return;
    }

    @SubscribeEvent
    public static void onEventStart(AudienceEventStartedEvent event){
        if(!(event.getLevel() instanceof ServerLevel svlevel))return;
        AudienceEvent startedEvent = event.getStartedEvent();

        if(spawnForEvent(svlevel, startedEvent, AudienceEvents.ROBOT_PATROL, EntityRegistry.HANDIBOT.get(), 2)) return;
        if(spawnForEventSinglePlayer(svlevel, startedEvent, AudienceEvents.BIG_BOSS, EntityRegistry.ERADICATOR.get(), 1)) return;
    }





    // |--------------------------------------------------------------------------------|
    // |------------------------------------Methods-------------------------------------|
    // |--------------------------------------------------------------------------------|

    private static boolean spawnForObjective(ServerLevel svlevel, PlayerObjective startedObj, ResourceKey<PlayerObjective> compObj, EntityType<?> entityType, int amount){
        boolean isTrue = startedObj.is(svlevel, compObj);
        if(isTrue) attemptSpawn(svlevel, entityType, amount);
        return isTrue;
    }

    private static boolean spawnForObjectiveSinglePlayer(ServerLevel svlevel, PlayerObjective startedObj, ResourceKey<PlayerObjective> compObj, EntityType<?> entityType, int amount){
        boolean isTrue = startedObj.is(svlevel, compObj);
        if(isTrue) attemptSpawnSinglePlayer(svlevel, entityType, amount);
        return isTrue;
    }

    private static boolean spawnForEvent(ServerLevel svlevel, AudienceEvent startedEvent, AudienceEvents.AudienceEventType eventType, EntityType<?> entityType, int amount){
        boolean isTrue = startedEvent.is(eventType);
        if(isTrue) attemptSpawn(svlevel, entityType, amount);
        return isTrue;
    }

    private static boolean spawnForEventSinglePlayer(ServerLevel svlevel, AudienceEvent startedEvent, AudienceEvents.AudienceEventType eventType, EntityType<?> entityType, int amount){
        boolean isTrue = startedEvent.is(eventType);
        if(isTrue) attemptSpawnSinglePlayer(svlevel, entityType, amount);
        return isTrue;
    }

    private static void attemptSpawn(ServerLevel svlevel, EntityType<?> entityType, int amount){
        List<ServerPlayer> players = svlevel.getPlayers(p -> !p.isSpectator());
        for(ServerPlayer player : players){
            int offset = 0;
            for(int i=0; i<amount; i++){
                BlockPos pos = findRandomSpawnPos(svlevel, player.getOnPos(), offset, 20);
                if(pos == null) pos = player.getOnPos();
                entityType.spawn(svlevel, pos, MobSpawnType.PATROL);
                offset++;
            }
        }
    }

    private static void attemptSpawnSinglePlayer(ServerLevel svlevel, EntityType<?> entityType, int amount){
        List<ServerPlayer> players = svlevel.getPlayers(p -> !p.isSpectator());
        Collections.shuffle(players);
        ServerPlayer player = players.getFirst();
        for(int i=0; i<amount; i++){
            BlockPos pos = findRandomSpawnPos(svlevel, player.getOnPos(), 0, 20);
            if(pos == null) pos = player.getOnPos();
            entityType.spawn(svlevel, pos, MobSpawnType.PATROL);
        }
    }

    // based on Raid spawn pos logic
    @Nullable
    private static BlockPos findRandomSpawnPos(ServerLevel svlevel, BlockPos targetPos, int offsetMultiplier, int maxTry) {
        int i = offsetMultiplier == 0 ? 2 : 2 - offsetMultiplier;
        BlockPos.MutableBlockPos pPos = new BlockPos.MutableBlockPos();
        SpawnPlacementType spawnplacementtype = SpawnPlacements.getPlacementType(EntityType.RAVAGER);

        for (int i1 = 0; i1 < maxTry; i1++) {
            float f = svlevel.random.nextFloat() * (float) (Math.PI * 2);
            int j = targetPos.getX() + Mth.floor(Mth.cos(f) * 16.0F * (float)i) + svlevel.random.nextInt(5);
            int l = targetPos.getZ() + Mth.floor(Mth.sin(f) * 16.0F * (float)i) + svlevel.random.nextInt(5);
            int k = svlevel.getHeight(Heightmap.Types.WORLD_SURFACE, j, l);
            pPos.set(j, k, l);
            if (svlevel
                    .hasChunksAt(
                            pPos.getX() - 10,
                            pPos.getZ() - 10,
                            pPos.getX() + 10,
                            pPos.getZ() + 10
                    )
                    && svlevel.isPositionEntityTicking(pPos)
                    && (spawnplacementtype.isSpawnPositionOk(svlevel, pPos, EntityType.RAVAGER) || svlevel.getBlockState(pPos.below()).is(Blocks.SNOW)
                    && svlevel.getBlockState(pPos).isAir()
            )) {
                return pPos;
            }
        }
        return null;
    }
}
