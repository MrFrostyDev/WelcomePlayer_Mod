package xyz.mrfrostydev.welcomeplayer.data;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public class AudiencePhase {
    public static final AudiencePhase HATED = AudiencePhase.create("hated");
    public static final AudiencePhase DISLIKED = AudiencePhase.create("disliked");
    public static final AudiencePhase NEUTRAL = AudiencePhase.create("neutral");
    public static final AudiencePhase LIKED = AudiencePhase.create("liked");
    public static final AudiencePhase ADORED = AudiencePhase.create("adored");

    public static final Codec<AudiencePhase> CODEC = Codec.STRING.xmap(
            AudiencePhase::create,
            AudiencePhase::phase
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, AudiencePhase> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, AudiencePhase::phase,
            AudiencePhase::new
    );

    private final String phase;

    private AudiencePhase(String phase){
        this.phase = phase;
    }

    public static AudiencePhase create(String v){
        return new AudiencePhase(v);
    }

    public String phase() {
        return phase;
    }

    public static int size() {
        return 5;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(phase);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AudiencePhase p
                && Objects.equals(p.phase(), this.phase);
    }
}
