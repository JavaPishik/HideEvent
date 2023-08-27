package me.jarpishik.hideevent.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GameStorage {
    public static final Location lobbyLocation = new Location(Bukkit.getWorld("world"), 0.5, -60, 0.5);
    public static final Location spawnLocation = new Location(Bukkit.getWorld("world"), 7, -60, 18);
    public static final Location gameSpawnLocation = new Location(Bukkit.getWorld("world"), -17.5, -60, 1.5);
    public static final Location seekerLobbyLocation = new Location(Bukkit.getWorld("world"), -8, -60, 13);

    public static void applyEffectToPlayer(Player player, PotionEffectType type, double seconds, int power, boolean effects) {
        player.addPotionEffect(new PotionEffect(type, (int) (seconds * 20), power, effects, effects, effects));
    }

    public static Player getRandomPlayer() {
        return Bukkit.getOnlinePlayers().stream().skip((int) (Bukkit.getOnlinePlayers().size() * Math.random())).findFirst().orElse(null);
    }
}
