package com.hotmail.rodytellezreyes.directo.commands;

import com.hotmail.rodytellezreyes.directo.Directo;
import com.hotmail.rodytellezreyes.directo.utils.actionbar.ActionBar;
import net.kyori.text.TextComponent;
import net.kyori.text.adapter.bukkit.SpigotTextAdapter;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DirectoCommand implements CommandExecutor {
    FileConfiguration configFile = Directo.getConfigFile();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Necesitas ser un jugador!");
            return false;
        }
        if (sender.hasPermission("directo.use")) {
            if (args.length == 0) {
                for (String name : Directo.activePlayers.keySet()) {
                    if (sender.getName().equalsIgnoreCase(name)) {
                        anunciarDirecto(name, Directo.activePlayers.get(name));
                        return false;
                    }
                }
                sender.sendMessage(color("&cNo estas en directo. &eUsa /directo [link]"));

            } else if (args.length == 1) {
                if (args[0].startsWith("http://") || args[0].startsWith("https://")) {
                    if (Directo.activePlayers.containsKey(sender.getName())) {
                        Directo.activePlayers.replace(sender.getName(), args[0]);
                    } else {
                        Directo.activePlayers.put(sender.getName(), args[0]);
                    }
                    anunciarDirecto(sender.getName(), args[0]);
                } else {
                    sender.sendMessage(color("&cError! El link debe comenzar con http:// o https://"));
                }
            } else {
                sender.sendMessage(color("&cError! &eUsa /directo [link]"));
            }
        } else {
            sender.sendMessage(color(configFile.getString("no-perms")));
        }

        return false;
    }

    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void anunciarDirecto(String playerName, String link) {
        ActionBar actionBar = new ActionBar();
        TextComponent linkMsg = LegacyComponentSerializer.legacy().deserialize(configFile.getString("messages.link-message"),'&').clickEvent(ClickEvent.openUrl(link));
        TextComponent message = LegacyComponentSerializer.legacy().deserialize(configFile.getString("messages.prefix"),'&');
        String msgString = color(configFile.getString("messages.message-structure").replaceAll("\\{player}", playerName));
        while (msgString.endsWith("  ")) {
            msgString = msgString.substring(0, msgString.length() - 1);
        }
        if (msgString.contains("{linkmessage}")) {
            String[] msgSplit = msgString.split("\\{linkmessage}");
            int nLinks = StringUtils.countMatches(msgString, "{linkmessage}");
            if (msgString.startsWith("\\{link-message}")) {
                message = message.append(linkMsg);
                nLinks -= 1;
                for (int i = 0; i < nLinks; i++) {
                    message = message.append(LegacyComponentSerializer.legacy().deserialize(msgSplit[i]))
                            .append(linkMsg);
                }
            } else {
                for (int i = 0; i < nLinks; i++) {
                    message = message.append(LegacyComponentSerializer.legacy().deserialize(msgSplit[i]))
                            .append(linkMsg);
                }
            }
        } else {
            message = message.append(LegacyComponentSerializer.legacy().deserialize(msgString));
        }
        Bukkit.getServer().spigot().broadcast(SpigotTextAdapter.toBungeeCord(message));
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            actionBar.sendActionBar(p, color(configFile.getString("messages.actionbar-message").replaceAll("\\{playeryt}", playerName).replaceAll("\\{player}", p.getName())));
        }
    }
}
