package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.mrfrostydev.welcomeplayer.data.*;
import xyz.mrfrostydev.welcomeplayer.events.AudienceEventEndEvent;
import xyz.mrfrostydev.welcomeplayer.events.AudienceEventStartedEvent;
import xyz.mrfrostydev.welcomeplayer.network.ServerShowHostMessagePacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AudienceEventUtil {
    private static final Random RANDOM = new Random();

    /**
     * Pick and set an event from the {@link AudienceEventManager}. Selected event is based on the {@link AudiencePhase} and {@link AudienceMood}.
     * If the manager doesn't have enough events for that mood and phase, default to a NOTHING event.
     */
    public static void pickEvent(ServerLevel svlevel){
        AudienceData data = AudienceUtil.getAudienceData(svlevel);
        AudienceEventManager manager = data.getEventManager();
        AudienceMood mood = data.getMood();
        AudiencePhase phase = data.getPhase();
        AudienceEvent previousEvent = manager.getGoingEvent();

        NeoForge.EVENT_BUS.post(new AudienceEventEndEvent(svlevel, data, previousEvent));

        // Since the phase is the key and all events of that phase are stored in it,
        // we do not need to check if the event is the right phase.
        List<AudienceEvent> allEvents = manager.getEventMap().getOrDefault(phase, new ArrayList<>());
        List<AudienceEvent> events = new ArrayList<>();
        for(AudienceEvent e : allEvents){
            if(mood.equals(e.mood())){
                events.add(e);
            }
        }

        AudienceEvent event = AudienceEvent.NOTHING;
        if(!events.isEmpty()){
            int rnd = RANDOM.nextInt(events.size());
            event = events.get(rnd);
            while(event.is(previousEvent) && events.size() > 1){
                rnd = RANDOM.nextInt(events.size());
                event = events.get(rnd);
            }
        }

        sendEventDialog(event);
        manager.setGoingEvent(event);
        data.setDirty();

        NeoForge.EVENT_BUS.post(new AudienceEventStartedEvent(svlevel, data, event));
        AudienceUtil.syncToClients(svlevel);
    }

    public static void setGoingEvent(ServerLevel svlevel, AudienceEvent event){
        AudienceData data = AudienceUtil.getAudienceData(svlevel);
        AudienceEventManager manager = data.getEventManager();

        NeoForge.EVENT_BUS.post(new AudienceEventEndEvent(svlevel, data, manager.getGoingEvent()));

        sendEventDialog(event);
        manager.setGoingEvent(event);
        data.setDirty();

        NeoForge.EVENT_BUS.post(new AudienceEventStartedEvent(svlevel, data, event));
        AudienceUtil.syncToClients(svlevel);
    }

    public static AudienceEvent getGoingEvent(ServerLevel svlevel){
        AudienceData data = AudienceUtil.getAudienceData(svlevel);
        AudienceEventManager manager = data.getEventManager();
        return manager.getGoingEvent();
    }

    public static void sendEventDialog(AudienceEvent event){
        if(event.is(AudienceEvent.NOTHING)) return;

        List<Component> eventDialog = event.dialog();
        for(Component comp : eventDialog){
            PacketDistributor.sendToAllPlayers(ServerShowHostMessagePacket.create(comp));
        }
    }

}
