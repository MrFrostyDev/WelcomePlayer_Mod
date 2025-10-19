package xyz.mrfrostydev.welcomeplayer.events.subscribers.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents;
import xyz.mrfrostydev.welcomeplayer.registries.DataAttachmentRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.ParticleRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.SoundEventRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceEventUtil;

import java.util.Optional;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class EventDamageEvents {

    @SubscribeEvent
    public static void onEntityDamageShield(LivingIncomingDamageEvent event){
        if(!(event.getEntity().level() instanceof ServerLevel svlevel))return;
        if(event.getSource().getEntity() == null)return;
        if(event.getEntity().getType().equals(EntityType.PLAYER))return;
        LivingEntity victim = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        AudienceEvent goingEvent = AudienceEventUtil.getGoingEvent(svlevel);

        if(goingEvent.is(AudienceEvents.BACKUP_SAFETY)){
            if(!victim.hasData(DataAttachmentRegistry.DAMAGE_SHIELD.get())){
                victim.setData(DataAttachmentRegistry.DAMAGE_SHIELD.get(), 3);
            }
        }
        if(victim.hasData(DataAttachmentRegistry.DAMAGE_SHIELD.get())){
            int remainingShield = victim.getData(DataAttachmentRegistry.DAMAGE_SHIELD);
            if(remainingShield > 0){
                victim.setData(DataAttachmentRegistry.DAMAGE_SHIELD.get(),remainingShield - 1);

                svlevel.playSound(null,
                        victim.getX(), victim.getEyeY(), victim.getZ(),
                        SoundEventRegistry.SHIELD_HIT.get(), SoundSource.NEUTRAL,
                        1.0F, svlevel.random.nextFloat() * 0.4F + 0.8F
                );

                if(attacker != null){
                    double victimHeightHalf = victim.getBbHeight() / 2;
                    Vec3 vecDir =  attacker.getEyePosition()
                            .subtract(victim.position())
                            .normalize()
                            .scale(1.2);
                    svlevel.sendParticles(ParticleRegistry.SHIELD_HIT.get(),
                            victim.getX() + vecDir.x, victim.getY() + vecDir.y + victimHeightHalf, victim.getZ() + vecDir.z,
                            1, 0, 0, 0, 0
                    );
                }
                else{
                    svlevel.sendParticles(ParticleRegistry.SHIELD_HIT.get(),
                            victim.getX(), victim.getEyeY() - 0.2, victim.getZ(),
                            1, 0, 0, 0, 0
                    );
                }

                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityDamageRepulsion(LivingIncomingDamageEvent event){
        if(!(event.getEntity().level() instanceof ServerLevel svlevel))return;
        if(event.getSource().getEntity() == null)return;
        if(event.getEntity().getType().equals(EntityType.PLAYER))return;
        LivingEntity victim = event.getEntity();
        AudienceEvent goingEvent = AudienceEventUtil.getGoingEvent(svlevel);

        if(goingEvent.is(AudienceEvents.REPULSION_TECH)){
            if(!victim.hasData(DataAttachmentRegistry.REPULSION_COOLDOWN.get())){
                victim.setData(DataAttachmentRegistry.REPULSION_COOLDOWN.get(), 0);
            }
            int cooldown = victim.getData(DataAttachmentRegistry.REPULSION_COOLDOWN);
            if(cooldown <= 0){
                victim.setData(DataAttachmentRegistry.REPULSION_COOLDOWN.get(),5);

                svlevel.explode(victim, null,
                        new SimpleExplosionDamageCalculator(
                                false, false, Optional.of(2.0F), Optional.empty()),
                        victim.getX(), victim.getY() + victim.getBbHeight() / 2, victim.getZ(),
                        4, false,
                        Level.ExplosionInteraction.TRIGGER,
                        ParticleRegistry.SHOCK_BLAST.get(),
                        ParticleRegistry.SHOCK_BLAST.get(),
                        SoundEvents.BREEZE_WIND_CHARGE_BURST
                );

                svlevel.playSound(null,
                        victim.getX(), victim.getEyeY(), victim.getZ(),
                        SoundEvents.BREEZE_WIND_CHARGE_BURST, SoundSource.NEUTRAL,
                        1.0F, svlevel.random.nextFloat() * 0.4F + 0.8F
                );
            }
        }
    }

    @SubscribeEvent
    public static void onEntityKnockbackPropulsion(LivingKnockBackEvent event){
        if(!(event.getEntity().level() instanceof ServerLevel svlevel))return;
        AudienceEvent goingEvent = AudienceEventUtil.getGoingEvent(svlevel);

        if(goingEvent.is(AudienceEvents.PROPULSION_UPGRADE)){
            event.setStrength(event.getOriginalStrength() * 4);
        }
    }
}
