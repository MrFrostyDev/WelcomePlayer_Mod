package xyz.mrfrostydev.welcomeplayer.events;

import net.minecraft.world.level.Level;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;

public class AudienceEventStartedEvent extends AudienceModEvent {
    private final AudienceEvent startedEvent;

    public AudienceEventStartedEvent(Level level, AudienceData audData, AudienceEvent endedEvent) {
        super(level, audData);
        this.startedEvent = endedEvent;
    }

    /**
     * Return the {@link AudienceEvent} that just ended.
     */
    public AudienceEvent getStartedEvent() {
        return startedEvent;
    }
}
