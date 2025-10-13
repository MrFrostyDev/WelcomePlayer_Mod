package xyz.mrfrostydev.welcomeplayer.data.datagen.providers.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ItemRegistry;

import java.util.HashSet;
import java.util.Set;

public class ModBlockLoot extends BlockLootSubProvider {

    private final Set<Block> lootTables = new HashSet<>();

    public ModBlockLoot(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    public void generate() {
        dropSelf(BlockRegistry.RETROSTEEL_ORANGE_TILES.get());
        dropSelf(BlockRegistry.RETROSTEEL_WHITE_TILES.get());
        dropSelf(BlockRegistry.RETROSTEEL_BROWN_WALL.get());
        dropSelf(BlockRegistry.RETROSTEEL_WHITE_WALL.get());
        dropSelf(BlockRegistry.RETROSTEEL_BLEND_WALL.get());
        dropSelf(BlockRegistry.RETROSTEEL_BEAMS.get());

        add(BlockRegistry.RETROSTEEL_ORE.get(), b -> createOreDrop(b, ItemRegistry.RAW_RETROSTEEL.get()));

        dropOther(BlockRegistry.RETRO_TESLA_COIL.get(), ItemRegistry.BATTERY.get());
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        this.lootTables.add(block);
        this.map.put(block.getLootTable(), builder);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return lootTables;
    }
}