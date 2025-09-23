package xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks;

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

    public static final ResourceKey<PlayerObjective> BROKEN_BOXING = registerEntry("furious_broken_boxing", 8, true, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> PAINFUL_SAPPER = registerEntry("furious_painful_sapper", 4, true, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> DOG_PERSON = registerEntry("bored_dog_person", 1, false, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> CAT_PERSON = registerEntry("bored_cat_person", 1, false, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> SHORT_FUSE = registerEntry("bored_short_fuse", 10, true, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> DUCK_HUNT = registerEntry("bored_duck_hunt", 10, true, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> THE_BUTCHER = registerEntry("neutral_the_butcher", 20, true, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> EXPLORER = registerEntry("neutral_explorer", 1, false, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> DESTRUCTION = registerEntry("neutral_destruction", 12, true, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> ZOMBIE_KILLER = registerEntry("neutral_zombie_killer", 8, true, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));


    public static final ResourceKey<PlayerObjective> COAL_MINER = registerEntry("neutral_coal_miner", 30, true, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> HUMAN_CHARGER = registerEntry("neutral_human_charger", 10, true, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> MONSTER_HUNTER = registerEntry("interested_monster_hunter", 20, true, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> SURVEYOR = registerEntry("interested_surveyor", 2, true, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> PURE_VIOLENCE = registerEntry("interested_pure_violence", 50, true, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> GOLIATH = registerEntry("thrilled_goliath", 1, false, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));

    public static final ResourceKey<PlayerObjective> NIGHT_KILLER = registerEntry("thrilled_night_killer", 1, false, createDialog(
            "dialog.welcomeplayer.objective.0",
            "dialog.welcomeplayer.objective.1"
    ));


    public static void bootstrap(BootstrapContext<PlayerObjective> context){
        for(PlayerObjectives.ObjectiveInfo i : OBJECTIVES){
            context.register(i.key, new PlayerObjective(i.id, i.phase, i.maxValue, i.playerScaling, i.dialog));
        }
    }

    public static ResourceKey<PlayerObjective> registerEntry(String name, int maxValue, boolean playerScaling, List<Component> dialog){
        ResourceKey<PlayerObjective> event = ResourceKey.create(DatapackRegistry.PLAYER_OBJECTIVES, ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, name));
        if(maxValue <= 0){
            throw new RuntimeException("maxValue for a PlayerObjective cannot be less than 1");
        }
        OBJECTIVES.add(getEventInfoByString(name, event, maxValue, playerScaling, dialog));
        return event;
    }

    private static PlayerObjectives.ObjectiveInfo getEventInfoByString(String name, ResourceKey<PlayerObjective> event, int maxValue, boolean playerScaling, List<Component> dialog){
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

        return new PlayerObjectives.ObjectiveInfo(event, id.toString(), phase, maxValue,playerScaling, dialog);
    }

    public static List<Component> createDialog(String... comp){
        LinkedList<Component> compList = new LinkedList<>();
        for(String translation : comp){
            compList.add(Component.translatable(translation));
        }
        return compList;
    }

    private record ObjectiveInfo(ResourceKey<PlayerObjective> key, String id, AudiencePhase phase, int maxValue, boolean playerScaling, List<Component> dialog) {}
}
