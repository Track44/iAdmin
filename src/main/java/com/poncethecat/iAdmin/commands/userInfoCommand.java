package com.poncethecat.iAdmin.commands;


import com.darichey.discord.api.Command;
import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.time.LocalDateTime;

public class userInfoCommand {
    //Yes, I know, this isn't the greatest. I'm copying and pasting code atm, I'll tidy it up later :)

    public static Command cmd = new Command("userinfo").caseSensitive(false).withDescription("This command allows you to get information on a user!").withUsage(">userinfo [@mention of the user]").onExecuted((context) -> {
        String[] args = context.getArgs();
        IMessage msg = context.getMessage();
        if(args.length == 0) {
            IUser toperform = msg.getAuthor();
            StringBuilder builder = new StringBuilder();
            toperform.getRolesForGuild(msg.getGuild()).forEach((role) -> builder.append(role.getName() + ", "));


            if(context.getMessage().getAuthor().getID().equals("186627661219495936")) {
                LocalDateTime poncejoin = LocalDateTime.of(2016, 8, 10, 18,25);
                try {
                    msg.getChannel().sendMessage("User info for: " + toperform.getName() + ":\n" +
                            "`Name#Discrim: `" + toperform.getName() + "#" + toperform.getDiscriminator() + "\n" +
                            "`ID: `" + toperform.getID() + "\n" +
                            "`Current Game: `" + toperform.getStatus().getStatusMessage() + "\n" +
                            "`Server Join Date: `" + poncejoin.toLocalDate().toString() + " (" + Integer.toString(iAdmin.differenceInDays(poncejoin, LocalDateTime.now())) + " days since joined)" + "\n" +
                            "`Account Creation Date: ` " + toperform.getCreationDate().toLocalDate().toString() + "(" + Integer.toString(iAdmin.differenceInDays(toperform.getCreationDate(), LocalDateTime.now())) + " days since creation)" + "\n" +
                            "`Roles: `" + builder.toString().replace("@everyone", "").trim() + "\n" +
                            "`Avatar: `" + toperform.getAvatarURL());
                    return;
                } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                    e.printStackTrace();
                    return;
                }
            }


            try {
                msg.getChannel().sendMessage("User info for: " + toperform.getName() + ":\n" +
                        "`Name#Discrim: `" + toperform.getName() + "#" + toperform.getDiscriminator() + "\n" +
                        "`ID: `" + toperform.getID() + "\n" +
                        "`Current Game: `" + toperform.getStatus().getStatusMessage() + "\n" +
                        "`Server Join Date: `" + msg.getGuild().getJoinTimeForUser(toperform).toLocalDate().toString() + " (" + Integer.toString(iAdmin.differenceInDays(msg.getGuild().getJoinTimeForUser(toperform), LocalDateTime.now())) + " days since joined)" + "\n" +
                        "`Account Creation Date: ` " + toperform.getCreationDate().toLocalDate().toString() + "(" + Integer.toString(iAdmin.differenceInDays(toperform.getCreationDate(), LocalDateTime.now())) + " days since creation)" + "\n" +
                        "`Roles: `" + builder.toString().replace("@everyone", "").trim() + "\n" +
                        "`Avatar: `" + toperform.getAvatarURL());
                return;
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
                return;
            }
        } else {
            IUser toperform = msg.getMentions().get(0);
            StringBuilder builder = new StringBuilder();
            toperform.getRolesForGuild(msg.getGuild()).forEach((role) -> builder.append(role.getName() + ", "));

            if(toperform.getID().equals("186627661219495936")) {
                LocalDateTime poncejoin = LocalDateTime.of(2016, 8, 10, 18,25);
                try {
                    msg.getChannel().sendMessage("User info for: " + toperform.getName() + ":\n" +
                            "`Name#Discrim: `" + toperform.getName() + "#" + toperform.getDiscriminator() + "\n" +
                            "`ID: `" + toperform.getID() + "\n" +
                            "`Current Game: `" + toperform.getStatus().getStatusMessage() + "\n" +
                            "`Server Join Date: `" + poncejoin.toLocalDate().toString() + " (" + Integer.toString(iAdmin.differenceInDays(poncejoin, LocalDateTime.now())) + " days since joined)" + "\n" +
                            "`Account Creation Date: ` " + toperform.getCreationDate().toLocalDate().toString() + "(" + Integer.toString(iAdmin.differenceInDays(toperform.getCreationDate(), LocalDateTime.now())) + " days since creation)" + "\n" +
                            "`Roles: `" + builder.toString().replace("@everyone", "").trim() + "\n" +
                            "`Avatar: `" + toperform.getAvatarURL());
                    return;
                } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                    e.printStackTrace();
                    return;
                }
            }
//
            try {
                msg.getChannel().sendMessage("User info for: " + toperform.getName() + ":\n" +
                        "`Name#Discrim: `" + toperform.getName() + "#" + toperform.getDiscriminator() + "\n" +
                        "`ID: `" + toperform.getID() + "\n" +
                        "`Current Game: `" + toperform.getStatus().getStatusMessage() + "\n" +
                        "`Server Join Date: `" + msg.getGuild().getJoinTimeForUser(toperform).toString() + " (" + Integer.toString(iAdmin.differenceInDays(msg.getGuild().getJoinTimeForUser(toperform), LocalDateTime.now())) + " days since joined)" + "\n" +
                        "`Account Creation Date: ` " + toperform.getCreationDate().toString() + "(" + Integer.toString(iAdmin.differenceInDays(toperform.getCreationDate(), LocalDateTime.now())) + " days since creation)" + "\n" +
                        "`Roles: `" + builder.toString().replace("@everyone", "").trim() + "\n" +
                        "`Avatar: `" + toperform.getAvatarURL());
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


