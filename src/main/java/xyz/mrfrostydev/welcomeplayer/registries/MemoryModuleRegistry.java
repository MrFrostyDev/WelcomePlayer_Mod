package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

import java.util.Optional;
import java.util.function.Supplier;

public class MemoryModuleRegistry {

    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(BuiltInRegistries.MEMORY_MODULE_TYPE, WelcomeplayerMain.MOD_ID);
    public static void register(IEventBus eventBus) {
        MEMORY_MODULE_TYPES.register(eventBus);
    }

    public static final Supplier<MemoryModuleType<Unit>> ERADICATOR_SHOOT_COOLDOWN = MEMORY_MODULE_TYPES.register(
            "eradicator_shoot_cooldown",
            () -> new MemoryModuleType<>(Optional.of(Unit.CODEC))
    );

    public static final Supplier<MemoryModuleType<Unit>> ERADICATOR_SHOOT_CHARGING = MEMORY_MODULE_TYPES.register(
            "eradicator_shoot_charging",
            () -> new MemoryModuleType<>(Optional.of(Unit.CODEC))
    );

    public static final Supplier<MemoryModuleType<Unit>> ERADICATOR_SHOOTING = MEMORY_MODULE_TYPES.register(
            "eradicator_shooting",
            () -> new MemoryModuleType<>(Optional.of(Unit.CODEC))
    );

    public static final Supplier<MemoryModuleType<Unit>> ERADICATOR_SAWING = MEMORY_MODULE_TYPES.register(
            "eradicator_sawing",
            () -> new MemoryModuleType<>(Optional.of(Unit.CODEC))
    );

    public static final Supplier<MemoryModuleType<Unit>> ERADICATOR_SHOOT_RECOVERING = MEMORY_MODULE_TYPES.register(
            "eradicator_recovering",
            () -> new MemoryModuleType<>(Optional.of(Unit.CODEC))
    );

}
