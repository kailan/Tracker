package pw.kmp.tracker.trackers.lava;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.trackers.Tracker;

public class LavaDamageTracker extends Tracker {

    @Override
    public DamageCause resolveDamage(Player player, EntityDamageEvent event, Lifetime lifetime) {
        if(event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            return new LavaDamageCause();
        }
        return null;
    }
}
