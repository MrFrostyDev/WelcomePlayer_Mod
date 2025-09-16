package xyz.mrfrostydev.welcomeplayer;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import xyz.mrfrostydev.welcomeplayer.registries.*;

@Mod(WelcomeplayerMain.MOD_ID)
public class WelcomeplayerMain {
    public static final String MOD_ID = "welcomeplayer";
    private static final Logger LOGGER = LogUtils.getLogger();

    public WelcomeplayerMain(IEventBus modEventBus, ModContainer modContainer) {

        BlockRegistry.register(modEventBus);
        DataAttachmentRegistry.register(modEventBus);
        DataComponentRegistry.register(modEventBus);
        MenuRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);
        EntityRegistry.register(modEventBus);
        GlobalLootModifierRegistry.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
    }

}
