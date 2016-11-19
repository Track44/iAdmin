package com.poncethecat.iAdmin.commands;


import com.darichey.discord.api.Command;
import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static com.poncethecat.iAdmin.iAdmin.commands;

public class evalCommand {

    static String[] aliases1 = new String[]{"evaluate", "java"};
    static Set<String> aliases = new HashSet(Arrays.asList(aliases1));
    public static Command cmd = new Command("eval").caseSensitive(false).withDescription("[Super Admin] This command allows you to evaluate a javascript statement with pre-defined variables").withUsage(">eval (javascript here)").onExecuted((context) -> {
    String[] args = context.getArgs();
        if(!(context.getMessage().getAuthor().getID().equals("186627661219495936") || context.getMessage().getAuthor().getID().equals("145521851676884992") || context.getMessage().getAuthor().getID().equals("95732219607257088"))) {
            try {
                context.getMessage().reply("You don't have permission to do that, you piece of shit.");
                return;
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
        }
        if(args.length == 0) {
            try {
                context.getMessage().reply("Please provide args! You can run >help (command name) to find out more about this command!");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
            return;
        }



        StringBuilder toRepeat = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            toRepeat.append(args[i]);
            toRepeat.append(' ');
        }



        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.put("client", iAdmin.client);
        engine.put("commands", commands);
        try {
           Object result =  engine.eval(toRepeat.toString());
            RequestBuffer.request(() -> {
                try {
                    context.getMessage().reply((String) result);
                } catch (MissingPermissionsException | DiscordException e) {
                    e.printStackTrace();
                }
            });

        } catch (ScriptException e) {
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


