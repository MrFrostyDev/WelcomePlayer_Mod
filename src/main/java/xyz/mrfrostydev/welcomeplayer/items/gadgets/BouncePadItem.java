package xyz.mrfrostydev.welcomeplayer.items.gadgets;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.mrfrostydev.welcomeplayer.entities.items.BouncePadEntity;

public class BouncePadItem extends Item {
    public BouncePadItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack padStack = player.getItemInHand(hand);
        if(player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.fail(padStack);

        player.getCooldowns().addCooldown(this, 10);
        padStack.consume(1, player);
        if(!level.isClientSide()){
            level.addFreshEntity(new BouncePadEntity(level, player));
        }

        return InteractionResultHolder.success(padStack);
    }
}
