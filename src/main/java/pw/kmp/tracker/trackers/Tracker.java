package pw.kmp.tracker.trackers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pw.kmp.tracker.TrackerPlugin;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.lifetime.Lifetime;

public abstract class Tracker implements Listener {

    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, TrackerPlugin.get());
    }

    public void disable() {
        HandlerList.unregisterAll(this);
    }

    public abstract DamageCause resolveDamage(Player player, EntityDamageEvent event, Lifetime lifetime);

}
