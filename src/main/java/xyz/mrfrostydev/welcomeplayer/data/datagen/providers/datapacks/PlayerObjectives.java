package xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudiencePhase;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayerObjectives {

    private static final List<ObjectiveInfo> OBJECTIVES = new ArrayList<>();

    public static final ResourceKey<PlayerObjective> NEUTRAL_DUCK_HUNT = registerEntry("neutral_duck_hunt", 24000, createDialog(
            "dialog.welcomeplayer.fleshlords_mock.0",
            "dialog.welcomeplayer.fleshlords_mock.1"
    ));

    public static final ResourceKey<PlayerObjective> NEUTRAL_ZOMBIE_KILLER = registerEntry("neutral_zombie_killer", 48000, createDialog(
            "dialog.welcomeplayer.fleshlords_mock.0",
            "dialog.welcomeplayer.fleshlords_mock.1"
    ));

    public static void bootstrap(BootstrapContext<PlayerObjective> context){
        for(PlayerObjectives.ObjectiveInfo i : OBJECTIVES){
            context.register(i.key, new PlayerObjective(i.id, i.phase, i.duration, i.dialog));
        }
    }

    public static ResourceKey<PlayerObjective> registerEntry(String name, int duration, List<Component> dialog){
        ResourceKey<PlayerObjective> event = ResourceKey.create(DatapackRegistry.PLAYER_OBJECTIVES, ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, name));
        OBJECTIVES.add(getEventInfoByString(name, event, duration, dialog));
        return event;
    }

    private static PlayerObjectives.ObjectiveInfo getEventInfoByString(String name, ResourceKey<PlayerObjective> event, int duration, List<Component> dialog){
        String[] values = name.split("_");

        AudiencePhase phase = switch(values[0]){
            case "thrilled" -> AudiencePhase.THRILLED;
            case "interested" -> AudiencePhase.INTERESTED;
            case "neutral" -> AudiencePhase.NEUTRAL;
            case "bored" -> AudiencePhase.BORED;
            case "furious" -> AudiencePhase.FURIOUS;
            default -> throw new IllegalArgumentException("Invalid phase name was entered for: " + name);
        };

        StringBuilder id = new StringBuilder(values[1]);
        for(int i=2; i<values.length; i++){
            id.append("_").append(values[i]);
        }

        return new PlayerObjectives.ObjectiveInfo(event, id.toString(), phase, duration, dialog);
    }

    public static List<Component> createDialog(String... comp){
        LinkedList<Component> compList = new LinkedList<>();
        for(String translation : comp){
            compList.add(Component.translatable(translation));
        }
        return compList;
    }

    private record ObjectiveInfo(ResourceKey<PlayerObjective> key, String id, AudiencePhase phase, int duration, List<Component> dialog) {}
}
