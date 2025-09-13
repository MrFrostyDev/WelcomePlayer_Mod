package xyz.mrfrostydev.welcomeplayer.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.AudienceMood;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;

public class AudienceModEvent extends Event {
    private Level level;
    private AudienceData audData;

    public AudienceModEvent(Level level, AudienceData audData) {
        this.level = level;
        this.audData = audData;
    }

    public Level getLevel(){
        return level;
    }

    public AudienceData getFleshLordData(){
        return audData;
    }

    public int getGlobalFavour(){
        if(level instanceof ServerLevel){
            return AudienceUtil.getInterest((ServerLevel)level);
        }
        else return 0;
    }

    public AudienceMood getMood(){
        if(level instanceof ServerLevel){
            return AudienceUtil.getMood((ServerLevel)level);
        }
        else return AudienceMood.NEUTRAL;
    }
}
