package xyz.mrfrostydev.welcomeplayer.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;
import java.util.Objects;

public record PlayerObjective(String id, AudiencePhase phase, int duration, List<Component> dialog) {
    public static final PlayerObjective NOTHING = new PlayerObjective("nothing", AudiencePhase.NEUTRAL, -1, List.of(Component.literal("nothing new")));

    public static final Codec<PlayerObjective> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.fieldOf("id").forGetter(PlayerObjective::id),
                    AudiencePhase.CODEC.fieldOf("phase").forGetter(PlayerObjective::phase),
                    Codec.INT.fieldOf("duration").forGetter(PlayerObjective::duration),
                    ComponentSerialization.CODEC.listOf().fieldOf("dialog").forGetter(PlayerObjective::dialog)
            ).apply(instance, PlayerObjective::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerObjective> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, PlayerObjective::id,
            AudiencePhase.STREAM_CODEC, PlayerObjective::phase,
            ByteBufCodecs.INT, PlayerObjective::duration,
            ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs.list(32)), PlayerObjective::dialog,
            PlayerObjective::new
    );

    public static PlayerObjective create(String v, AudiencePhase phase, int duration, List<Component> dialog){
        return new PlayerObjective(v, phase, duration, dialog);
    }

    public boolean is(PlayerObjective obj){
        return obj.equals(this);
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
