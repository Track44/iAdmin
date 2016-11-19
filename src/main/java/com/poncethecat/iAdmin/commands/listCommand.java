package com.poncethecat.iAdmin.commands;


import com.darichey.discord.api.Command;
import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.poncethecat.iAdmin.iAdmin.commands;

public class listCommand {
    static String[] aliases1 = new String[]{"list"};
    static Set<String> aliases = new HashSet(Arrays.asList(aliases1));
    public static Command cmd = new Command("customcommands").withAliases(aliases).caseSensitive(false).withDescription("This allows you to list all custom commands").withUsage(">customcommands").onExecuted((context) -> {
    String[] args = context.getArgs();
        StringBuilder builder = new StringBuilder();
        commands.keySet().forEach((command) -> builder.append(command + ", "));
        try {
            //idek why we have three character limits worth of commands but okay
            String[] stringparts = iAdmin.split(4, builder.toString());
            context.getMessage().getClient().getOrCreatePMChannel(context.getMessage().getAuthor()).sendMessage("Here are all the current commands!\n" + "```" + stringparts[0] + "```");
            context.getMessage().getClient().getOrCreatePMChannel(context.getMessage().getAuthor()).sendMessage("```" + stringparts[1] + "```");
            context.getMessage().getClient().getOrCreatePMChannel(context.getMessage().getAuthor()).sendMessage("```" + stringparts[2] + "```");
            context.getMessage().getClient().getOrCreatePMChannel(context.getMessage().getAuthor()).sendMessage("```" + stringparts[3] + "```");
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
        return;

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


