package com.poncethecat.iAdmin.commands;


import com.darichey.discord.api.Command;
import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RoleBuilder;

import java.awt.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class colorCommand {
    static String[] aliases1 = new String[]{"colour", "color2", "colour2"};
    static Set<String> aliases = new HashSet(Arrays.asList(aliases1));
    public static Command cmd = new Command("color").withAliases(aliases).caseSensitive(false).withDescription("This command allows you to change your color!").withUsage(">color [color number] | A list of colors are here: https://track44.moe/colors").onExecuted((context) -> {
        IMessage msg = context.getMessage();
        String[] args = context.getArgs();
        AtomicBoolean isDone = new AtomicBoolean();
        isDone.set(false);
        if(args.length == 0) {
            try {
                context.getMessage().reply("Please provide args! You can run >help (command name) to find out more about this command!");
                return;
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
            return;
        }


        if(!iAdmin.isParsable(args[0])) {
            try {
                context.getMessage().reply("Please provide a number between 1 and 95!");
                return;
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
                return;
            }
        }

        if(Integer.parseInt(args[0]) <= 95) {
            AtomicBoolean hasRole = new AtomicBoolean(false);
            final IRole[] aRole = new IRole[1];
            context.getMessage().getAuthor().getRolesForGuild(context.getMessage().getGuild()).forEach((role) -> {
                if(role.getName().contains("color")) {
                    hasRole.set(true);
                    aRole[0] = role;
                }
            });

            if (hasRole.get()) {
                try {
                    context.getMessage().getAuthor().removeRole(aRole[0]);
                    context.getMessage().getAuthor().addRole(context.getMessage().getGuild().getRolesByName("Color " + args[0]).get(0));
                    context.getMessage().reply("I have changed your color to color" + args[0]);
                    return;
                } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    context.getMessage().getAuthor().addRole(context.getMessage().getGuild().getRolesByName("Color " + args[0]).get(0));
                    context.getMessage().reply("I have changed your color to color" + args[0]);
                    return;
                } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                    e.printStackTrace();
                }

            }


        } else {
            try {
                context.getMessage().reply("Please pick a number between 1 and 95");
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


