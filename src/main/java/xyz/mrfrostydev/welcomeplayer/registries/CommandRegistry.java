package xyz.mrfrostydev.welcomeplayer.registries;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.commands.WelcomeplayerCommands;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class CommandRegistry {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        var commandDispatcher = event.getDispatcher();

        WelcomeplayerCommands.register(commandDispatcher);
    }
}
