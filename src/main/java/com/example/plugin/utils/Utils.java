package com.example.plugin.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    /**
     * Преобразует строку с кодами & или MiniMessage в форматированную строку (Legacy).
     * Используется для обычных методов sendMessage(String).
     */
    public static String color(String message) {
        if (message == null || message.isEmpty()) return "";

        // Если строка содержит теги MiniMessage (например, <red> или <gradient>)
        if (message.contains("<") && message.contains(">")) {
            Component component = MiniMessage.miniMessage().deserialize(message);
            return LegacyComponentSerializer.legacyAmpersand().serialize(component).replace("&", "§");
        }

        // Стандартная поддержка кодов через &
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Преобразует строку в современный Component (Adventure API).
     * Рекомендуется для новых версий Paper/Spigot 1.21.
     */
    public static Component componentColor(String message) {
        if (message == null || message.isEmpty()) return Component.empty();

        if (message.contains("<") && message.contains(">")) {
            return MiniMessage.miniMessage().deserialize(message);
        }

        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    /**
     * Обработка списков строк (например, для Lore предметов или многострочных сообщений).
     */
    public static List<String> colorList(List<String> messages) {
        return messages.stream().map(Utils::color).collect(Collectors.toList());
    }
}