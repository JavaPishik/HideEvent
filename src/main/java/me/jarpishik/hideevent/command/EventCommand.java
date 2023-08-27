package me.jarpishik.hideevent.command;

import me.jarpishik.hideevent.game.GameManager;
import me.jarpishik.hideevent.utilities.Command;
import me.jarpishik.hideevent.utilities.TextUtilities;
import org.bukkit.command.CommandSender;

public class EventCommand implements Command {
    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(TextUtilities.format("[&dHideEvent&f] Неверное количество аргументов."));
            return;
        }

        if (args[0].equals("start")) {
            if (!sender.isOp()) {
                sender.sendMessage(TextUtilities.format("[&dHideEvent&f] У вас нет прав на данную команду."));
                return;
            }
            if (GameManager.isGameOffline()) {
                GameManager.startGame();
                sender.sendMessage(TextUtilities.format("[&dHideEvent&f] Произвожу запуск..."));
                return;
            }
            sender.sendMessage(TextUtilities.format("[&dHideEvent&f] Игра уже активна."));
            return;
        }

        if (args[0].equals("stop")) {
            if (!sender.isOp()) {
                sender.sendMessage(TextUtilities.format("[&dHideEvent&f] У вас нет прав на данную команду."));
                return;
            }
            if (!GameManager.isGameOffline()) {
                GameManager.stopGame(null);
                sender.sendMessage(TextUtilities.format("[&dHideEvent&f] Произвожу остановку..."));
                return;
            }
            sender.sendMessage(TextUtilities.format("[&dHideEvent&f] Не найдено активных игр."));
            return;
        }

        sender.sendMessage(TextUtilities.format("[&dHideEvent&f] Не удалось найти команду."));
    }
}
