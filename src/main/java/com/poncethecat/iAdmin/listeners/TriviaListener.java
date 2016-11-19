package com.poncethecat.iAdmin.listeners;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class TriviaListener {

    AtomicBoolean triviaGameGoingOn = new AtomicBoolean(false);
    AtomicBoolean nextQuestion = new AtomicBoolean(false);


    @EventSubscriber
    public void onMessage(MessageReceivedEvent event) {

    }


}
