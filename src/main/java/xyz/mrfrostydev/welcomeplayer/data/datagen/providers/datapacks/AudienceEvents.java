package xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.AudienceMood;
import xyz.mrfrostydev.welcomeplayer.data.AudiencePhase;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class AudienceEvents {

    private static final List<EventInfo> EVENTS = new ArrayList<>();

    public static final AudienceEventType EMPTY = new AudienceEventType("empty");
    public static final AudienceEventType EXPIRED_GOODS = new AudienceEventType("expired_goods");

    // Expired Goods
    public static final ResourceKey<AudienceEvent> LIKED_SAD_BROKEN_FEED = registerEntry("liked_sad_expired_goods", createDialog(
            "dialog.welcomeplayer.fleshlords_mock.0",
            "dialog.welcomeplayer.fleshlords_mock.1"
    ));

    public static List<Component> createDialog(String... comp){
        LinkedList<Component> compList = new LinkedList<>();
        for(String translation : comp){
            compList.add(Component.translatable(translation));
        }
        return compList;
    }

    public static ResourceKey<AudienceEvent> registerEntry(String name, List<Component> dialog){
        ResourceKey<AudienceEvent> event = ResourceKey.create(DatapackRegistry.AUDIENCE_EVENTS, ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, name));
        EVENTS.add(getEventInfoByString(name, event, dialog));
        return event;
    }

    private static EventInfo getEventInfoByString(String name, ResourceKey<AudienceEvent> event, List<Component> dialog){
        String[] values = name.split("_");

        AudiencePhase phase = switch(values[0]){
            case "thrilled" -> AudiencePhase.THRILLED;
            case "interested" -> AudiencePhase.INTERESTED;
            case "neutral" -> AudiencePhase.NEUTRAL;
            case "bored" -> AudiencePhase.BORED;
            case "furious" -> AudiencePhase.FURIOUS;
            default -> throw new IllegalArgumentException("Invalid phase name was entered for: " + name);
        };

        AudienceMood mood = switch(values[1]){
            case "happy" -> AudienceMood.HAPPY;
            case "neutral" -> AudienceMood.NEUTRAL;
            case "sad" -> AudienceMood.SAD;
            case "angry" -> AudienceMood.ANGRY;
            case "cruel" -> AudienceMood.CRUEL;
            default -> throw new IllegalArgumentException("Invalid mood name was entered for: " + name);
        };

        StringBuilder id = new StringBuilder(values[2]);
        for(int i=3; i<values.length; i++){
            id.append("_").append(values[i]);
        }

        return new EventInfo(event, id.toString(), phase, mood, dialog);
    }

    public static void bootstrap(BootstrapContext<AudienceEvent> context){
        for(EventInfo i : EVENTS){
            register(context, i.key, i.id, i.phase, i.mood, i.dialog);
        }
    }

    private static void register(BootstrapContext<AudienceEvent> context,
                                 ResourceKey<AudienceEvent> key, String id, AudiencePhase phase, AudienceMood mood, List<Component> list){
        context.register(key, new AudienceEvent(id, phase, mood, list));
    }

    public static class AudienceEventType {
        public static final Codec<AudienceEventType> CODEC = RecordCodecBuilder.create(inst ->
                inst.group(
                        Codec.STRING.fieldOf("id").forGetter(e -> e.id)
                ).apply(inst, AudienceEventType::new)
        );

        private String id;

        public AudienceEventType(String id){
            this.id = id;
        }

        public String id(){
            return id;
        }

        public boolean is(AudienceEvent event){
            return Objects.equals(event.ID(), id);
        }
    }


    private record EventInfo(ResourceKey<AudienceEvent> key, String id, AudiencePhase phase, AudienceMood mood, List<Component> dialog) {}
}
