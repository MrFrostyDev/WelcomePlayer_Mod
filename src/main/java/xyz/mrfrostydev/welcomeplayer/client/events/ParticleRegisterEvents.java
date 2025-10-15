package xyz.mrfrostydev.welcomeplayer.client.events;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.particles.*;
import xyz.mrfrostydev.welcomeplayer.registries.ParticleRegistry;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class ParticleRegisterEvents {

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.LASER_BLAST.get(), LaserBlastParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.LASER_TRAIL.get(), LaserTrailParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.LASER_CHARGE.get(), LaserChargeParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.WARP_REWARD.get(), WarpRewardParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.SHOCK_BLAST.get(), ShockBlastParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.SHOCK_CHARGE.get(), ShockChargeParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.ORB_TRAIL.get(), OrbTrailParticle.Provider::new);
    }
}
