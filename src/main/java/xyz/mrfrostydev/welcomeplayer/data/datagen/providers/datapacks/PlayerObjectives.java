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

    // |------------------------------------------------------------|
    // |--------------------------Furious---------------------------|
    // |------------------------------------------------------------|

    public static final ResourceKey<PlayerObjective> FORCED_RESUPPLY = registerEntry("furious_forced_resupply", 30, true, createDialog(
            "dialog.welcomeplayer.objective.forced_resupply.0",
            "dialog.welcomeplayer.objective.forced_resupply.1",
            "dialog.welcomeplayer.objective.forced_resupply.2",
            "dialog.welcomeplayer.objective.forced_resupply.3"
    ));

    public static final ResourceKey<PlayerObjective> CRUEL_SUFFERING = registerEntry("furious_cruel_suffering", 200, true, createDialog(
            "dialog.welcomeplayer.objective.cruel_suffering.0",
            "dialog.welcomeplayer.objective.cruel_suffering.1",
            "dialog.welcomeplayer.objective.cruel_suffering.2",
            "dialog.welcomeplayer.objective.cruel_suffering.3"
    ));

    public static final ResourceKey<PlayerObjective> ENDLESS_VIOLENCE = registerEntry("furious_endless_violence", 80, true, createDialog(
            "dialog.welcomeplayer.objective.endless_violence.0",
            "dialog.welcomeplayer.objective.endless_violence.1",
            "dialog.welcomeplayer.objective.endless_violence.2"
    ));

    // |------------------------------------------------------------|
    // |---------------------------Bored----------------------------|
    // |------------------------------------------------------------|

    public static final ResourceKey<PlayerObjective> DAY_LABOUR = registerEntry("bored_day_labour", 6, true, createDialog(
            "dialog.welcomeplayer.objective.day_labour.0",
            "dialog.welcomeplayer.objective.day_labour.1",
            "dialog.welcomeplayer.objective.day_labour.2",
            "dialog.welcomeplayer.objective.day_labour.3"
    ));

    public static final ResourceKey<PlayerObjective> METAL_ROSES = registerEntry("bored_metal_roses", 2, false, createDialog(
            "dialog.welcomeplayer.objective.metal_roses.0",
            "dialog.welcomeplayer.objective.metal_roses.1",
            "dialog.welcomeplayer.objective.metal_roses.2",
            "dialog.welcomeplayer.objective.metal_roses.3"
    ));

    public static final ResourceKey<PlayerObjective> DOG_PERSON = registerEntry("bored_dog_person", 1, false, createDialog(
            "dialog.welcomeplayer.objective.dog_person.0",
            "dialog.welcomeplayer.objective.dog_person.1",
            "dialog.welcomeplayer.objective.dog_person.2",
            "dialog.welcomeplayer.objective.dog_person.3"
    ));

    public static final ResourceKey<PlayerObjective> CAT_PERSON = registerEntry("bored_cat_person", 1, false, createDialog(
            "dialog.welcomeplayer.objective.cat_person.0",
            "dialog.welcomeplayer.objective.cat_person.1",
            "dialog.welcomeplayer.objective.cat_person.2",
            "dialog.welcomeplayer.objective.cat_person.3"
    ));

    public static final ResourceKey<PlayerObjective> SHORT_FUSE = registerEntry("bored_short_fuse", 10, true, createDialog(
            "dialog.welcomeplayer.objective.short_fuse.0",
            "dialog.welcomeplayer.objective.short_fuse.1",
            "dialog.welcomeplayer.objective.short_fuse.2"
    ));

    public static final ResourceKey<PlayerObjective> DUCK_HUNT = registerEntry("bored_duck_hunt", 12, true, createDialog(
            "dialog.welcomeplayer.objective.duck_hunt.0",
            "dialog.welcomeplayer.objective.duck_hunt.1",
            "dialog.welcomeplayer.objective.duck_hunt.2",
            "dialog.welcomeplayer.objective.duck_hunt.3"
    ));

    public static final ResourceKey<PlayerObjective> PAIN = registerEntry("bored_pain", 80, true, createDialog(
            "dialog.welcomeplayer.objective.pain.0",
            "dialog.welcomeplayer.objective.pain.1",
            "dialog.welcomeplayer.objective.pain.2",
            "dialog.welcomeplayer.objective.pain.3"
    ));

    // |------------------------------------------------------------|
    // |--------------------------Neutral---------------------------|
    // |------------------------------------------------------------|

    public static final ResourceKey<PlayerObjective> THE_SHEPHERD = registerEntry("neutral_the_shepherd", 10, true, createDialog(
            "dialog.welcomeplayer.objective.the_shepherd.0",
            "dialog.welcomeplayer.objective.the_shepherd.1",
            "dialog.welcomeplayer.objective.the_shepherd.2",
            "dialog.welcomeplayer.objective.the_shepherd.3",
            "dialog.welcomeplayer.objective.the_shepherd.4"
    ));

    public static final ResourceKey<PlayerObjective> PEST_CONTROL = registerEntry("neutral_pest_control", 10, true, createDialog(
            "dialog.welcomeplayer.objective.pest_control.0",
            "dialog.welcomeplayer.objective.pest_control.1",
            "dialog.welcomeplayer.objective.pest_control.2",
            "dialog.welcomeplayer.objective.pest_control.3"
    ));

    public static final ResourceKey<PlayerObjective> THE_BUTCHER = registerEntry("neutral_the_butcher", 18, true, createDialog(
            "dialog.welcomeplayer.objective.the_butcher.0",
            "dialog.welcomeplayer.objective.the_butcher.1",
            "dialog.welcomeplayer.objective.the_butcher.2"
    ));

    public static final ResourceKey<PlayerObjective> EXPLORER = registerEntry("neutral_explorer", 1, false, createDialog(
            "dialog.welcomeplayer.objective.explorer.0",
            "dialog.welcomeplayer.objective.explorer.1",
            "dialog.welcomeplayer.objective.explorer.2",
            "dialog.welcomeplayer.objective.explorer.3"
    ));

    public static final ResourceKey<PlayerObjective> DEMOLITION = registerEntry("neutral_demolition", 12, true, createDialog(
            "dialog.welcomeplayer.objective.demolition.0",
            "dialog.welcomeplayer.objective.demolition.1",
            "dialog.welcomeplayer.objective.demolition.2",
            "dialog.welcomeplayer.objective.demolition.3"
    ));

    public static final ResourceKey<PlayerObjective> ZOMBIE_KILLER = registerEntry("neutral_zombie_killer", 10, true, createDialog(
            "dialog.welcomeplayer.objective.zombie_killer.0",
            "dialog.welcomeplayer.objective.zombie_killer.1",
            "dialog.welcomeplayer.objective.zombie_killer.2"
    ));

    public static final ResourceKey<PlayerObjective> COAL_MINER = registerEntry("neutral_coal_miner", 24, true, createDialog(
            "dialog.welcomeplayer.objective.coal_miner.0",
            "dialog.welcomeplayer.objective.coal_miner.1",
            "dialog.welcomeplayer.objective.coal_miner.2",
            "dialog.welcomeplayer.objective.coal_miner.3"
    ));

    public static final ResourceKey<PlayerObjective> FARMER = registerEntry("neutral_farmer", 30, true, createDialog(
            "dialog.welcomeplayer.objective.farmer.0",
            "dialog.welcomeplayer.objective.farmer.1",
            "dialog.welcomeplayer.objective.farmer.2",
            "dialog.welcomeplayer.objective.farmer.3"
    ));

    public static final ResourceKey<PlayerObjective> RATIONING = registerEntry("neutral_rationing", 50, true, createDialog(
            "dialog.welcomeplayer.objective.rationing.0",
            "dialog.welcomeplayer.objective.rationing.1",
            "dialog.welcomeplayer.objective.rationing.2",
            "dialog.welcomeplayer.objective.rationing.3"
    ));


    // |------------------------------------------------------------|
    // |-------------------------Interested-------------------------|
    // |------------------------------------------------------------|

    public static final ResourceKey<PlayerObjective> MONSTER_HUNTER = registerEntry("interested_monster_hunter", 25, true, createDialog(
            "dialog.welcomeplayer.objective.monster_hunter.0",
            "dialog.welcomeplayer.objective.monster_hunter.1",
            "dialog.welcomeplayer.objective.monster_hunter.2",
            "dialog.welcomeplayer.objective.monster_hunter.3"
    ));

    public static final ResourceKey<PlayerObjective> SET_BATTLE = registerEntry("interested_set_battle", 6, true, createDialog(
            "dialog.welcomeplayer.objective.set_battle.0",
            "dialog.welcomeplayer.objective.set_battle.1",
            "dialog.welcomeplayer.objective.set_battle.2",
            "dialog.welcomeplayer.objective.set_battle.3",
            "dialog.welcomeplayer.objective.set_battle.4"
    ));

    public static final ResourceKey<PlayerObjective> SURVEYOR = registerEntry("interested_surveyor", 2, true, createDialog(
            "dialog.welcomeplayer.objective.surveyor.0",
            "dialog.welcomeplayer.objective.surveyor.1",
            "dialog.welcomeplayer.objective.surveyor.2",
            "dialog.welcomeplayer.objective.surveyor.3",
            "dialog.welcomeplayer.objective.surveyor.4"
    ));

    public static final ResourceKey<PlayerObjective> PURE_VIOLENCE = registerEntry("interested_pure_violence", 50, true, createDialog(
            "dialog.welcomeplayer.objective.pure_violence.0",
            "dialog.welcomeplayer.objective.pure_violence.1",
            "dialog.welcomeplayer.objective.pure_violence.2"
    ));

    public static final ResourceKey<PlayerObjective> RICH_DISPLAY = registerEntry("interested_rich_display", 16, true, createDialog(
            "dialog.welcomeplayer.objective.rich_display.0",
            "dialog.welcomeplayer.objective.rich_display.1",
            "dialog.welcomeplayer.objective.rich_display.2",
            "dialog.welcomeplayer.objective.rich_display.3",
            "dialog.welcomeplayer.objective.rich_display.4"
    ));

    public static final ResourceKey<PlayerObjective> HUMAN_CHARGER = registerEntry("interested_human_charger", 16, true, createDialog(
            "dialog.welcomeplayer.objective.human_charger.0",
            "dialog.welcomeplayer.objective.human_charger.1",
            "dialog.welcomeplayer.objective.human_charger.2"
    ));

    public static final ResourceKey<PlayerObjective> DESTRUCTION = registerEntry("interested_destruction", 15, true, createDialog(
            "dialog.welcomeplayer.objective.destruction.0",
            "dialog.welcomeplayer.objective.destruction.1",
            "dialog.welcomeplayer.objective.destruction.2"
    ));

    public static final ResourceKey<PlayerObjective> CARNIFEROUS_CROWD = registerEntry("interested_carniferous_crowd", 100, true, createDialog(
            "dialog.welcomeplayer.objective.carniferous_crowd.0",
            "dialog.welcomeplayer.objective.carniferous_crowd.1",
            "dialog.welcomeplayer.objective.carniferous_crowd.2",
            "dialog.welcomeplayer.objective.carniferous_crowd.3"
    ));

    public static final ResourceKey<PlayerObjective> DISCHARGE = registerEntry("interested_discharge", 2, true, createDialog(
            "dialog.welcomeplayer.objective.discharge.0",
            "dialog.welcomeplayer.objective.discharge.1",
            "dialog.welcomeplayer.objective.discharge.2",
            "dialog.welcomeplayer.objective.discharge.3"
    ));

    // |------------------------------------------------------------|
    // |-------------------------Thrilled---------------------------|
    // |------------------------------------------------------------|

    public static final ResourceKey<PlayerObjective> SUFFERING = registerEntry("thrilled_suffering", 200, true, createDialog(
            "dialog.welcomeplayer.objective.suffering.0",
            "dialog.welcomeplayer.objective.suffering.1",
            "dialog.welcomeplayer.objective.suffering.2",
            "dialog.welcomeplayer.objective.suffering.3"
    ));

    public static final ResourceKey<PlayerObjective> GOLIATH = registerEntry("thrilled_goliath", 1, false, createDialog(
            "dialog.welcomeplayer.objective.goliath.0",
            "dialog.welcomeplayer.objective.goliath.1",
            "dialog.welcomeplayer.objective.goliath.2",
            "dialog.welcomeplayer.objective.goliath.3"
    ));

    public static final ResourceKey<PlayerObjective> NIGHT_KILLER = registerEntry("thrilled_night_killer", 1, false, createDialog(
            "dialog.welcomeplayer.objective.night_killer.0",
            "dialog.welcomeplayer.objective.night_killer.1",
            "dialog.welcomeplayer.objective.night_killer.2",
            "dialog.welcomeplayer.objective.night_killer.3"
    ));

    public static final ResourceKey<PlayerObjective> WONDER_EGGS = registerEntry("thrilled_wonder_eggs", 10, true, createDialog(
            "dialog.welcomeplayer.objective.wonder_eggs.0",
            "dialog.welcomeplayer.objective.wonder_eggs.1",
            "dialog.welcomeplayer.objective.wonder_eggs.2",
            "dialog.welcomeplayer.objective.wonder_eggs.3",
            "dialog.welcomeplayer.objective.wonder_eggs.4"
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
