package xyz.mrfrostydev.welcomeplayer.events.subscribers.events;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.PlayerObjective;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.PlayerObjectives;
import xyz.mrfrostydev.welcomeplayer.entities.projectiles.ShockChargeProjectile;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.TagRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceEventUtil;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

import java.util.function.Predicate;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class EventKillEvents {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event){
        if(!(event.getEntity().level() instanceof ServerLevel svlevel))return;
        if(event.getSource().getEntity() == null)return;
        LivingEntity entity = event.getEntity();
        AudienceEvent goingEvent = AudienceEventUtil.getGoingEvent(svlevel);

        if(entity.getType().is(TagRegistry.HOST_ROBOT) && goingEvent.is(AudienceEvents.DESTRUCT_PROTOCOL)){
            float power = 1.8F * (float)Math.log10(entity.getMaxHealth() + 1);
            svlevel.explode(entity,
                    Explosion.getDefaultDamageSource(entity.level(), entity), null,
                    entity.getX(), entity.getY() + 0.4, entity.getZ(),
                    power, true, Level.ExplosionInteraction.MOB,
                    ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION, SoundEvents.GENERIC_EXPLODE);
            svlevel.sendParticles(ParticleTypes.EXPLOSION,
                    entity.getX(), entity.getY() + 0.4, entity.getZ(),
                    4, 0.8, 0.8, 0.8, 0);
        }
        else if(goingEvent.is(AudienceEvents.ELECTRIC_SOUL)){
            ShockChargeProjectile shock = new ShockChargeProjectile(svlevel, null,
                    entity.getX(), entity.getEyeY(), entity.getZ(), 100, true);
            svlevel.addFreshEntity(shock);
        }
    }
}
