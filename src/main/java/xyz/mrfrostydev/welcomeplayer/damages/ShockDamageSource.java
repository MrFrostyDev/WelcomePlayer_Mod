package xyz.mrfrostydev.welcomeplayer.damages;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShockDamageSource extends DamageSource {
    public ShockDamageSource(Holder<DamageType> type, @Nullable Entity entity) {
        super(type, entity);
    }

    @Override
    public @NotNull Component getLocalizedDeathMessage(LivingEntity victim){
        return Component.translatable("death.welcomeplayer.damage.shock", victim.getDisplayName());
    }
}
