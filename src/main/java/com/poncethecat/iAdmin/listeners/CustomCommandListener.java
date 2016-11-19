package com.poncethecat.iAdmin.listeners;

import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.api.internal.Requests;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

public class CustomCommandListener {

    @EventSubscriber
    public void onMessage(MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        if(event.getMessage().getContent().startsWith(">") && !iAdmin.registry.getCommandByName(event.getMessage().getContent().substring(1), true).isPresent()) {
            if(iAdmin.commands.containsKey(event.getMessage().getContent().substring(1))) {
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage(iAdmin.commands.get(event.getMessage().getContent().substring(1)));
                    } catch (MissingPermissionsException | DiscordException e) {
                        e.printStackTrace();
                    }
                });
                return;
            } else {
                return;
            }
        }
    }

}
