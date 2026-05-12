package com.example.plugin.commands;

import com.example.plugin.RTP;
import com.example.plugin.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class RtpCommand implements CommandExecutor {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        RTP plugin = RTP.getInstance();
        FileConfiguration msg = plugin.getMessages();
        String prefix = msg.getString("prefix");

        // Обработка /rtp reload
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("rtp.admin")) {
                sender.sendMessage(Utils.color(prefix + msg.getString("no-permission")));
                return true;
            }
            plugin.reloadPluginConfigs();
            sender.sendMessage(Utils.color(prefix + msg.getString("reload-success")));
            return true;
        }

        // Логика основной телепортации
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Utils.color(msg.getString("player-only")));
            return true;
        }

        if (!player.hasPermission("rtp.use")) {
            player.sendMessage(Utils.color(prefix + msg.getString("no-permission")));
            return true;
        }

        // Проверка кулдауна
        int cooldownTime = plugin.getConfig().getInt("rtp-settings.cooldown");
        if (cooldowns.containsKey(player.getUniqueId())) {
            long secondsLeft = ((cooldowns.get(player.getUniqueId()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
            if (secondsLeft > 0) {
                player.sendMessage(Utils.color(prefix + msg.getString("cooldown").replace("%time%", String.valueOf(secondsLeft))));
                return true;
            }
        }

        // Телепортация с использованием радиуса из конфига
        int radius = plugin.getConfig().getInt("rtp-settings.radius");
        
        double x = (Math.random() * (radius * 2)) - radius;
        double z = (Math.random() * (radius * 2)) - radius;
        double y = player.getWorld().getHighestBlockYAt((int) x, (int) z) + 1;

        Location loc = new Location(player.getWorld(), x, y, z);
        player.teleport(loc);

        String coords = (int)x + ", " + (int)y + ", " + (int)z;
        player.sendMessage(Utils.color(prefix + msg.getString("success").replace("%coords%", coords)));
        
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
        return true;
    }
}