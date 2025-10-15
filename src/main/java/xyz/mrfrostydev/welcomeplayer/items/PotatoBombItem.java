package xyz.mrfrostydev.welcomeplayer.items;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PotatoBombItem extends Item {
    public PotatoBombItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(entity instanceof Player player && player.hasInfiniteMaterials()){
            super.inventoryTick(stack, level, entity, slotId, isSelected);
            return;
        }

        if(level instanceof ServerLevel){
            int tickCount = level.getServer().getTickCount();
            if(tickCount % 40 == 0){
                stack.setDamageValue(stack.getDamageValue() + 2);
                if(stack.getDamageValue() >= stack.getMaxDamage()){
                    stack.setCount(0);
                    this.explode(level, entity.getX(), entity.getEyeY() - 0.2, entity.getZ());
                }
            }
        }

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    protected void explode(Level level, double x, double y, double z) {
        level
                .explode(
                        null,
                        null,
                        null,
                        x, y, z,
                        3.0F,
                        false,
                        Level.ExplosionInteraction.TRIGGER
                );
    }
}
