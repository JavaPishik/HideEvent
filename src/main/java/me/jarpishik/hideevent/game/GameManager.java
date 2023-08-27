package me.jarpishik.hideevent.game;

import org.bukkit.entity.Player;

public class GameManager {
    private static Game currentGame;

    public static void startGame() {
        currentGame = new Game();
        currentGame.preLaunch();
    }

    public static void stopGame(Player winner) {
        currentGame.stop(winner);
        currentGame = null;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static boolean isGameOffline() {
        return currentGame == null;
    }
}
