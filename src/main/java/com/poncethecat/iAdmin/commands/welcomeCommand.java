package com.poncethecat.iAdmin.commands;


import com.darichey.discord.api.Command;
import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class welcomeCommand {
    static String[] aliases1 = new String[]{""};
    static Set<String> aliases = new HashSet(Arrays.asList(aliases1));
    public static Command cmd = new Command("").withAliases(aliases).caseSensitive(false).withDescription("[Mod] This command allows you to cleanup messages!").onExecuted((context) -> {
    String[] args = context.getArgs();
        if(args.length == 0) {
            try {
                context.getMessage().reply("Please provide args! You can run >help (command name) to find out more about this command!");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
            return;
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


