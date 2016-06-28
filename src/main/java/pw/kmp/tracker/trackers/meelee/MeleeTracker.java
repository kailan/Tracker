package pw.kmp.tracker.trackers.meelee;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.trackers.Tracker;

public class MeleeTracker extends Tracker {
    @Override
    public DamageCause resolveDamage(Player player, EntityDamageEvent event, Lifetime lifetime) {
        if(event instanceof EntityDamageByEntityEvent && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent) event;
            if(entityEvent.getDamager() instanceof LivingEntity) {
                LivingEntity attacker = (LivingEntity) entityEvent.getDamager();
                return new MeleeCause(attacker, attacker.getEquipment().getItemInHand());
            }
        }
        return null;
    }
}
