package pw.kmp.tracker.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import pw.kmp.tracker.event.TrackedDamageEvent;

public class DebugListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(final TrackedDamageEvent event) {
        Bukkit.broadcastMessage(event.getDamage().getDamaged().getName() + " damaged for " + event.getDamage().getDamage() + " raw half hearts at " + event.getDamage().getDamaged().getLocation() + " info: " + event.getDamage() + " cancelled?" + (event.isCancelled() ? "yes" : "no"));
    }
}
