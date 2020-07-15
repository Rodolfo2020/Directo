package com.hotmail.rodytellezreyes.directo.commands;

import com.hotmail.rodytellezreyes.directo.Directo;
import net.kyori.text.TextComponent;
import net.kyori.text.adapter.bukkit.SpigotTextAdapter;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DirectosCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("Necesitas ser un jugador!"));
            return false;
        }
        if (args.length != 0) {
            sender.sendMessage(color("&eUsa /directos."));
        } else {
            Player player = (Player) sender;
            TextComponent message = TextComponent.of(color("&9&lDirectos activos:"));
            for (String yTName : Directo.activePlayers.keySet()) {
                message = message.append(TextComponent.newline())
                        .append(TextComponent.of(color("  &7&l- &e" + yTName + " ")))
                        .append(TextComponent.of("[Click para ver]").color(TextColor.LIGHT_PURPLE).clickEvent(ClickEvent.openUrl(Directo.activePlayers.get(yTName))));
            }
            player.spigot().sendMessage(SpigotTextAdapter.toBungeeCord(message));
        }
        return false;
    }

    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
