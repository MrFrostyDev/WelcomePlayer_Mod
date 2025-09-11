package xyz.mrfrostydev.welcomeplayer.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record VendorItem(ItemStack item, int price, int minFavour) {
    public static final Codec<VendorItem> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ItemStack.CODEC.fieldOf("item").forGetter(VendorItem::item),
                    Codec.INT.fieldOf("price").forGetter(VendorItem::price),
                    Codec.INT.fieldOf("minFavour").forGetter(VendorItem::minFavour)
            ).apply(instance, VendorItem::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, VendorItem> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, VendorItem::item,
            ByteBufCodecs.INT, VendorItem::price,
            ByteBufCodecs.INT, VendorItem::minFavour,
            VendorItem::new
    );

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VendorItem r
                && ItemStack.isSameItemSameComponents(r.item, this.item)
                && r.price == this.price
                && r.minFavour == this.minFavour;
    }

    public ItemStack getItem(){
        return item.copy();
    }
}
