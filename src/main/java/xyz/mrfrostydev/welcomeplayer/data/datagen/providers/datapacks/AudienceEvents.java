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
    public static final AudienceEventType COMMERCIAL_BREAK = new AudienceEventType("commercial_break");
    public static final AudienceEventType ZOMBIE_HORDE = new AudienceEventType("zombie_horde");
    public static final AudienceEventType ROBOT_PATROL = new AudienceEventType("robot_patrol");
    public static final AudienceEventType BIG_BOSS = new AudienceEventType("big_boss");
    public static final AudienceEventType FAST_SERVICE = new AudienceEventType("fast_service");
    public static final AudienceEventType SAD_RAIN = new AudienceEventType("sad_rain");
    public static final AudienceEventType TOXIC_RAIN = new AudienceEventType("toxic_rain");
    public static final AudienceEventType HOT_POTATO = new AudienceEventType("hot_potato");
    public static final AudienceEventType DESTRUCT_PROTOCOL = new AudienceEventType("destruct_protocol");
    public static final AudienceEventType RAIDING_PARTY = new AudienceEventType("raiding_party");
    public static final AudienceEventType FAST_FOOD = new AudienceEventType("fast_food");
    public static final AudienceEventType BOMB_SQUAD = new AudienceEventType("bomb_squad");
    public static final AudienceEventType NUCLEAR_SQUAD = new AudienceEventType("nuclear_squad");
    public static final AudienceEventType ROARING_THUNDER = new AudienceEventType("roaring_thunder");
    public static final AudienceEventType ELECTRIC_SOUL = new AudienceEventType("electric_soul");
    public static final AudienceEventType SPEED_SUBJECTS = new AudienceEventType("speed_subjects");
    public static final AudienceEventType WARP_MALFUNCTION = new AudienceEventType("warp_malfunction");
    public static final AudienceEventType BACKUP_SAFETY = new AudienceEventType("backup_safety");
    public static final AudienceEventType REPULSION_TECH = new AudienceEventType("repulsion_tech");
    public static final AudienceEventType PROPULSION_UPGRADE = new AudienceEventType("propulsion_upgrade");
    public static final AudienceEventType CONTRABAND = new AudienceEventType("contraband");

    // |------------------------------------------------------------------------|
    // |-------------------------------Contraband-------------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> BORED_ANGRY_CONTRABAND = registerEntry("bored_angry_contraband", createDialog(
            "dialog.welcomeplayer.event.contraband.bored.0",
            "dialog.welcomeplayer.event.contraband.bored.1",
            "dialog.welcomeplayer.event.contraband.bored.2"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_ANGRY_CONTRABAND = registerEntry("interested_angry_contraband", createDialog(
            "dialog.welcomeplayer.event.contraband.interested.0",
            "dialog.welcomeplayer.event.contraband.interested.1",
            "dialog.welcomeplayer.event.contraband.interested.2",
            "dialog.welcomeplayer.event.contraband.interested.3"
    ));

    // |------------------------------------------------------------------------|
    // |---------------------------Propulsion Upgrade---------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> NEUTRAL_HAPPY_PROPULSION_UPGRADE = registerEntry("neutral_happy_propulsion_upgrade", createDialog(
            "dialog.welcomeplayer.event.propulsion_upgrade.neutral.0",
            "dialog.welcomeplayer.event.propulsion_upgrade.neutral.1",
            "dialog.welcomeplayer.event.propulsion_upgrade.neutral.2"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_HAPPY_PROPULSION_UPGRADE = registerEntry("interested_happy_propulsion_upgrade", createDialog(
            "dialog.welcomeplayer.event.propulsion_upgrade.interested.0",
                "dialog.welcomeplayer.event.propulsion_upgrade.interested.1",
            "dialog.welcomeplayer.event.propulsion_upgrade.interested.2",
            "dialog.welcomeplayer.event.propulsion_upgrade.interested.3"
    ));
    public static final ResourceKey<AudienceEvent> THRILLED_HAPPY_PROPULSION_UPGRADE = registerEntry("thrilled_happy_propulsion_upgrade", createDialog(
            "dialog.welcomeplayer.event.propulsion_upgrade.thrilled.0",
            "dialog.welcomeplayer.event.propulsion_upgrade.thrilled.1",
            "dialog.welcomeplayer.event.propulsion_upgrade.thrilled.2",
            "dialog.welcomeplayer.event.propulsion_upgrade.thrilled.3"
    ));

    // |------------------------------------------------------------------------|
    // |---------------------------Repulsion Upgrade----------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> NEUTRAL_SAD_REPULSION_TECH = registerEntry("neutral_sad_repulsion_tech", createDialog(
            "dialog.welcomeplayer.event.repulsion_tech.neutral.0",
            "dialog.welcomeplayer.event.repulsion_tech.neutral.1",
            "dialog.welcomeplayer.event.repulsion_tech.neutral.2"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_SAD_REPULSION_TECH = registerEntry("interested_sad_repulsion_tech", createDialog(
            "dialog.welcomeplayer.event.repulsion_tech.interested.0",
            "dialog.welcomeplayer.event.repulsion_tech.interested.1",
            "dialog.welcomeplayer.event.repulsion_tech.interested.2",
            "dialog.welcomeplayer.event.repulsion_tech.interested.3"
    ));
    public static final ResourceKey<AudienceEvent> THRILLED_SAD_REPULSION_TECH = registerEntry("thrilled_sad_repulsion_tech", createDialog(
            "dialog.welcomeplayer.event.repulsion_tech.thrilled.0",
            "dialog.welcomeplayer.event.repulsion_tech.thrilled.1",
            "dialog.welcomeplayer.event.repulsion_tech.thrilled.2"
    ));

    // |------------------------------------------------------------------------|
    // |-----------------------------Backup Safety------------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> BORED_SAD_BACKUP_SAFETY = registerEntry("bored_sad_backup_safety", createDialog(
            "dialog.welcomeplayer.event.backup_safety.bored.0",
            "dialog.welcomeplayer.event.backup_safety.bored.1",
            "dialog.welcomeplayer.event.backup_safety.bored.2"
    ));
    public static final ResourceKey<AudienceEvent> NEUTRAL_SAD_BACKUP_SAFETY = registerEntry("neutral_sad_backup_safety", createDialog(
            "dialog.welcomeplayer.event.backup_safety.neutral.0",
            "dialog.welcomeplayer.event.backup_safety.neutral.1",
            "dialog.welcomeplayer.event.backup_safety.neutral.2"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_SAD_BACKUP_SAFETY = registerEntry("interested_sad_backup_safety", createDialog(
            "dialog.welcomeplayer.event.backup_safety.interested.0",
            "dialog.welcomeplayer.event.backup_safety.interested.1",
            "dialog.welcomeplayer.event.backup_safety.interested.2",
            "dialog.welcomeplayer.event.backup_safety.interested.3"
    ));
    public static final ResourceKey<AudienceEvent> THRILLED_SAD_BACKUP_SAFETY = registerEntry("thrilled_sad_backup_safety", createDialog(
            "dialog.welcomeplayer.event.backup_safety.thrilled.0",
            "dialog.welcomeplayer.event.backup_safety.thrilled.1",
            "dialog.welcomeplayer.event.backup_safety.thrilled.2"
    ));


    // |------------------------------------------------------------------------|
    // |----------------------------Warp Malfunction----------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> BORED_NEUTRAL_WARP_MALFUNCTION = registerEntry("bored_neutral_warp_malfunction", createDialog(
            "dialog.welcomeplayer.event.warp_malfunction.bored.0",
            "dialog.welcomeplayer.event.warp_malfunction.bored.1",
            "dialog.welcomeplayer.event.warp_malfunction.bored.2",
            "dialog.welcomeplayer.event.warp_malfunction.bored.3"
    ));
    public static final ResourceKey<AudienceEvent> NEUTRAL_NEUTRAL_WARP_MALFUNCTION = registerEntry("neutral_neutral_warp_malfunction", createDialog(
            "dialog.welcomeplayer.event.warp_malfunction.neutral.0",
            "dialog.welcomeplayer.event.warp_malfunction.neutral.1",
            "dialog.welcomeplayer.event.warp_malfunction.neutral.2",
            "dialog.welcomeplayer.event.warp_malfunction.neutral.3"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_NEUTRAL_WARP_MALFUNCTION = registerEntry("interested_neutral_warp_malfunction", createDialog(
            "dialog.welcomeplayer.event.warp_malfunction.interested.0",
            "dialog.welcomeplayer.event.warp_malfunction.interested.1",
            "dialog.welcomeplayer.event.warp_malfunction.interested.2",
            "dialog.welcomeplayer.event.warp_malfunction.interested.3"
    ));

    // |------------------------------------------------------------------------|
    // |-----------------------------Speed Subjects-----------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> NEUTRAL_HAPPY_SPEED_SUBJECTS = registerEntry("neutral_happy_speed_subjects", createDialog(
            "dialog.welcomeplayer.event.speed_subjects.neutral.0",
            "dialog.welcomeplayer.event.speed_subjects.neutral.1",
            "dialog.welcomeplayer.event.speed_subjects.neutral.2"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_HAPPY_SPEED_SUBJECTS = registerEntry("interested_happy_speed_subjects", createDialog(
            "dialog.welcomeplayer.event.speed_subjects.interested.0",
            "dialog.welcomeplayer.event.speed_subjects.interested.1",
            "dialog.welcomeplayer.event.speed_subjects.interested.2"
    ));
    public static final ResourceKey<AudienceEvent> THRILLED_HAPPY_SPEED_SUBJECTS = registerEntry("thrilled_happy_speed_subjects", createDialog(
            "dialog.welcomeplayer.event.speed_subjects.thrilled.0",
            "dialog.welcomeplayer.event.speed_subjects.thrilled.1",
            "dialog.welcomeplayer.event.speed_subjects.thrilled.2"
    ));

    // |------------------------------------------------------------------------|
    // |------------------------------Electric Soul-----------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> BORED_NEUTRAL_ELECTRIC_SOUL = registerEntry("bored_neutral_electric_soul", createDialog(
            "dialog.welcomeplayer.event.electric_soul.bored.0",
            "dialog.welcomeplayer.event.electric_soul.bored.1",
            "dialog.welcomeplayer.event.electric_soul.bored.2",
            "dialog.welcomeplayer.event.electric_soul.bored.3"

    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_NEUTRAL_ELECTRIC_SOUL = registerEntry("interested_neutral_electric_soul", createDialog(
            "dialog.welcomeplayer.event.electric_soul.interested.0",
            "dialog.welcomeplayer.event.electric_soul.interested.1",
            "dialog.welcomeplayer.event.electric_soul.interested.2"
    ));

    // |------------------------------------------------------------------------|
    // |----------------------------Commercial Break----------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> BORED_NEUTRAL_COMMERCIAL_BREAK = registerEntry("bored_neutral_commercial_break", createDialog(
            "dialog.welcomeplayer.event.commercial_break.bored.0",
            "dialog.welcomeplayer.event.commercial_break.bored.1",
            "dialog.welcomeplayer.event.commercial_break.bored.2",
            "dialog.welcomeplayer.event.commercial_break.bored.3"

    ));
    public static final ResourceKey<AudienceEvent> NEUTRAL_NEUTRAL_COMMERCIAL_BREAK = registerEntry("neutral_neutral_commercial_break", createDialog(
            "dialog.welcomeplayer.event.commercial_break.neutral.0",
            "dialog.welcomeplayer.event.commercial_break.neutral.1"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_NEUTRAL_COMMERCIAL_BREAK = registerEntry("interested_neutral_commercial_break", createDialog(
            "dialog.welcomeplayer.event.commercial_break.interested.0",
            "dialog.welcomeplayer.event.commercial_break.interested.1"
    ));

    // |------------------------------------------------------------------------|
    // |------------------------------Robot Patrol------------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> BORED_NEUTRAL_ROBOT_PATROL = registerEntry("bored_neutral_robot_patrol", createDialog(
            "dialog.welcomeplayer.event.robot_patrol.bored.0",
            "dialog.welcomeplayer.event.robot_patrol.bored.1"
    ));
    public static final ResourceKey<AudienceEvent> NEUTRAL_NEUTRAL_ROBOT_PATROL = registerEntry("neutral_neutral_robot_patrol", createDialog(
            "dialog.welcomeplayer.event.robot_patrol.neutral.0",
            "dialog.welcomeplayer.event.robot_patrol.neutral.1",
            "dialog.welcomeplayer.event.robot_patrol.neutral.2"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_NEUTRAL_ROBOT_PATROL = registerEntry("interested_neutral_robot_patrol", createDialog(
            "dialog.welcomeplayer.event.robot_patrol.interested.0",
            "dialog.welcomeplayer.event.robot_patrol.interested.1",
            "dialog.welcomeplayer.event.robot_patrol.interested.2"
    ));

    // |------------------------------------------------------------------------|
    // |--------------------------------Fast Food-------------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> NEUTRAL_HAPPY_FAST_FOOD = registerEntry("neutral_happy_fast_food", createDialog(
            "dialog.welcomeplayer.event.fast_food.neutral.0",
            "dialog.welcomeplayer.event.fast_food.neutral.1"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_HAPPY_FAST_FOOD = registerEntry("interested_happy_fast_food", createDialog(
            "dialog.welcomeplayer.event.fast_food.interested.0"
    ));

    // |------------------------------------------------------------------------|
    // |--------------------------------Sad Rain--------------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> BORED_SAD_SAD_RAIN = registerEntry("bored_sad_sad_rain", createDialog(
            "dialog.welcomeplayer.event.sad_rain.bored.0",
            "dialog.welcomeplayer.event.sad_rain.bored.1",
            "dialog.welcomeplayer.event.sad_rain.bored.2"
    ));
    public static final ResourceKey<AudienceEvent> NEUTRAL_SAD_SAD_RAIN = registerEntry("neutral_sad_sad_rain", createDialog(
            "dialog.welcomeplayer.event.sad_rain.neutral.0",
            "dialog.welcomeplayer.event.sad_rain.neutral.1",
            "dialog.welcomeplayer.event.sad_rain.neutral.2"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_SAD_SAD_RAIN = registerEntry("interested_sad_sad_rain", createDialog(
            "dialog.welcomeplayer.event.sad_rain.interested.0",
            "dialog.welcomeplayer.event.sad_rain.interested.1",
            "dialog.welcomeplayer.event.sad_rain.interested.2"
    ));

    // |------------------------------------------------------------------------|
    // |-------------------------------Toxic Rain-------------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> BORED_SAD_TOXIC_RAIN = registerEntry("bored_sad_toxic_rain", createDialog(
            "dialog.welcomeplayer.event.toxic_rain.bored.0",
            "dialog.welcomeplayer.event.toxic_rain.bored.1",
            "dialog.welcomeplayer.event.toxic_rain.bored.2"
    ));
    public static final ResourceKey<AudienceEvent> THRILLED_SAD_TOXIC_RAIN = registerEntry("thrilled_sad_toxic_rain", createDialog(
            "dialog.welcomeplayer.event.toxic_rain.thrilled.0",
            "dialog.welcomeplayer.event.toxic_rain.thrilled.1",
            "dialog.welcomeplayer.event.toxic_rain.thrilled.2"
    ));

    // |------------------------------------------------------------------------|
    // |--------------------------------Big Boss--------------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> FURIOUS_CRUEL_BIG_BOSS = registerEntry("furious_cruel_big_boss", createDialog(
            "dialog.welcomeplayer.event.big_boss.furious.0",
            "dialog.welcomeplayer.event.big_boss.furious.1",
            "dialog.welcomeplayer.event.big_boss.furious.2"
    ));
    public static final ResourceKey<AudienceEvent> THRILLED_CRUEL_BIG_BOSS = registerEntry("thrilled_cruel_big_boss", createDialog(
            "dialog.welcomeplayer.event.big_boss.thrilled.0",
            "dialog.welcomeplayer.event.big_boss.thrilled.1",
            "dialog.welcomeplayer.event.big_boss.thrilled.2",
            "dialog.welcomeplayer.event.big_boss.thrilled.3",
            "dialog.welcomeplayer.event.big_boss.thrilled.4"
    ));

    // |------------------------------------------------------------------------|
    // |-------------------------------Hot Potato-------------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> INTERESTED_HAPPY_HOT_POTATO = registerEntry("interested_happy_hot_potato", createDialog(
            "dialog.welcomeplayer.event.hot_potato.interested.0",
            "dialog.welcomeplayer.event.hot_potato.interested.1",
            "dialog.welcomeplayer.event.hot_potato.interested.2"
    ));
    public static final ResourceKey<AudienceEvent> THRILLED_HAPPY_HOT_POTATO = registerEntry("thrilled_happy_hot_potato", createDialog(
            "dialog.welcomeplayer.event.hot_potato.thrilled.0",
            "dialog.welcomeplayer.event.hot_potato.thrilled.1"
    ));

    // |------------------------------------------------------------------------|
    // |----------------------------Destruct Protocol---------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> FURIOUS_ANGRY_DESTRUCT_PROTOCOL = registerEntry("furious_angry_destruct_protocol", createDialog(
            "dialog.welcomeplayer.event.destruct_protocol.furious.0",
            "dialog.welcomeplayer.event.destruct_protocol.furious.1",
            "dialog.welcomeplayer.event.destruct_protocol.furious.2"
    ));
    public static final ResourceKey<AudienceEvent> THRILLED_CRUEL_DESTRUCT_PROTOCOL = registerEntry("thrilled_cruel_destruct_protocol", createDialog(
            "dialog.welcomeplayer.event.destruct_protocol.thrilled.0",
            "dialog.welcomeplayer.event.destruct_protocol.thrilled.1",
            "dialog.welcomeplayer.event.destruct_protocol.thrilled.2",
            "dialog.welcomeplayer.event.destruct_protocol.thrilled.3"
    ));

    // |------------------------------------------------------------------------|
    // |-------------------------------Zombie Horde-----------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> FURIOUS_ANGRY_ZOMBIE_HORDE = registerEntry("furious_angry_zombie_horde", createDialog(
            "dialog.welcomeplayer.event.zombie_horde.furious.0",
            "dialog.welcomeplayer.event.zombie_horde.furious.1",
            "dialog.welcomeplayer.event.zombie_horde.furious.2"
    ));
    public static final ResourceKey<AudienceEvent> BORED_ANGRY_ZOMBIE_HORDE = registerEntry("bored_angry_zombie_horde", createDialog(
            "dialog.welcomeplayer.event.zombie_horde.bored.0",
            "dialog.welcomeplayer.event.zombie_horde.bored.1"
    ));
    public static final ResourceKey<AudienceEvent> THRILLED_CRUEL_ZOMBIE_HORDE = registerEntry("thrilled_cruel_zombie_horde", createDialog(
            "dialog.welcomeplayer.event.zombie_horde.thrilled.0",
            "dialog.welcomeplayer.event.zombie_horde.thrilled.1",
            "dialog.welcomeplayer.event.zombie_horde.thrilled.2"
    ));

    // |------------------------------------------------------------------------|
    // |-------------------------------Raiding Party----------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> FURIOUS_ANGRY_RAIDING_PARTY = registerEntry("furious_angry_raiding_party", createDialog(
            "dialog.welcomeplayer.event.raiding_party.furious.0",
            "dialog.welcomeplayer.event.raiding_party.furious.1"
    ));
    public static final ResourceKey<AudienceEvent> BORED_ANGRY_RAIDING_PARTY = registerEntry("bored_angry_raiding_party", createDialog(
            "dialog.welcomeplayer.event.raiding_party.bored.0",
            "dialog.welcomeplayer.event.raiding_party.bored.1"
    ));

    // |------------------------------------------------------------------------|
    // |-------------------------------Fast Service-----------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> NEUTRAL_HAPPY_FAST_SERVICE = registerEntry("neutral_happy_fast_service", createDialog(
            "dialog.welcomeplayer.event.fast_service.neutral.0",
            "dialog.welcomeplayer.event.fast_service.neutral.1"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_HAPPY_FAST_SERVICE = registerEntry("interested_happy_fast_service", createDialog(
            "dialog.welcomeplayer.event.fast_service.interested.0",
            "dialog.welcomeplayer.event.fast_service.interested.1",
            "dialog.welcomeplayer.event.fast_service.interested.2"
    ));

    // |------------------------------------------------------------------------|
    // |--------------------------------Bomb Squad------------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> FURIOUS_ANGRY_BOMB_SQUAD = registerEntry("furious_angry_bomb_squad", createDialog(
            "dialog.welcomeplayer.event.bomb_squad.furious.0",
            "dialog.welcomeplayer.event.bomb_squad.furious.1",
            "dialog.welcomeplayer.event.bomb_squad.furious.2"
    ));
    public static final ResourceKey<AudienceEvent> BORED_CRUEL_BOMB_SQUAD = registerEntry("bored_cruel_bomb_squad", createDialog(
            "dialog.welcomeplayer.event.bomb_squad.bored.0",
            "dialog.welcomeplayer.event.bomb_squad.bored.1"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_ANGRY_BOMB_SQUAD = registerEntry("interested_angry_bomb_squad", createDialog(
            "dialog.welcomeplayer.event.bomb_squad.interested.0",
            "dialog.welcomeplayer.event.bomb_squad.interested.1",
            "dialog.welcomeplayer.event.bomb_squad.interested.2"
    ));

    // |------------------------------------------------------------------------|
    // |------------------------------Nuclear Squad-----------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> FURIOUS_CRUEL_NUCLEAR_SQUAD = registerEntry("furious_cruel_nuclear_squad", createDialog(
            "dialog.welcomeplayer.event.nuclear_squad.furious.0",
            "dialog.welcomeplayer.event.nuclear_squad.furious.1"
    ));
    public static final ResourceKey<AudienceEvent> THRILLED_CRUEL_NUCLEAR_SQUAD = registerEntry("thrilled_cruel_nuclear_squad", createDialog(
            "dialog.welcomeplayer.event.nuclear_squad.thrilled.0",
            "dialog.welcomeplayer.event.nuclear_squad.thrilled.1",
            "dialog.welcomeplayer.event.nuclear_squad.thrilled.2"
    ));

    // |------------------------------------------------------------------------|
    // |-----------------------------Roaring Thunder----------------------------|
    // |------------------------------------------------------------------------|
    public static final ResourceKey<AudienceEvent> BORED_CRUEL_ROARING_THUNDER = registerEntry("bored_sad_roaring_thunder", createDialog(
            "dialog.welcomeplayer.event.roaring_thunder.bored.0",
            "dialog.welcomeplayer.event.roaring_thunder.bored.1",
            "dialog.welcomeplayer.event.roaring_thunder.bored.2",
            "dialog.welcomeplayer.event.roaring_thunder.bored.3"
    ));
    public static final ResourceKey<AudienceEvent> INTERESTED_SAD_ROARING_THUNDER = registerEntry("interested_sad_roaring_thunder", createDialog(
            "dialog.welcomeplayer.event.roaring_thunder.interested.0",
            "dialog.welcomeplayer.event.roaring_thunder.interested.1",
            "dialog.welcomeplayer.event.roaring_thunder.interested.2",
            "dialog.welcomeplayer.event.roaring_thunder.interested.3"
    ));

    public static List<Component> createDialog(String... comp) {
        LinkedList<Component> compList = new LinkedList<>();
        for (String translation : comp) {
            compList.add(Component.translatable(translation));
        }
        return compList;
    }

    public static ResourceKey<AudienceEvent> registerEntry(String name, List<Component> dialog) {
        ResourceKey<AudienceEvent> event = ResourceKey.create(DatapackRegistry.AUDIENCE_EVENTS, ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, name));
        EVENTS.add(getEventInfoByString(name, event, dialog));
        return event;
    }

    private static EventInfo getEventInfoByString(String name, ResourceKey<AudienceEvent> event, List<Component> dialog) {
        String[] values = name.split("_");

        AudiencePhase phase = switch (values[0]) {
            case "thrilled" -> AudiencePhase.THRILLED;
            case "interested" -> AudiencePhase.INTERESTED;
            case "neutral" -> AudiencePhase.NEUTRAL;
            case "bored" -> AudiencePhase.BORED;
            case "furious" -> AudiencePhase.FURIOUS;
            default -> throw new IllegalArgumentException("Invalid phase name was entered for: " + name);
        };

        AudienceMood mood = switch (values[1]) {
            case "happy" -> AudienceMood.HAPPY;
            case "neutral" -> AudienceMood.NEUTRAL;
            case "sad" -> AudienceMood.SAD;
            case "angry" -> AudienceMood.ANGRY;
            case "cruel" -> AudienceMood.CRUEL;
            default -> throw new IllegalArgumentException("Invalid mood name was entered for: " + name);
        };

        StringBuilder id = new StringBuilder(values[2]);
        for (int i = 3; i < values.length; i++) {
            id.append("_").append(values[i]);
        }

        return new EventInfo(event, id.toString(), phase, mood, dialog);
    }

    public static void bootstrap(BootstrapContext<AudienceEvent> context) {
        for (EventInfo i : EVENTS) {
            register(context, i.key, i.id, i.phase, i.mood, i.dialog);
        }
    }

    private static void register(BootstrapContext<AudienceEvent> context,
                                 ResourceKey<AudienceEvent> key, String id, AudiencePhase phase, AudienceMood mood, List<Component> list) {
        context.register(key, new AudienceEvent(id, phase, mood, list));
    }

    public static class AudienceEventType {
        public static final Codec<AudienceEventType> CODEC = RecordCodecBuilder.create(inst ->
                inst.group(
                        Codec.STRING.fieldOf("id").forGetter(e -> e.id)
                ).apply(inst, AudienceEventType::new)
        );

        private String id;

        public AudienceEventType(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }

        public boolean is(AudienceEvent event) {
            return Objects.equals(event.ID(), id);
        }
    }


    private record EventInfo(ResourceKey<AudienceEvent> key, String id, AudiencePhase phase, AudienceMood mood,
                             List<Component> dialog) {
    }
}
