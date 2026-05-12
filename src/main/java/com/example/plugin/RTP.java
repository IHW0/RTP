package com.example.plugin;

import com.example.plugin.commands.RtpCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class RTP extends JavaPlugin {
    private static RTP instance;
    private FileConfiguration messagesConfig;
    private File messagesFile;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig(); // сохраняет config.yml
        createMessagesConfig(); // создаёт messages.yml

        getCommand("rtp").setExecutor(new RtpCommand());
    }

    // Логика создания и загрузки messages.yml
    private void createMessagesConfig() {
        messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void reloadPluginConfigs() {
        reloadConfig(); // перезагружает config.yml
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile); // перезагружает messages.yml
    }

    public FileConfiguration getMessages() {
        return messagesConfig;
    }

    public static RTP getInstance() {
        return instance;
    }
}