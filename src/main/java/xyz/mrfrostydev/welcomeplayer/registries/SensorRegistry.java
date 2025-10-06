package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.eradicator.EradicatorNearestAttackableSensor;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.handibot.HandibotNearestAttackableSensor;

import java.util.function.Supplier;

public class SensorRegistry {
    private static final DeferredRegister<SensorType<?>> SENSOR_TYPE = DeferredRegister.create(Registries.SENSOR_TYPE, WelcomeplayerMain.MOD_ID);

    public static void register(IEventBus event){
        SENSOR_TYPE.register(event);
    }

    public static final Supplier<SensorType<HandibotNearestAttackableSensor>> HANDIBOT_NEAREST_ATTACKABLE_SENSOR = SENSOR_TYPE.register(
            "handibot_nearest_attackble",
            () -> new SensorType<>(HandibotNearestAttackableSensor::new)
    );

    public static final Supplier<SensorType<EradicatorNearestAttackableSensor>> ERADICATOR_NEAREST_ATTACKABLE_SENSOR = SENSOR_TYPE.register(
            "eradicator_nearest_attackble",
            () -> new SensorType<>(EradicatorNearestAttackableSensor::new)
    );

}
