package com.poncethecat.iAdmin;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;



public class TriviaGame {


    public IChannel channel;
    public File triviafile;


    public TriviaGame(IChannel iChannel, File triviafile) {
        this.channel = iChannel;
        this.triviafile = triviafile;
    }

    HashMap<String, String[]> questions = new HashMap<>();


    public void startGame()  {
            if(triviafile.exists()) {
                File file = new File("commands.txt");
                Scanner scanner = null;
                try {
                    scanner = new Scanner(file);
                    while(scanner.hasNextLine()) {
                        String questionandanswer[] = scanner.nextLine().split("|");
                        if(questionandanswer.length > 2) {

                        }

                    }
                } catch (FileNotFoundException e) {
                 //   channel.sendMessage("That trivia set doesn't exist!");
                }


            }
        }


    }

