package xyz.mrfrostydev.welcomeplayer.events;

import net.minecraft.world.level.Level;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;

public class AudienceEventEndEvent extends AudienceModEvent {
    private final AudienceEvent endedEvent;

    public AudienceEventEndEvent(Level level, AudienceData audData, AudienceEvent endedEvent) {
        super(level, audData);
        this.endedEvent = endedEvent;
    }

    /**
     * Return the {@link AudienceEvent} that just ended.
     */
    public AudienceEvent getEndedEvent() {
        return endedEvent;
    }
}
