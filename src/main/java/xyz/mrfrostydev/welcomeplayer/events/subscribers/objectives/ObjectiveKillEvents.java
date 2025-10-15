package xyz.mrfrostydev.welcomeplayer.events.subscribers.objectives;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.PlayerObjectives;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.TagRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

import java.util.function.Predicate;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class ObjectiveKillEvents {

    @SubscribeEvent
    public static void onEntityKill(LivingDeathEvent event){
        if(!(event.getEntity().level() instanceof ServerLevel svlevel))return;
        if(event.getSource().getEntity() == null)return;
        LivingEntity entity = event.getEntity();
        Entity attacker = event.getSource().getEntity();

        if(!(attacker.getType().equals(EntityType.PLAYER)))return; // Must be killed by player.

        if(compareEntityWithObjective(svlevel, entity, PlayerObjectives.METAL_ROSES, EntityType.IRON_GOLEM)) return;
        if(compareEntityWithObjective(svlevel, entity, PlayerObjectives.DUCK_HUNT, EntityType.CHICKEN)) return;

        if(compareEntityWithObjective(svlevel, entity, PlayerObjectives.THE_SHEPHERD, e -> e.getType().equals(EntityType.SHEEP) || e.getType().equals(EntityType.GOAT))) return;
        if(compareEntityWithObjective(svlevel, entity, PlayerObjectives.PEST_CONTROL, TagRegistry.ARACHNID)) return;
        if(compareEntityWithObjective(svlevel, entity, PlayerObjectives.ZOMBIE_KILLER, EntityType.ZOMBIE)) return;
        if(compareEntityWithObjective(svlevel, entity, PlayerObjectives.THE_BUTCHER, TagRegistry.ANIMAL)) return;

        if(compareEntityWithObjective(svlevel, entity, PlayerObjectives.SET_BATTLE, TagRegistry.HOST_ROBOT)) return;
        if(compareEntityWithObjective(svlevel, entity, PlayerObjectives.MONSTER_HUNTER, TagRegistry.UNDEAD)) return;
        if(compareEntityWithObjective(svlevel, entity, PlayerObjectives.PURE_VIOLENCE, e -> !e.getType().equals(EntityType.PLAYER))) return;

        if(compareEntityWithObjective(svlevel, entity, PlayerObjectives.GOLIATH, EntityRegistry.ERADICATOR.get())) return;
        if(compareEntityWithObjective(svlevel, entity, PlayerObjectives.NIGHT_KILLER, EntityType.WARDEN)) return;
    }

    // |--------------------------------------------------------------------------------|
    // |------------------------------------Methods-------------------------------------|
    // |--------------------------------------------------------------------------------|

    public static boolean compareEntityWithObjective(ServerLevel svlevel, LivingEntity entity, ResourceKey<PlayerObjective> event, Predicate<LivingEntity> predicate){
        boolean isTrue = ObjectiveUtil.isCurrentObjective(svlevel, event) && predicate.test(entity);
        if(isTrue) ObjectiveUtil.addProgress(svlevel, 1);
        return isTrue;
    }

    public static boolean compareEntityWithObjective(ServerLevel svlevel, LivingEntity entity, ResourceKey<PlayerObjective> event, EntityType<?> entityType){
        boolean isTrue = ObjectiveUtil.isCurrentObjective(svlevel, event) && entity.getType().equals(entityType);
        if(isTrue) ObjectiveUtil.addProgress(svlevel, 1);
        return isTrue;
    }

    public static boolean compareEntityWithObjective(ServerLevel svlevel, LivingEntity entity, ResourceKey<PlayerObjective> event, TagKey<EntityType<?>> tag){
        boolean isTrue = ObjectiveUtil.isCurrentObjective(svlevel, event) && entity.getType().is(tag);
        if(isTrue) ObjectiveUtil.addProgress(svlevel, 1);
        return isTrue;
    }
}
