package com.poncethecat.iAdmin.commands;


import com.darichey.discord.api.Command;
import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.poncethecat.iAdmin.iAdmin.registry;

public class helpCommand {
    static String[] aliases1 = new String[]{};
    static Set<String> aliases = new HashSet(Arrays.asList(aliases1));
    public static Command cmd = new Command("help").caseSensitive(false).withDescription("Help command.").withUsage(">help [command name]").onExecuted((context) -> {
        String[] args = context.getArgs();
        StringBuilder builder = new StringBuilder();
        registry.getCommands().forEach((cmd) -> builder.append(">" + cmd.getName() + " - " + cmd.getDescription() + "\n"));
        if (args.length == 0) {
            try {
                context.getMessage().getAuthor().getOrCreatePMChannel().sendMessage("```" + builder.toString() + "```\n" + "You can get more info on a command by doing >help (command name)!");
                context.getMessage().reply("PM'd you a list of commands.");
            } catch (RateLimitException | DiscordException | MissingPermissionsException e) {
                e.printStackTrace();
            }
        } else {
            if (registry.getCommandByName(args[0], true).isPresent()) {
                try {
                    context.getMessage().getChannel().sendMessage("```python\n" + "Command Name: " + args[0] + "\n"
                            + "Command Usage: " + registry.getCommandByName(args[0], true).get().getUsage() + "\n"
                            + "Command Description: " + registry.getCommandByName(args[0], true).get().getDescription() + "```");
                } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    context.getMessage().reply("This command doesn't exist!");
                } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                    e.printStackTrace();
                }
            }
        }
    }).onFailure((context, err) -> {
        try {
            context.getMessage().reply("Something went wrong! D: Here's what happened: " + err.toString());
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {

            if(e instanceof MissingPermissionsException) {
                try {
                    iAdmin.client.getOrCreatePMChannel(context.getMessage().getGuild().getOwner()).sendMessage("Hey! Someone in your guild (" + context.getMessage().getAuthor().mention() + ") tried to use one of my commands, and for some reason I had permission to read the messages but I couldn't respond! \n Please kindly revoke my read permissions from that channel or allow me write permissions! Thanks :)");
                } catch (MissingPermissionsException | RateLimitException | DiscordException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    });

}


