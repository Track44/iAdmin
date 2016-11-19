package com.poncethecat.iAdmin;
//Jenkins test secsdcadsf
//asdfasdfasdfasfds last time testest
import com.darichey.discord.api.CommandRegistry;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.poncethecat.iAdmin.commands.*;
import com.poncethecat.iAdmin.listeners.CustomCommandListener;
import com.poncethecat.iAdmin.listeners.JoinListener;
import com.poncethecat.iAdmin.listeners.ReadyEvent;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;



public class iAdmin {
    public static IDiscordClient client;
    public static CommandRegistry registry;
    public static String welcomemessage = "Welcome %user%";
    public static HashMap<String, String > commands = new HashMap<>();
    public static void main(String[] args) throws DiscordException, InterruptedException {
        client = new ClientBuilder().withToken(args[0]).setMaxReconnectAttempts(10).build();
        client.getDispatcher().registerListener(new ReadyEvent());
        client.getDispatcher().registerListener(new CustomCommandListener());
        client.getDispatcher().registerListener(new JoinListener());
        client.login();

    }





    public static boolean checkForRole(IGuild guild, IUser user, String roleid) {
        AtomicBoolean hasRole = new AtomicBoolean();
        hasRole.set(false);
        IRole role1 = guild.getRoleByID(roleid);
        client.getUserByID(user.getID()).getRolesForGuild(guild).forEach((role) -> {
            if(role1.getPosition() <= role.getPosition()) {
                hasRole.set(true);
            }
        });
        return hasRole.get();
    }

    public static int differenceInDays(LocalDateTime start, LocalDateTime end) {
        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();
        if (start.toLocalTime().isAfter(end.toLocalTime())) {
            startDate = startDate.plusDays(1);
        }
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static boolean isParsable(String input){
        boolean parsable = true;
        try{
            Integer.parseInt(input);
        }catch(NumberFormatException e){
            parsable = false;
        }
        return parsable;
    }


    public static String[] split(int parts, String tosplit) {
        Iterable<String> iterator = Splitter.fixedLength(tosplit.length() / parts).split(tosplit);
        String[] stringthatissplit = Iterables.toArray(iterator, String.class);
        return stringthatissplit;
    }

    //I'm lazy, therefore this is taken from StackOverflow, I don't remember where from, but I didn't make this.
    public static void removeLineFromFile(String file, String lineToRemove) throws IOException {
        File inputFile = new File(file);
        File tempFile = new File("temp" + file);

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));


        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(inputFile);
    }

}


