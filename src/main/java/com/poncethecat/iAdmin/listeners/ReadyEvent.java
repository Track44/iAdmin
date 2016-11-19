package com.poncethecat.iAdmin.listeners;

import com.darichey.discord.api.CommandRegistry;
import com.poncethecat.iAdmin.commands.*;
import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.api.events.EventSubscriber;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.poncethecat.iAdmin.iAdmin.registry;
import static com.poncethecat.iAdmin.iAdmin.welcomemessage;


public class ReadyEvent {

    @EventSubscriber
    public void onReady(sx.blah.discord.handle.impl.events.ReadyEvent event) throws FileNotFoundException {
        registry = CommandRegistry.getForClient(event.getClient());
        registry.setPrefix(">");
        registry.register(cleanupCommand.cmd);
        registry.register(cancelTaskCommand.cmd);
        registry.register(colorCommand.cmd);
        registry.register(scheduleCommand.cmd);
        registry.register(userInfoCommand.cmd);
        registry.register(customCommandCommand.cmd);
        registry.register(helpCommand.cmd);
        registry.register(statusCommand.cmd);
        registry.register(listCommand.cmd);
        registry.register(evalCommand.cmd);
        registry.register(acceptCommand.cmd);
        System.out.println("Ready!");
        File file = new File("commands.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            StringBuilder toRepeat = new StringBuilder();
            String[] commandandresponse = scanner.nextLine().split(" ");
            for (int i = 1; i < commandandresponse.length; i++) {
                toRepeat.append(commandandresponse[i]);
                toRepeat.append(' ');
            }
            if(commandandresponse[0].equals("") || commandandresponse[0].equals(" ")) {
                System.out.println("backing out, this is an empty line");
            } else {
                iAdmin.commands.put(commandandresponse[0], toRepeat.toString().trim().replace("%n", "\n"));
                System.out.println("Added command: " + commandandresponse[0] + " with response: " + toRepeat.toString().trim());
            }
        }
        System.out.println("Done adding commands!");
        scanner.close();

        System.out.println("Importing welcome message: ");
        File file2 = new File("welcome.txt");
        Scanner scanner2 = new Scanner(file2);
        StringBuilder welcomeMessage = new StringBuilder();
        while (scanner2.hasNextLine()) {
            welcomeMessage.append(scanner2.nextLine() + "\n");
        }
        scanner2.close();
        System.out.print("Welcome message imported");
        welcomemessage = welcomeMessage.toString();





    }

}
