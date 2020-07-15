package com.hotmail.rodytellezreyes.directo.listeners;

import com.hotmail.rodytellezreyes.directo.Directo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Directo.activePlayers.remove(p.getName());
    }
}
