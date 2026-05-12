package com.example.plugin.commands;

import com.example.plugin.utils.Utils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class RtpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            return true;
        }

        if (!player.hasPermission("rtp.use")) {
            player.sendMessage(Utils.colorize("&cYou do not have permission to use this command."));
            return true;
        }

        World world = player.getWorld();

        Random random = new Random();

        int x = random.nextInt(2000) - 1000;
        int z = random.nextInt(2000) - 1000;

        Location location = new Location(world, x, 0, z);

        int y = world.getHighestBlockYAt(location);

        Location safeLocation = new Location(world, x + 0.5, y + 1, z + 0.5);

        player.teleport(safeLocation);

        player.sendMessage(Utils.colorize("&aВы успешно телепортированы!"));

        return true;
    }
}