package com.poncethecat.iAdmin.commands;


import com.darichey.discord.api.Command;
import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.poncethecat.iAdmin.iAdmin.commands;

public class customCommandCommand {
    //This class also contains the two other custom command related commands, I just thought it might be easier :P
    static String[] aliases1 = new String[]{"removecommand"};
    static Set<String> aliases = new HashSet(Arrays.asList(aliases1));
    public static Command cmd = new Command("addcommand").withAliases(aliases).caseSensitive(false).withDescription("[Mod] This command allows you to add and remove commands! Users can do >customcommands to get a list of commands").withUsage(">addcommand [command name] [command content]").onExecuted((context) -> {
    String[] args = context.getMessage().getContent().replace(iAdmin.registry.getPrefix() + context.getName(), "").split(" ");

        if(args.length == 0) {
            return;
        }

    if(!iAdmin.checkForRole(context.getMessage().getGuild(), context.getMessage().getAuthor(), "233255691203117056")) {
        try {
            context.getMessage().reply("You don't have permission to do that, you piece of shit.");
            return;
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    if(iAdmin.registry.getCommandByName(args[0], true).isPresent()) {
        try {
            context.getMessage().reply("ILLEGAL.");
            return;
        } catch (MissingPermissionsException | DiscordException | RateLimitException e) {
            e.printStackTrace();
        }

    }



    //Addcommand command
    if(context.getName().equals("addcommand ") || context.getName().equals("addcommand")) {

        if(commands.containsKey(args[0])) {
            try {
                context.getMessage().reply("Sorry! This command already exists :(");
                return;
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
            return;
        }

        if(args[0].equals("")) {
            try {
                context.getMessage().reply("You left an empty value for the command content! Retard.");
                return;
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
        }
        StringBuilder toRepeat = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            toRepeat.append(args[i]);
            toRepeat.append(' ');
        }
        commands.put(args[0], toRepeat.toString());
        Writer output;
        try {
            output = new BufferedWriter(new FileWriter("commands.txt", true));
            output.append("\n" + args[0] + " " + toRepeat.toString().trim().replace("\n", "%n"));
            output.close();
        } catch (IOException e) {
            try {
                context.getMessage().reply("There was an error writing to the commands file. Stacktrace: " + e.getStackTrace().toString());
            } catch (MissingPermissionsException | RateLimitException | DiscordException e1) {
                e1.printStackTrace();
            }
        }

        try {
            context.getMessage().reply("You have added the command: " + "`>" + args[0] + "`" + "!");
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    if(context.getName().equals("removecommand") || context.getName().equals("removecommand ")) {

        if(commands.containsKey(args[0])) {
            try {
                iAdmin.removeLineFromFile("commands.txt",args[0] + " " + commands.get(args[0]).replace("\n", "%n"));
            } catch (IOException e) {e.printStackTrace();}
            commands.remove(args[0]);
            try {
                context.getMessage().reply("I have removed the command: `>" + args[0] + "`!");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
        } else {
            try {
                context.getMessage().reply("The command: " + args[0] + " doesn't exist!");
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


