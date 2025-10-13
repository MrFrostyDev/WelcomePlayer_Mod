package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, WelcomeplayerMain.MOD_ID);

    public static void register(IEventBus event){
        PARTICLE_TYPES.register(event);
    }

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LASER_BLAST = PARTICLE_TYPES.register(
            "laser_blast",
            () -> new SimpleParticleType(false)
    );

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LASER_TRAIL = PARTICLE_TYPES.register(
            "laser_trail",
            () -> new SimpleParticleType(false)
    );

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LASER_CHARGE = PARTICLE_TYPES.register(
            "laser_charge",
            () -> new SimpleParticleType(false)
    );

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WARP_REWARD = PARTICLE_TYPES.register(
            "warp_reward",
            () -> new SimpleParticleType(false)
    );
}
