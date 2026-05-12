package com.example.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import com.example.plugin.managers.PluginManager;
import com.example.plugin.listeners.PlayerListener;
import com.example.plugin.commands.RtpCommand;
import com.example.plugin.utils.Utils;

public class RTP extends JavaPlugin {
    
    @Override
    public void onEnable() {
        
        // Initialize managers
        PluginManager.getInstance().initialize();
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        
        // Register commands
        getCommand("rtp").setExecutor(new RtpCommand());
        
        getLogger().info("RTP has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RTP has been disabled!");
    }
    
}