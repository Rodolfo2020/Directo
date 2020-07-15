package com.hotmail.rodytellezreyes.directo.config;

import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigManager {
    private static ConfigManager instance;

    public static ConfigManager get() {
        if (instance == null)
            instance = new ConfigManager();
        return instance;
    }

    public void setup(Plugin p, String configName) {
        File pluginDir = p.getDataFolder();
        File configFile = new File(pluginDir, configName + ".yml");
        if (!configFile.exists())
            p.saveResource(configName + ".yml", false);
    }
}
