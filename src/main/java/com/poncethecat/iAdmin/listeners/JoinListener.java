package com.poncethecat.iAdmin.listeners;

import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.UserJoinEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class JoinListener {


    @EventSubscriber
    public void onNewUser(UserJoinEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        event.getUser().addRole(event.getGuild().getRolesByName("Newbie").get(0));
        iAdmin.client.getChannelByID("242809041003216897").sendMessage(iAdmin.welcomemessage.replace("%user%", event.getUser().mention()));
        event.getGuild().getChannelByID("203376328181940225").sendMessage("```python\n" +
                "Name: " + event.getUser().getName() + "\n" +
                "ID: " + event.getUser().getID() + "\n" +
                "Joined: " + event.getJoinTime().toLocalDate().toString() + "\n" +
                "Created: " + event.getUser().getCreationDate().toLocalDate().toString() + "\n" +
                "Avatar: " + event.getUser().getAvatarURL() +"\n" +
                "```\n");

    }

}
