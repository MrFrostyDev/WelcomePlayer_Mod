package xyz.mrfrostydev.welcomeplayer.data;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public class AudienceMood {
    public static final AudienceMood NEUTRAL = AudienceMood.create("neutral");
    public static final AudienceMood HAPPY = AudienceMood.create("happy");
    public static final AudienceMood SAD = AudienceMood.create("sad");
    public static final AudienceMood BORED = AudienceMood.create("bored");
    public static final AudienceMood ANGRY = AudienceMood.create("angry");
    public static final AudienceMood CRUEL = AudienceMood.create("cruel");

    public static final Codec<AudienceMood> CODEC = Codec.STRING.xmap(
            AudienceMood::create,
            AudienceMood::mood
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, AudienceMood> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, AudienceMood::mood,
            AudienceMood::new
    );

    private final String mood;

    private AudienceMood(String mood){
        this.mood = mood;
    }

    public static AudienceMood create(String v){
        return new AudienceMood(v);
    }

    public String mood() {
        return mood;
    }

    public static int size() {
        return 6;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mood);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AudienceMood o
                && Objects.equals(o.mood(), this.mood);
    }

    @Override
    public String toString() {
        return mood.toUpperCase();
    }
}
