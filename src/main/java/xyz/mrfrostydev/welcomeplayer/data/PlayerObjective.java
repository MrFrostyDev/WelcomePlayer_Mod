package xyz.mrfrostydev.welcomeplayer.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xyz.mrfrostydev.welcomeplayer.registries.DatapackRegistry;

import java.util.List;
import java.util.Objects;

public record PlayerObjective(String id, AudiencePhase phase, int maxValue, boolean playerScaling, List<Component> dialog) {
    public static final PlayerObjective NOTHING = new PlayerObjective("nothing", AudiencePhase.NEUTRAL, 10, false, List.of(Component.literal("nothing new")));

    public static final Codec<PlayerObjective> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.fieldOf("id").forGetter(PlayerObjective::id),
                    AudiencePhase.CODEC.fieldOf("phase").forGetter(PlayerObjective::phase),
                    Codec.INT.fieldOf("maxValue").forGetter(PlayerObjective::maxValue),
                    Codec.BOOL.fieldOf("playerScaling").forGetter(PlayerObjective::playerScaling),
                    ComponentSerialization.CODEC.listOf().fieldOf("dialog").forGetter(PlayerObjective::dialog)
            ).apply(instance, PlayerObjective::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerObjective> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, PlayerObjective::id,
            AudiencePhase.STREAM_CODEC, PlayerObjective::phase,
            ByteBufCodecs.INT, PlayerObjective::maxValue,
            ByteBufCodecs.BOOL, PlayerObjective::playerScaling,
            ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs.list(32)), PlayerObjective::dialog,
            PlayerObjective::new
    );

    public static PlayerObjective create(String v, AudiencePhase phase, int maxValue, boolean playerScaling, List<Component> dialog){
        return new PlayerObjective(v, phase, maxValue, playerScaling, dialog);
    }

    public boolean is(PlayerObjective obj){
        return obj.equals(this);
    }

    public boolean is(Level level, ResourceKey<PlayerObjective> key){
        return is(Objects.requireNonNull(level.registryAccess().registryOrThrow(DatapackRegistry.PLAYER_OBJECTIVES).get(key)));
    }

    public boolean isNothing(){
        return is(NOTHING);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlayerObjective that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
