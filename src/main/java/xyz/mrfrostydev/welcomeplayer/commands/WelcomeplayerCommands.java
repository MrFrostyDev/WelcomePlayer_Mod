package xyz.mrfrostydev.welcomeplayer.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import xyz.mrfrostydev.welcomeplayer.data.*;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceEventUtil;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;
import xyz.mrfrostydev.welcomeplayer.utils.ObjectiveUtil;

import java.util.List;

public class WelcomeplayerCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        LiteralCommandNode<CommandSourceStack> welcomeplayer = dispatcher.register(Commands.literal("wp")
                .requires((p) -> p.hasPermission(2))
                .then(Commands.literal("interest")
                        .then(Commands.literal("add")
                                .then(Commands.argument("add", IntegerArgumentType.integer())
                                        .executes(context -> audienceInterestCommand(context.getSource(), IntegerArgumentType.getInteger(context, "add"), false, false))
                                )
                        )
                        .then(Commands.literal("set")
                                .then(Commands.argument("set", IntegerArgumentType.integer())
                                        .executes(context -> audienceInterestCommand(context.getSource(), IntegerArgumentType.getInteger(context, "set"), false, true))
                                )
                        )
                        .then(Commands.literal("get")
                                .executes(context -> audienceInterestCommand(context.getSource(), 0, true, false))
                        )
                )
                .then(Commands.literal("mood")
                        .then(Commands.literal("set")
                                .then(Commands.argument("0-4", IntegerArgumentType.integer(0, 4))
                                        .executes(context -> moodCommand(context.getSource(), IntegerArgumentType.getInteger(context, "0-4"), false))
                                )
                        )
                        .then(Commands.literal("get")
                                .executes(context -> moodCommand(context.getSource(), 0, true))
                        )
                )
                .then(Commands.literal("objective")
                        .then(Commands.literal("set")
                                .then(Commands.argument("path", StringArgumentType.string())
                                        .executes(context -> objectiveCommand(context.getSource(), StringArgumentType.getString(context, "path"), false))
                                )
                        )
                        .then(Commands.literal("get")
                                .executes(context -> objectiveCommand(context.getSource(), "", true))
                        )
                )
                .then(Commands.literal("event")
                        .then(Commands.literal("set")
                                .then(Commands.argument("path", StringArgumentType.string())
                                        .executes(context -> eventCommand(context.getSource(), StringArgumentType.getString(context, "path"), false))
                                )
                        )
                        .then(Commands.literal("get")
                                .executes(context -> eventCommand(context.getSource(), "", true))
                        )
                )
        );
    }

    private static int audienceInterestCommand(CommandSourceStack source, int value, boolean isGet, boolean isSet) {
        ServerLevel svlevel = source.getLevel();
        AudienceData data = AudienceUtil.getAudienceData(svlevel);
        if(isGet){
            source.sendSuccess(() -> Component.translatable("commands.welcomeplayer.interest.get", data.getInterest()), true);
        }
        else{
            String s = (isSet) ? "set" : "add";
            if (isSet) AudienceUtil.setInterestRaw(svlevel, value); else AudienceUtil.addInterestRaw(svlevel, value);
            source.sendSuccess(() -> Component.translatable("commands.welcomeplayer.interest." + s, value, data.getInterest()), true);
        }
        return value;
    }
    
    private static int moodCommand(CommandSourceStack source, int value, boolean isGet) {
        ServerLevel svlevel = source.getLevel();
        AudienceData data = AudienceUtil.getAudienceData(svlevel);
        if(isGet){
            source.sendSuccess(() -> Component.translatable("commands.welcomeplayer.mood.get", data.getMood().toString()), true);
        }
        else{
            AudienceMood mood = switch (value){
                case 1 -> AudienceMood.HAPPY;
                case 2 -> AudienceMood.SAD;
                case 3 -> AudienceMood.ANGRY;
                case 4 -> AudienceMood.CRUEL;
                default -> AudienceMood.NEUTRAL;
            };
            AudienceUtil.setMood(svlevel, mood);
            AudienceUtil.syncToClients(svlevel);
            source.sendSuccess(() -> Component.translatable("commands.welcomeplayer.mood.set", AudienceUtil.getMood(svlevel)), true);
        }
        return value;
    }

    private static int objectiveCommand(CommandSourceStack source, String eventname, boolean isGet) {
        ServerLevel svlevel = source.getLevel();
        PlayerObjective event = PlayerObjective.NOTHING;

        if(isGet){
            source.sendSuccess(() -> Component.translatable("commands.welcomeplayer.objective.get", ObjectiveUtil.getGoingObjective(svlevel).id(), ObjectiveUtil.getProgress(svlevel)), true);
        }
        else{
            List<Holder.Reference<PlayerObjective>> allEvents = ObjectiveUtil.getAllObjectivesAsReference(svlevel);
            for(Holder.Reference<PlayerObjective> holder : allEvents){
                if(eventname.matches(holder.getKey().location().getPath())){
                    event = holder.value();
                }
            }

            ObjectiveUtil.setGoingObjective(svlevel, event);
            AudienceUtil.syncToClients(svlevel);
            source.sendSuccess(() -> Component.translatable("commands.welcomeplayer.objective.set", ObjectiveUtil.getGoingObjective(svlevel).id()), true);
        }

        return 0;
    }

    private static int eventCommand(CommandSourceStack source, String eventname, boolean isGet) {
        ServerLevel svlevel = source.getLevel();
        AudienceEvent event = AudienceEvent.NOTHING;

        if(isGet){
            source.sendSuccess(() -> Component.translatable("commands.welcomeplayer.event.get", AudienceEventUtil.getGoingEvent(svlevel).getName()), true);
        }
        else{
            List<Holder.Reference<AudienceEvent>> allEvents = AudienceEventManager.getAllEventsAsReference(svlevel);
            for(Holder.Reference<AudienceEvent> holder : allEvents){
                if(eventname.matches(holder.getKey().location().getPath())){
                    event = holder.value();
                }
            }

            AudienceUtil.pickMood(svlevel);
            AudienceEventUtil.setGoingEvent(svlevel, event);
            AudienceUtil.restartPhaseShift(svlevel);
            AudienceUtil.syncToClients(svlevel);
            source.sendSuccess(() -> Component.translatable("commands.welcomeplayer.event.set", AudienceEventUtil.getGoingEvent(svlevel).getName()), true);
        }

        return 0;
    }
}
