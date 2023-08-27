package me.jarpishik.hideevent.utilities;

import me.jarpishik.hideevent.HideEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerUtilities {
    public static void runLater(double seconds, Runnable run) {
        new BukkitRunnable() {@Override public void run() {
                run.run();
        }}.runTaskLater(HideEvent.getCurrentPlugin(), (long) (seconds * 20));
    }
}
