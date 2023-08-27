package me.jarpishik.hideevent.listener;

import me.jarpishik.hideevent.game.GameManager;
import me.jarpishik.hideevent.game.GameStorage;
import me.jarpishik.hideevent.utilities.TextUtilities;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerListener implements Listener {
    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(TextUtilities.format("&d<+> " + event.getPlayer().getName()));
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(TextUtilities.format("&5<-> " + event.getPlayer().getName()));
        if (!GameManager.isGameOffline()) {
            if (event.getPlayer() == GameManager.getCurrentGame().getSeeker()) {
                GameManager.stopGame(null);
                return;
            }
            if (GameManager.getCurrentGame().getPlayersInGame().contains(event.getPlayer())) {
                GameManager.getCurrentGame().getPlayersInGame().remove(event.getPlayer());
                event.getPlayer().teleport(GameStorage.lobbyLocation);
                GameManager.getCurrentGame().check();
            }
        }
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage(TextUtilities.format("&4" + event.getPlayer().getName() + " был найден..."));
        if (!GameManager.isGameOffline()) {
            if (event.getPlayer() == GameManager.getCurrentGame().getSeeker()) {
                GameManager.stopGame(null);
                return;
            }
            if (GameManager.getCurrentGame().getPlayersInGame().contains(event.getPlayer())) {
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
                GameManager.getCurrentGame().check();
            }
        }
    }

    @EventHandler
    private void onInteract(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (GameManager.isGameOffline()) return;
        if (!(event.getRightClicked() instanceof Player)) return;

        if (event.getPlayer().getItemInHand().getType() == Material.ECHO_SHARD) {
            Player targetPlayer = (Player) event.getRightClicked();
            targetPlayer.setHealth(0);
        }
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent event) {
        if (!GameManager.isGameOffline()) {
            event.setCancelled(true);
        }
    }
}
