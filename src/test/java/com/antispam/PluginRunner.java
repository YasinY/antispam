package com.antispam;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PluginRunner {
    public static void main(String[] args) throws Exception {
        ExternalPluginManager.loadBuiltin(AntiBeggarPlugin.class);
        RuneLite.main(args);
    }
}
