package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.TagRegistry;

import java.util.concurrent.CompletableFuture;

public class ItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider {
    public ItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(TagRegistry.CONTRABAND)
                .add(
                        Items.MACE,
                        Items.TRIDENT,
                        ItemRegistry.SHOCK_ORB_LAUNCHER.get(),
                        ItemRegistry.LASER_CUTTER.get(),
                        ItemRegistry.STASIS_STICK.get()
                )
                .addOptionalTags(
                        ItemTags.SWORDS,
                        ItemTags.AXES,
                        ItemTags.SHARP_WEAPON_ENCHANTABLE,
                        ItemTags.BOW_ENCHANTABLE,
                        ItemTags.CROSSBOW_ENCHANTABLE
                );
    }

}

