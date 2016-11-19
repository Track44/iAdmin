package com.poncethecat.iAdmin.commands;


import com.darichey.discord.api.Command;
import com.poncethecat.iAdmin.iAdmin;
import com.poncethecat.iAdmin.utils.Scheduler;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class cancelTaskCommand {
    static String[] aliases = {};
    public static Command cmd = new Command("canceltask").caseSensitive(false).withDescription("[Mod] This command allows you cancel tasks made with >schedule!").withUsage(">canceltask [task name]").onExecuted((context) -> {
    String[] args = context.getArgs();
        if(!iAdmin.checkForRole(context.getMessage().getGuild(), context.getMessage().getAuthor(),"233252981175353345")) {
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
        if(Scheduler.isTask(args[0])) {
            Scheduler.cancelTask(args[0]);
            try {
                context.getMessage().reply("Canceled!");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
        } else {
            try {
                context.getMessage().reply("This is not a task!");
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


