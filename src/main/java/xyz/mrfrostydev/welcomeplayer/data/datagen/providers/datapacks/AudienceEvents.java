package xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.AudienceMood;
import xyz.mrfrostydev.welcomeplayer.data.AudiencePhase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class AudienceEvents {

    private static final List<EventInfo> EVENTS = new ArrayList<>();

    public static final AudienceEventType EMPTY = new AudienceEventType("empty");
    public static final AudienceEventType EXPIRED_GOODS = new AudienceEventType("expired_goods");
    public static final AudienceEventType THE_WATCHERS = new AudienceEventType("the_watchers");
    public static final AudienceEventType CHICKEN_RUN = new AudienceEventType("chicken_run");
    public static final AudienceEventType FLESHY_WARMTH = new AudienceEventType("fleshy_warmth");
    public static final AudienceEventType PLENTIFUL_HARVEST = new AudienceEventType("plentiful_harvest");
    public static final AudienceEventType CATTLE_FEVER = new AudienceEventType("cattle_fever");
    public static final AudienceEventType GLOOMY_FOG = new AudienceEventType("gloomy_fog");
    public static final AudienceEventType LONG_SORROW = new AudienceEventType("long_sorrow");
    public static final AudienceEventType SURGE = new AudienceEventType("surge");
    public static final AudienceEventType BURNING_SORROW = new AudienceEventType("burning_sorrow");
    public static final AudienceEventType LONG_NIGHT = new AudienceEventType("long_night");
    public static final AudienceEventType BROKEN_FEED = new AudienceEventType("broken_feed");
    public static final AudienceEventType TOTAL_MALFUNCTION = new AudienceEventType("total_malfunction");
    public static final AudienceEventType RAIDING_PARTY = new AudienceEventType("raiding_party");
    public static final AudienceEventType FLESH_GROWTH = new AudienceEventType("flesh_grown");
    public static final AudienceEventType DEATHLY_AIR = new AudienceEventType("deathly_air");
    public static final AudienceEventType ANIMAL_COMBUSTION = new AudienceEventType("animal_combustion");

    // Expired Goods
    public static final ResourceKey<AudienceEvent> LIKED_SAD_BROKEN_FEED = registerEntry("liked_sad_expired_goods", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> ADORED_SAD_BROKEN_FEED = registerEntry("adored_sad_expired_goods", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> DISLIKED_SAD_BROKEN_FEED = registerEntry("disliked_sad_expired_goods", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> HATED_NEUTRAL_FEED = registerEntry("liked_neutral_expired_goods", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));

    // Broken Feed
    public static final ResourceKey<AudienceEvent> LIKED_ANGRY_BROKEN_FEED = registerEntry("liked_angry_broken_feed", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> ADORED_NEUTRAL_BROKEN_FEED = registerEntry("adored_neutral_broken_feed", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> DISLIKED_NEUTRAL_BROKEN_FEED = registerEntry("disliked_neutral_broken_feed", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));

    // Animal Combustion
    public static final ResourceKey<AudienceEvent> LIKED_ANGRY_ANIMAL_COMBUSTION = registerEntry("liked_bored_animal_combustion", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> ADORED_NEUTRAL_ANIMAL_COMBUSTION = registerEntry("adored_bored_animal_combustion", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> DISLIKED_ANGRY_ANIMAL_COMBUSTION = registerEntry("disliked_angry_animal_combustion", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> HATED_ANGRY_ANIMAL_COMBUSTION = registerEntry("hated_angry_animal_combustion", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));

    // Total Malfunction
    public static final ResourceKey<AudienceEvent> HATED_ANGRY_TOTAL_MALFUNCTION = registerEntry("hated_angry_total_malfunction", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> DISLIKED_ANGRY_TOTAL_MALFUNCTION = registerEntry("disliked_angry_total_malfunction", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> ADORED_ANGRY_TOTAL_MALFUNCTION = registerEntry("adored_angry_total_malfunction", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));

    // Cattle Fever
    public static final ResourceKey<AudienceEvent> NEUTRAL_SAD_CATTLE_FEVER = registerEntry("neutral_sad_cattle_fever", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> DISLIKED_SAD_CATTLE_FEVER = registerEntry("disliked_sad_cattle_fever", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> HATED_NEUTRAL_CATTLE_FEVER = registerEntry("hated_neutral_cattle_fever", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> LIKED_SAD_CATTLE_FEVER = registerEntry("liked_sad_cattle_fever", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> ADORED_SAD_CATTLE_FEVER = registerEntry("adored_sad_cattle_fever", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));

    // Gloomy Fog
    public static final ResourceKey<AudienceEvent> NEUTRAL_SAD_GLOOMY_FOG = registerEntry("neutral_sad_gloomy_fog", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> DISLIKED_SAD_GLOOMY_FOG = registerEntry("disliked_sad_gloomy_fog", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> HATED_NEUTRAL_GLOOMY_FOG = registerEntry("hated_neutral_gloomy_fog", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> LIKED_SAD_GLOOMY_FOG = registerEntry("liked_sad_gloomy_fog", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> ADORED_SAD_GLOOMY_FOG = registerEntry("adored_sad_gloomy_fog", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));


    // Surge
    public static final ResourceKey<AudienceEvent> LIKED_BORED_SURGE = registerEntry("liked_bored_surge", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> ADORED_BORED_SURGE = registerEntry("adored_bored_surge", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> DISLIKED_NEUTRAL_SURGE = registerEntry("disliked_neutral_surge", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> HATED_NEUTRAL_SURGE = registerEntry("hated_neutral_surge", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));

    // Deathly Air
    public static final ResourceKey<AudienceEvent> DISLIKED_ANGRY_DEATHLY_AIR = registerEntry("disliked_angry_deathly_air", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> HATED_NEUTRAL_DEATHLY_AIR = registerEntry("hated_neutral_deathly_air", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));

    // Chicken Run
    public static final ResourceKey<AudienceEvent> NEUTRAL_HAPPY_CHICKEN_RUN = registerEntry("neutral_happy_chicken_run", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> LIKED_HAPPY_CHICKEN_RUN = registerEntry("liked_happy_chicken_run", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));

    // Plentiful Harvest
    public static final ResourceKey<AudienceEvent> ADORED_HAPPY_CHICKEN_RUN = registerEntry("adored_happy_plentiful_harvest", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));


    // The Watchers
    public static final ResourceKey<AudienceEvent> LIKED_NEUTRAL_THE_WATCHERS = registerEntry("liked_neutral_the_watchers", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));

    // Flesh Growth
    public static final ResourceKey<AudienceEvent> LIKED_NEUTRAL_FLESH_GROWTH = registerEntry("liked_neutral_flesh_growth", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));

    // Fleshy Warmth
    public static final ResourceKey<AudienceEvent> NEUTRAL_HAPPY_FLESHY_WARMTH = registerEntry("neutral_happy_fleshy_warmth", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));
    public static final ResourceKey<AudienceEvent> LIKED_HAPPY_FLESHY_WARMTH = registerEntry("liked_happy_fleshy_warmth", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));

    public static void bootstrap(BootstrapContext<AudienceEvent> context){
        for(EventInfo i : EVENTS){
            register(context, i.key, i.id, i.phase, i.mood, i.dialog);
        }
    }

    public static List<Component> createDialog(String... comp){
        LinkedList<Component> compList = new LinkedList<>();
        for(String translation : comp){
            compList.add(Component.translatable(translation));
        }
        return compList;
    }

    public static ResourceKey<AudienceEvent> registerEntry(String name, List<Component> dialog){
        ResourceKey<AudienceEvent> event = ResourceKey.create(DatapackRegistry.FLESH_LORD_EVENTS, ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, name));
        EVENTS.add(getEventInfoByString(name, event, dialog));
        return event;
    }

    private static EventInfo getEventInfoByString(String name, ResourceKey<AudienceEvent> event, List<Component> dialog){
        String[] values = name.split("_");

        AudiencePhase phase = switch(values[0]){
            case "adored" -> AudiencePhase.ADORED;
            case "liked" -> AudiencePhase.LIKED;
            case "neutral" -> AudiencePhase.NEUTRAL;
            case "disliked" -> AudiencePhase.DISLIKED;
            case "hated" -> AudiencePhase.HATED;
            default -> throw new IllegalArgumentException("Invalid phase name was entered for: " + name);
        };

        AudienceMood mood = switch(values[1]){
            case "happy" -> AudienceMood.HAPPY;
            case "neutral" -> AudienceMood.NEUTRAL;
            case "bored" -> AudienceMood.BORED;
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
