package xyz.mrfrostydev.welcomeplayer.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class WelcomeplayerCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        LiteralCommandNode<CommandSourceStack> welcomeplayer = dispatcher.register(Commands.literal("wp")
                .requires((p) -> p.hasPermission(2))
        );
    }
}
