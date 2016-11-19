package com.poncethecat.iAdmin.utils;

import com.poncethecat.iAdmin.iAdmin;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class MessageUtils {


    public static void sendMessage(IChannel channel, String message) {
        try {
            channel.sendMessage(message);
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }

}
