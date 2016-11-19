package com.poncethecat.iAdmin.currency;


import sx.blah.discord.api.IDiscordClient;


import java.util.HashMap;

public class CurrencyManager {
    private static HashMap<IDiscordClient, CurrencyManager> currencyManagers = new HashMap<>();

    public static CurrencyManager getFromClient(IDiscordClient client)  {
        if(!currencyManagers.containsKey(client)) {
            currencyManagers.put(client, new CurrencyManager());
        }
        return currencyManagers.get(client);
    }

    private CurrencyManager() {}




}
