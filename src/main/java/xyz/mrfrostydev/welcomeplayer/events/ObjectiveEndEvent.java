package xyz.mrfrostydev.welcomeplayer.events;

import net.minecraft.world.level.Level;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;

public class ObjectiveEndEvent extends AudienceModEvent {
    private final PlayerObjective endedObj;

    public ObjectiveEndEvent(Level level, AudienceData audData, PlayerObjective endedObj) {
        super(level, audData);
        this.endedObj = endedObj;
    }

    /**
     * Return the {@link PlayerObjective} that just ended.
     */
    public PlayerObjective getEndedEvent() {
        return endedObj;
    }
}
