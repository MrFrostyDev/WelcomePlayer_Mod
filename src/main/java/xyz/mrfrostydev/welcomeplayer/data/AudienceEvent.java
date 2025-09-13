package xyz.mrfrostydev.welcomeplayer.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents;

import java.util.List;
import java.util.Objects;

public record AudienceEvent(String ID, AudiencePhase phase, AudienceMood mood, List<Component> dialog) {
    public static final AudienceEvent NOTHING = new AudienceEvent("nothing", AudiencePhase.NEUTRAL, AudienceMood.NEUTRAL, List.of(Component.literal("changed into neutral")));

    public static final Codec<AudienceEvent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.fieldOf("id").forGetter(AudienceEvent::ID),
                    AudiencePhase.CODEC.fieldOf("phase").forGetter(AudienceEvent::phase),
                    AudienceMood.CODEC.fieldOf("mood").forGetter(AudienceEvent::mood),
                    ComponentSerialization.CODEC.listOf().fieldOf("dialog").forGetter(AudienceEvent::dialog)
            ).apply(instance, AudienceEvent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AudienceEvent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, AudienceEvent::ID,
            AudiencePhase.STREAM_CODEC, AudienceEvent::phase,
            AudienceMood.STREAM_CODEC, AudienceEvent::mood,
            ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs.list(32)), AudienceEvent::dialog,
            AudienceEvent::new
    );

    public boolean is(AudienceEvent compare){
        return this.equals(compare);
    }

    public boolean is(AudienceEvents.AudienceEventType compare){return compare.is(this);}

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AudienceEvent o
                && Objects.equals(o.ID, this.ID);
    }

    public Component getName(){
        return Component.translatable("event.welcomeplayer." + ID);
    }
}
