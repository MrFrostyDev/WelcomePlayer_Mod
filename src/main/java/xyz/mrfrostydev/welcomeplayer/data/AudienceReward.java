package xyz.mrfrostydev.welcomeplayer.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record AudienceReward(ItemStack stack, AudiencePhase phase) {
    public static final Codec<AudienceReward> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ItemStack.CODEC.fieldOf("itemstack").forGetter(AudienceReward::stack),
                    AudiencePhase.CODEC.fieldOf("phase").forGetter(AudienceReward::phase)
            ).apply(instance, AudienceReward::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AudienceReward> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, AudienceReward::stack,
            AudiencePhase.STREAM_CODEC, AudienceReward::phase,
            AudienceReward::new
    );
}
