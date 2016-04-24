package pw.kmp.tracker.trackers.mob;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.trackers.Tracker;

public class MobTracker extends Tracker {

    @Override
    public DamageCause resolveDamage(Player player, EntityDamageEvent event, Lifetime lifetime) {
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            if (e.getDamager() instanceof Creature) {
                return new MobAttackCause((Creature) e.getDamager());
            }
        }
        return null;
    }

}
