package pw.kmp.tracker.trackers.meelee;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
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

                Material weaponMaterial;
                ItemStack held = attacker.getEquipment().getItemInHand();
                if (held != null) {
                    weaponMaterial = held.getType();
                } else {
                    weaponMaterial = Material.AIR;
                }

                return new MeleeCause(attacker, weaponMaterial);
            }
        }
        return null;
    }
}
