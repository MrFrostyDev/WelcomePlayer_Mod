package xyz.mrfrostydev.welcomeplayer.data;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceEventUtil;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AudienceEventManager {
    public static final Codec<AudienceEventManager> CODEC = RecordCodecBuilder.create(
            inst -> inst.group(
                    Codec.pair(
                            AudiencePhase.CODEC.fieldOf("phase").codec(),
                            AudienceEvent.CODEC.listOf().fieldOf("events").codec()
                    ).listOf().xmap(
                            list -> list.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)),
                            map -> map.entrySet().stream().map(e -> Pair.of(e.getKey(), e.getValue())).collect(Collectors.toList())
                    ).fieldOf("eventMap").forGetter(AudienceEventManager::getEventMap),
                    AudienceEvent.CODEC.fieldOf("goingEvent").forGetter(AudienceEventManager::getGoingEvent)
            ).apply(inst, AudienceEventManager::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AudienceEventManager> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(
                    HashMap::new,
                    AudiencePhase.STREAM_CODEC,
                    AudienceEvent.STREAM_CODEC.apply(ByteBufCodecs.list(128)),
                    256
            ), AudienceEventManager::getEventMap,
            AudienceEvent.STREAM_CODEC, AudienceEventManager::getGoingEvent,
            AudienceEventManager::new
    );

    private Map<AudiencePhase, List<AudienceEvent>> eventMap;
    private AudienceEvent goingEvent;

    public AudienceEventManager(Map<AudiencePhase, List<AudienceEvent>> eventMap){
        this.eventMap = eventMap;
        this.goingEvent = AudienceEvent.NOTHING;
    }

    public AudienceEventManager(Map<AudiencePhase, List<AudienceEvent>> eventMap, AudienceEvent goingEvent){
        this.eventMap = eventMap;
        this.goingEvent = goingEvent;
    }

    public void setGoingEvent(AudienceEvent goingEvent) {
        this.goingEvent = goingEvent;
    }

    public AudienceEvent getGoingEvent() {
        return goingEvent;
    }

    public void setEventMap(Map<AudiencePhase, List<AudienceEvent>> eventMap){
        this.eventMap = eventMap;
    }

    public Map<AudiencePhase, List<AudienceEvent>> getEventMap() {
        return eventMap;
    }

    public static List<Holder.Reference<AudienceEvent>> getAllEventsAsReference(ServerLevel svlevel){
        return svlevel.registryAccess().registryOrThrow(DatapackRegistry.AUDIENCE_EVENTS).holders().toList();
    }
}
