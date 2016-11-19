package com.poncethecat.iAdmin.commands;

import com.darichey.discord.api.Command;

import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.*;

public class cleanupCommand {


    static String[] aliases1 = new String[]{"clean", "prune"};
    static Set<String> aliases = new HashSet(Arrays.asList(aliases1));
    public static Command cmd = new Command("cleanup").withAliases(aliases).caseSensitive(false).withDescription("[Mod] This command allows you to cleanup messages!").withUsage(">cleanup [text, @user mention, or just the number to cleanup] [text if nessasary] [number of messages to clear]").onExecuted((context) -> {
        String args[] = context.getArgs();

        if(!iAdmin.checkForRole(context.getMessage().getGuild(), context.getMessage().getAuthor(), "233256428616286209")) {
            try {
                context.getMessage().reply("GTFO. You aren't a god yet.");
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


        //Remove user messages!
        if(args[0].startsWith("<")) {
            IUser removemessagesfrom = context.getMessage().getMentions().get(0);
            Integer numberofmessagestoremove = Integer.parseInt(args[1]);
            int counter = 0;
            List<IMessage> messagesToBulkRemove = new ArrayList<IMessage>();
            for(IMessage message : context.getMessage().getChannel().getMessages()) {
                if(!(counter > numberofmessagestoremove)) {
                    if(message.getAuthor().equals(removemessagesfrom)) {
                        messagesToBulkRemove.add(message);
                        counter++;
                    }
                }
            }
            if(messagesToBulkRemove.isEmpty()) {
                try {
                    context.getMessage().reply("This user hasn't chatted recently!");
                } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                    e.printStackTrace();
                }
                return;
            }
            messagesToBulkRemove.add(context.getMessage());
            try {
                context.getMessage().reply(args[1] + " messages from " + removemessagesfrom.mention() + "have been removed!");
                context.getMessage().getChannel().getMessages().bulkDelete(messagesToBulkRemove);
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
        }

        //Remove specific text
        if(args[0].equals("text")) {
            String texttoremove = args[1];
            int numbertoremove = Integer.parseInt(args[2]);
            int counter = 0;
            List<IMessage> messagestoBulkRemove = new ArrayList<IMessage>();
            for(IMessage message : context.getMessage().getChannel().getMessages()) {
                if(!(counter > numbertoremove)) {
                    if(message.getContent().contains(texttoremove) || message.getContent().equals(texttoremove)) {
                        messagestoBulkRemove.add(message);
                        counter++;
                    }
                }
            }
            messagestoBulkRemove.remove(context.getMessage());
            if(messagestoBulkRemove.isEmpty()) {
                try {
                    context.getMessage().reply("No messages contain this!");
                } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                    e.printStackTrace();
                }
                return;
            }
            messagestoBulkRemove.add(context.getMessage());
            try {
                context.getMessage().reply("All messages with that contents have been removed!");
                context.getMessage().getChannel().getMessages().bulkDelete(messagestoBulkRemove);
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }

        }

        //Remove just a number of messages
        if(iAdmin.isParsable(args[0])) {
            Integer messagestoclear = Integer.parseInt(args[0]);
            int counter = 0;
            List<IMessage> messagestoremove = new ArrayList<>();
            for(IMessage message : context.getMessage().getChannel().getMessages()) {
                if(!(counter > messagestoclear)) {
                    messagestoremove.add(message);
                    counter++;
                }
            }
            messagestoremove.add(context.getMessage());
            try {
                context.getMessage().reply("Removed " + Integer.toString(messagestoclear) + " messages!");
                context.getMessage().getChannel().getMessages().bulkDelete(messagestoremove);
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
