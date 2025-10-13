package xyz.mrfrostydev.welcomeplayer.mixins;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.PlayerObjectives;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

@Mixin(targets = "net.minecraft.world.entity.item.PrimedTnt")
public abstract class PrimedTntMixin extends Entity {
    protected PrimedTntMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "explode", at = @At("HEAD"))
    protected void addObjectiveProgress(CallbackInfo ci) {
        if(this.level() instanceof ServerLevel svlevel){
            PlayerObjective obj = ObjectiveUtil.getGoingObjective(svlevel);
            if(obj.is(svlevel, PlayerObjectives.DEMOLITION) || obj.is(svlevel, PlayerObjectives.DESTRUCTION)){
                ObjectiveUtil.addProgress(svlevel, 1);
            }
        }
    }
}
