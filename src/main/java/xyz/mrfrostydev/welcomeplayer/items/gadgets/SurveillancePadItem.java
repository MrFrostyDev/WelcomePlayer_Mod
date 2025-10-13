package xyz.mrfrostydev.welcomeplayer.items.gadgets;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.mrfrostydev.welcomeplayer.client.gui.screens.SurveillancePadScreen;
import xyz.mrfrostydev.welcomeplayer.data.ClientAudienceData;
import xyz.mrfrostydev.welcomeplayer.entities.items.BouncePadEntity;
import xyz.mrfrostydev.welcomeplayer.utils.ScreenUtil;

public class SurveillancePadItem extends Item {
    public SurveillancePadItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack usedStack = player.getItemInHand(usedHand);
        if(!player.isCrouching()){
            if(level.isClientSide){
                ScreenUtil.openSurveillancePad(Minecraft.getInstance(), ClientAudienceData.get());
                return InteractionResultHolder.consume(usedStack);
            }
        }
        return super.use(level, player, usedHand);
    }
}
