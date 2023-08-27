package me.jarpishik.hideevent;

import me.jarpishik.hideevent.command.EventCommand;
import me.jarpishik.hideevent.game.GameManager;
import me.jarpishik.hideevent.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HideEvent extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommand("event", new EventCommand());
        registerListener(new PlayerListener());
    }

    @Override
    public void onDisable() {
        if (!GameManager.isGameOffline()) GameManager.stopGame(null);
    }

    private void registerCommand(String name, CommandExecutor executor) {
        if (getCommand(name) == null) return;
        getCommand(name).setExecutor(executor);
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public static JavaPlugin getCurrentPlugin() {
        return HideEvent.getPlugin(HideEvent.class);
    }
}
