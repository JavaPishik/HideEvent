package me.jarpishik.hideevent.utilities;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface Command extends CommandExecutor {
    @Override
    default boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        onExecute(commandSender, strings);
        return true;
    }

    void onExecute(CommandSender sender, String[] args);
}
