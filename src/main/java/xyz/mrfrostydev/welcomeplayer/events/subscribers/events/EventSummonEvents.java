package xyz.mrfrostydev.welcomeplayer.events.subscribers.events;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents;
import xyz.mrfrostydev.welcomeplayer.events.AudienceEventStartedEvent;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class EventSummonEvents {

    @SubscribeEvent
    public static void onEventStart(AudienceEventStartedEvent event){
        if(!(event.getLevel() instanceof ServerLevel svlevel))return;
        AudienceEvent startedEvent = event.getStartedEvent();

        if(spawnForEvent(svlevel, startedEvent, AudienceEvents.ZOMBIE_HORDE, EntityType.ZOMBIE, 12)) return;
        if(spawnForEvent(svlevel, startedEvent, AudienceEvents.FAST_FOOD, EntityType.CHICKEN, 5)) return;
        if(spawnForEvent(svlevel, startedEvent, AudienceEvents.FAST_SERVICE, EntityRegistry.SERVICE_BOT.get(), 3)) return;
        if(spawnForEvent(svlevel, startedEvent, AudienceEvents.ROBOT_PATROL, EntityRegistry.HANDIBOT.get(), 2)) return;
        if(spawnForEvent(svlevel, startedEvent, AudienceEvents.BOMB_SQUAD, EntityType.CREEPER, 5)) return;
        if(spawnForEvent(svlevel, startedEvent, AudienceEvents.ROARING_THUNDER, EntityType.DROWNED, 8)) return;
        if(spawnForEventSinglePlayer(svlevel, startedEvent, AudienceEvents.BIG_BOSS, EntityRegistry.ERADICATOR.get(), 1)) return;

        if(startedEvent.is(AudienceEvents.NUCLEAR_SQUAD)){
            attemptSpawnWithModification(svlevel, EntityType.CREEPER, 4,
                    e -> {
                        if(e instanceof Creeper){
                            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(svlevel);
                            lightning.setDamage(0.0F);
                            e.thunderHit(svlevel, lightning);
                        }
                    }
            );
        }
    }

    // |--------------------------------------------------------------------------------|
    // |------------------------------------Methods-------------------------------------|
    // |--------------------------------------------------------------------------------|

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

    private static void attemptSpawnWithModification(ServerLevel svlevel, EntityType<?> entityType, int amount, Consumer<Entity> modification){
        List<ServerPlayer> players = svlevel.getPlayers(p -> !p.isSpectator());
        for(ServerPlayer player : players){
            int offset = 0;
            for(int i=0; i<amount; i++){
                BlockPos pos = findRandomSpawnPos(svlevel, player.getOnPos(), offset, 20);
                if(pos == null) pos = player.getOnPos();
                Entity entity = entityType.spawn(svlevel, pos, MobSpawnType.PATROL);
                modification.accept(entity);
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
            int j = targetPos.getX() + Mth.floor(Mth.cos(f) * 12.0F * (float)i) + svlevel.random.nextInt(6);
            int l = targetPos.getZ() + Mth.floor(Mth.sin(f) * 12.0F * (float)i) + svlevel.random.nextInt(6);
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
