package xyz.mrfrostydev.welcomeplayer.mixins;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.PlayerObjectives;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

@Mixin(targets = "net.minecraft.world.entity.TamableAnimal")
public abstract class TamableAnimalMixin extends Animal {
    protected TamableAnimalMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tame", at = @At("HEAD"))
    protected void addObjectiveProgress(Player player, CallbackInfo ci) {
        if(level() instanceof ServerLevel svlevel){
            PlayerObjective obj = ObjectiveUtil.getGoingObjective(svlevel);
            if(obj.is(svlevel, PlayerObjectives.DOG_PERSON) && getType().equals(EntityType.WOLF)){
                ObjectiveUtil.addProgress(svlevel, 1);
            }
            else if(obj.is(svlevel, PlayerObjectives.CAT_PERSON) && getType().equals(EntityType.CAT)){
                ObjectiveUtil.addProgress(svlevel, 1);
            }
        }
    }
}
