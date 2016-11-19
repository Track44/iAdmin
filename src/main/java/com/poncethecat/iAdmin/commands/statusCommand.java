package com.poncethecat.iAdmin.commands;


import com.darichey.discord.api.Command;
import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class statusCommand {


    public static Command cmd = new Command("status").caseSensitive(false).withDescription("[Mod] This command allows you to cleanup messages!").withUsage(">status [game, stream] [stream URL if applicable] [status]").onExecuted((context) -> {
    String[] args = context.getArgs();
    IMessage msg = context.getMessage();

        if(!iAdmin.checkForRole(context.getMessage().getGuild(), context.getMessage().getAuthor(), "233252981175353345")) {
            try {
                context.getMessage().reply("You don't have permission to do that, you piece of shit.");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
            return;
        }

        if(args.length == 0) {
            try {
                context.getMessage().reply("Please provide args! You can run >help (command name) to find out more about this command!");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
            return;
        }

        if(args[0].equals("game")) {
            StringBuilder toRepeat = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                toRepeat.append(args[i]);
                toRepeat.append(' ');
            }
            msg.getClient().changeStatus(Status.game(toRepeat.toString().trim()));
            try {
                msg.reply("Game status set to: " + toRepeat.toString().trim());
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
            return;
        }

        if(args[0].equals("stream")) {
            StringBuilder toRepeat = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                toRepeat.append(args[i]);
                toRepeat.append(' ');
            }
            msg.getClient().changeStatus(Status.stream(toRepeat.toString().trim(), args[1]));
            try {
                msg.reply("Streaming URL set to: " + args[1] + ". Status: " + toRepeat.toString().trim());
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
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


