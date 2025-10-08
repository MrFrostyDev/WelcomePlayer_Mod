package xyz.mrfrostydev.welcomeplayer;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import xyz.mrfrostydev.welcomeplayer.data.datagen.DataInitialize;
import xyz.mrfrostydev.welcomeplayer.registries.*;

@Mod(WelcomeplayerMain.MOD_ID)
public class WelcomeplayerMain {
    public static final String MOD_ID = "welcomeplayer";
    public static final Logger LOGGER = LogUtils.getLogger();

    public WelcomeplayerMain(IEventBus modEventBus, ModContainer modContainer) {

        BlockRegistry.register(modEventBus);
        DataAttachmentRegistry.register(modEventBus);
        DataComponentRegistry.register(modEventBus);
        MenuRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);
        EntityRegistry.register(modEventBus);
        MobEffectRegistry.register(modEventBus);
        GlobalLootModifierRegistry.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
        MemoryModuleRegistry.register(modEventBus);
        SensorRegistry.register(modEventBus);
        SoundEventRegistry.register(modEventBus);
        ParticleRegistry.register(modEventBus);

        modEventBus.addListener(DataInitialize::gatherData);
    }

}
