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
import java.util.Objects;

public class PlayerObjectives {

    private static final List<ObjectiveInfo> OBJECTIVES = new ArrayList<>();

    public static final PlayerObjectives.ObjectiveType EMPTY = new PlayerObjectives.ObjectiveType("empty");
    public static final PlayerObjectives.ObjectiveType TEST = new PlayerObjectives.ObjectiveType("test");


    // Expired Goods
    public static final ResourceKey<PlayerObjective> NEUTRAL_BROKEN_FEED = registerEntry("neutral_expired_goods", createDialog(
            "dialog.boundbyflesh.fleshlords_mock.0",
            "dialog.boundbyflesh.fleshlords_mock.1"
    ));

    public static void bootstrap(BootstrapContext<PlayerObjective> context){
        for(PlayerObjectives.ObjectiveInfo i : OBJECTIVES){
            register(context, i.key, i.id, i.phase, i.dialog);
        }
    }

    public static ResourceKey<PlayerObjective> registerEntry(String name, List<Component> dialog){
        ResourceKey<PlayerObjective> event = ResourceKey.create(DatapackRegistry.PLAYER_OBJECTIVES, ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, name));
        OBJECTIVES.add(getEventInfoByString(name, event, dialog));
        return event;
    }

    private static PlayerObjectives.ObjectiveInfo getEventInfoByString(String name, ResourceKey<PlayerObjective> event, List<Component> dialog){
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

        return new PlayerObjectives.ObjectiveInfo(event, id.toString(), phase, dialog);
    }

    public static List<Component> createDialog(String... comp){
        LinkedList<Component> compList = new LinkedList<>();
        for(String translation : comp){
            compList.add(Component.translatable(translation));
        }
        return compList;
    }

    private static void register(BootstrapContext<PlayerObjective> context,
                                 ResourceKey<PlayerObjective> key, String id, AudiencePhase phase, List<Component> comps){
        context.register(key, new PlayerObjective(id, phase, comps));
    }

    public static class ObjectiveType {
        public static final Codec<PlayerObjectives.ObjectiveType> CODEC = RecordCodecBuilder.create(inst ->
                inst.group(
                        Codec.STRING.fieldOf("id").forGetter(e -> e.id)
                ).apply(inst, PlayerObjectives.ObjectiveType::new)
        );

        private String id;

        public ObjectiveType(String id){
            this.id = id;
        }

        public String id(){
            return id;
        }

        public boolean is(PlayerObjective obj){
            return Objects.equals(obj.id(), id);
        }
    }

    private record ObjectiveInfo(ResourceKey<PlayerObjective> key, String id, AudiencePhase phase, List<Component> dialog) {}
}
