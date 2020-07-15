package com.hotmail.rodytellezreyes.directo;

import com.hotmail.rodytellezreyes.directo.commands.DirectoCommand;
import com.hotmail.rodytellezreyes.directo.commands.DirectosCommand;
import com.hotmail.rodytellezreyes.directo.config.ConfigManager;
import com.hotmail.rodytellezreyes.directo.config.ConfigUtils;
import com.hotmail.rodytellezreyes.directo.listeners.PlayerListeners;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Directo extends JavaPlugin {
    private ConfigUtils configUtils = new ConfigUtils();

    public static Map<String, String> activePlayers = new HashMap<>();
    private static FileConfiguration config;
    private static Directo plugin;

    @Override
    public void onEnable() {
        plugin = this;
        loadFiles();
        loadCommands();
        loadListeners();
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage("◆⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌◆");
        getServer().getConsoleSender().sendMessage("◆Plugin: " + getDescription().getName());
        getServer().getConsoleSender().sendMessage("◆Versión: " + getDescription().getVersion());
        getServer().getConsoleSender().sendMessage("◆Autor: " + getDescription().getAuthors());
        getServer().getConsoleSender().sendMessage("◆Se ha cargado el plugin.");
        getServer().getConsoleSender().sendMessage("◆⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌◆");
        getServer().getConsoleSender().sendMessage("");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage("◆⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌◆");
        getServer().getConsoleSender().sendMessage("◆" + getDescription().getFullName());
        getServer().getConsoleSender().sendMessage("◆Se ha desactivado el plugin.");
        getServer().getConsoleSender().sendMessage("◆⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌⚌◆");
        getServer().getConsoleSender().sendMessage("");
    }
    public void loadCommands() {
        getCommand("directo").setExecutor(new DirectoCommand());
        getCommand("directos").setExecutor(new DirectosCommand());
    }

    public void loadListeners() {
        PluginManager pM = Bukkit.getPluginManager();
        pM.registerEvents(new PlayerListeners(), this);
    }

    public void loadFiles() {
        ConfigManager.get().setup(this,"config");
        config = configUtils.getConfig(this, "config");
    }

    public static Directo getPlugin() {
        return plugin;
    }

    public static FileConfiguration getConfigFile() {
        return config;
    }
}
