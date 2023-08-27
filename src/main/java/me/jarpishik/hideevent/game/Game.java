package me.jarpishik.hideevent.game;

import com.destroystokyo.paper.Title;
import me.jarpishik.hideevent.utilities.TextUtilities;
import me.jarpishik.hideevent.utilities.TimerUtilities;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Game {
    private final List<Player> playersInGame = new ArrayList<>();
    private UUID seekerUUID;

    public void preLaunch() {
        seekerUUID = GameStorage.getRandomPlayer().getUniqueId();
        for (Player players: Bukkit.getOnlinePlayers()) {
            playersInGame.add(players);
            players.setGameMode(GameMode.ADVENTURE);
            players.setHealth(20);
            players.getInventory().clear();
            GameStorage.applyEffectToPlayer(players, PotionEffectType.BLINDNESS, 5, 0, false);
            players.playSound(players.getLocation(), Sound.ENTITY_WARDEN_HEARTBEAT, 1f, 1f);
            players.sendTitle(Title.builder().title(TextUtilities.format("&d3")).build());
        }

        TimerUtilities.runLater(1, ()-> {
            for (Player players: playersInGame) {
                players.sendTitle(Title.builder().title(TextUtilities.format("&d2")).build());
                players.playSound(players.getLocation(), Sound.ENTITY_WARDEN_HEARTBEAT, 1f, 1f);
            }
        });
        TimerUtilities.runLater(2, ()-> {
            for (Player players: playersInGame) {
                players.sendTitle(Title.builder().title(TextUtilities.format("&d1")).build());
                players.playSound(players.getLocation(), Sound.ENTITY_WARDEN_HEARTBEAT, 1f, 1f);
            }
        });
        TimerUtilities.runLater(3, ()-> {
            for (Player players: playersInGame) {
                players.sendTitle(Title.builder().title(TextUtilities.format("&d0")).build());
                players.playSound(players.getLocation(), Sound.ENTITY_WARDEN_HEARTBEAT, 1f, 1f);
            }
            start();
        });
    }

    public void start() {
        for (Player players: playersInGame) {
            players.teleport(GameStorage.spawnLocation);
            players.sendTitle(Title.builder().title(TextUtilities.format("&7Вы стали...")).build());
        }
        TimerUtilities.runLater(3, ()-> {
            for (Player players: playersInGame) {
                players.sendMessage(TextUtilities.format("[&dHideEvent&f] " + getSeeker().getName() + " стал &c&lискателем."));
                if (players == getSeeker()) {
                    players.sendTitle(Title.builder().title(TextUtilities.format("&cИСКАТЕЛЕМ")).build());
                    players.playSound(players.getLocation(), Sound.ENTITY_WITHER_DEATH, 100f, 0.5f);
                    players.teleport(GameStorage.seekerLobbyLocation);
                    players.sendMessage(TextUtilities.format("[&dHideEvent&f] Привет, мой дорогой друг! Твоя задача найти всех прячущихся. Через 30 секунд тебя пустят в игру."));
                    players.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 2, false, false, false));
                    players.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 2, false, false, false));
                    ItemStack killItem = new ItemStack(Material.ECHO_SHARD);
                    ItemMeta killItemMeta = killItem.getItemMeta();
                    killItemMeta.setDisplayName(TextUtilities.format("&cПОГЛОТИТЬ"));
                    killItemMeta.setLore(Collections.singletonList(TextUtilities.format("&4Поглощает игрока, по которому вы кликнете ПКМ")));
                    killItem.setItemMeta(killItemMeta);
                    players.getInventory().addItem(killItem);
                }
                else {
                    players.sendTitle(Title.builder().title(TextUtilities.format("&bПРЯЧУЩИМСЯ")).build());
                    players.playSound(players.getLocation(), Sound.ITEM_TOTEM_USE, 100f, 1f);
                    players.teleport(GameStorage.gameSpawnLocation);
                    players.sendMessage(TextUtilities.format("[&dHideEvent&f] Привет, мой дорогой друг! Твоя задача cпратяться так, чтобы тебя не нашли."));
                    players.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 1, false, false, false));
                    players.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1, false, false, false));
                }
            }
        });
        TimerUtilities.runLater(33, ()-> {
            getSeeker().teleport(GameStorage.gameSpawnLocation);
            for (Player players: playersInGame) {
                players.sendMessage(TextUtilities.format("[&dHideEvent&f] &c&lИскатель&f на свободе..."));
            }
            check();
        });
    }

    public void stop(Player winner) {
        for (Player players: playersInGame) {
            players.setGameMode(GameMode.ADVENTURE);
            players.setHealth(20);
            players.getInventory().clear();
            for (PotionEffect effect: players.getActivePotionEffects()) players.removePotionEffect(effect.getType());
            if (winner != null) {
                players.sendMessage(TextUtilities.format("[&dHideEvent&f] &b&l" + winner.getName() + "&f победил!"));
            }
            else {
                players.sendMessage(TextUtilities.format("[&dHideEvent&f] Игра окончена"));
            }
            players.teleport(GameStorage.lobbyLocation);
        }
    }

    public void check() {
        List<Player> hidersAlive = new ArrayList<>();
        for (Player players: playersInGame) {
            if (players != getSeeker() && players.getGameMode() == GameMode.ADVENTURE) {
                hidersAlive.add(players);
            }
        }
        if (hidersAlive.size() == 1) {
            Player winner = hidersAlive.get(0);
            GameManager.stopGame(winner);
        }
        if (hidersAlive.isEmpty()) {
            GameManager.stopGame(null);
        }
    }

    public List<Player> getPlayersInGame() {
        return playersInGame;
    }

    public Player getSeeker() {
        return Bukkit.getPlayer(seekerUUID);
    }

    public UUID getSeekerUUID() {
        return seekerUUID;
    }
}
