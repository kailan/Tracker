package pw.kmp.tracker.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.joda.time.Instant;
import pw.kmp.tracker.Lifetimes;
import pw.kmp.tracker.Trackers;
import pw.kmp.tracker.damage.Damage;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.event.TrackedDamageEvent;
import pw.kmp.tracker.event.TrackedDeathEvent;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.trackers.Tracker;
import pw.kmp.tracker.trackers.UnknownCause;
import pw.kmp.tracker.trackers.explosion.ExplosionCause;

public class DamageListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        Lifetime lifetime = Lifetimes.getLifetime(player);
        Damage damage = new Damage(event, Instant.now(), event.getDamage(), player);

        DamageCause cause = new UnknownCause();
        for (Tracker t : Trackers.getManager().getTrackers()) {
            DamageCause c = t.resolveDamage(player, event, lifetime);
            if (c != null) {
                cause = c;
                break;
            }
        }
        damage.setCause(cause);
        lifetime.addDamage(damage);

        TrackedDamageEvent tde = new TrackedDamageEvent(damage);
        tde.setCancelled(event.isCancelled());
        Bukkit.getPluginManager().callEvent(tde);
        event.setCancelled(tde.isCancelled());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(PlayerDeathEvent event) {
        Lifetime lifetime = Lifetimes.getLifetime(event.getEntity());
        if (lifetime != null && lifetime.getLastDamage() != null) {
            TrackedDeathEvent tde = new TrackedDeathEvent(event, event.getEntity(), lifetime.getLastDamage());
            Bukkit.getPluginManager().callEvent(tde);
        }
    }

}
