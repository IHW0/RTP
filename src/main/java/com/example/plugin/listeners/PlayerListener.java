package com.example.plugin.listeners;

import com.example.plugin.RTP;
import com.example.plugin.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Получаем строку из messages.yml через основной класс
        String welcome = RTP.getInstance().getMessages().getString("messages.welcome");
        
        if (welcome != null && !welcome.isEmpty()) {
            event.getPlayer().sendMessage(Utils.color(welcome));
        }
    }
}