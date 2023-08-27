package me.jarpishik.hideevent.utilities;

import org.bukkit.ChatColor;

public class TextUtilities {
    public static String format(String toFormat) {
        return ChatColor.translateAlternateColorCodes('&', toFormat);
    }
}
