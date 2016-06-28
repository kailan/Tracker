package pw.kmp.tracker.trackers.block;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.trackers.Tracker;

public class BlockTracker extends Tracker {
    @Override
    public DamageCause resolveDamage(Player player, EntityDamageEvent event, Lifetime lifetime) {
        if(event instanceof EntityDamageByBlockEvent && event.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
            EntityDamageByBlockEvent blockEvent = (EntityDamageByBlockEvent) event;
            return new BlockCause(blockEvent.getDamager().getState());
        }

        return null;
    }
}
