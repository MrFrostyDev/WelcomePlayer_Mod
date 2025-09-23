package xyz.mrfrostydev.welcomeplayer.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

public class BeaconBlock extends HorizontalDirectionalBlock {
    protected BeaconBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(BeaconBlock::new);
    }
}
