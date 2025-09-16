package xyz.mrfrostydev.welcomeplayer.events;

import net.minecraft.world.level.Level;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;

public class ObjectiveStartedEvent extends AudienceModEvent {
    private final PlayerObjective startedObj;

    public ObjectiveStartedEvent(Level level, AudienceData audData, PlayerObjective startedObj) {
        super(level, audData);
        this.startedObj = startedObj;
    }

    /**
     * Return the {@link PlayerObjective} that just ended.
     */
    public PlayerObjective getStartedObjective() {
        return startedObj;
    }
}
