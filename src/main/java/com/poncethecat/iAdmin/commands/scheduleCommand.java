package com.poncethecat.iAdmin.commands;


import com.darichey.discord.api.Command;
import com.poncethecat.iAdmin.iAdmin;
import com.poncethecat.iAdmin.utils.iAdminTask;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

import java.util.concurrent.TimeUnit;

public class scheduleCommand {
    static String[] aliases = {};
    public static Command cmd = new Command("schedule").caseSensitive(false).withDescription("[Mod] This command allows you to schedule a task!").withUsage(">schedule [task name] [time in seconds] [channel id] [message to be repeated]").onExecuted((context) -> {
    String[] args = context.getArgs();

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

        String taskname = args[0];
        Long time = Long.parseLong(args[1]) * 1000;
        String channel = args[2];
        StringBuilder toRepeat = new StringBuilder();
        for (int i = 3; i < args.length; i++) {
            toRepeat.append(args[i]);
            toRepeat.append(' ');
        }

        new iAdminTask(taskname) {
            @Override
            public void run() {
                RequestBuffer.request(() -> {
                    try {
                        iAdmin.client.getChannelByID(channel).sendMessage(toRepeat.toString().trim());
                    } catch (MissingPermissionsException e) {
                        e.printStackTrace();
                    } catch (DiscordException e) {
                        e.printStackTrace();
                    }
                });
            }
        }.repeat(time, time);
        try {
            context.getMessage().reply("Your task has been scheduled!");
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
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


