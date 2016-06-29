package pw.kmp.tracker.trackers.potion;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.trackers.Tracker;

public class PotionTracker extends Tracker {

    @Override
    public DamageCause resolveDamage(Player p, EntityDamageEvent damageEvent, Lifetime lifetime) {
        if (!(damageEvent.getEntity() instanceof Player)) return null;
        if (damageEvent.getCause() == EntityDamageEvent.DamageCause.WITHER || damageEvent.getCause() == EntityDamageEvent.DamageCause.POISON) {
            Player player = (Player) damageEvent.getEntity();

            if (damageEvent.getCause() == EntityDamageEvent.DamageCause.POISON)
                return new PotionDamageCause(getPotionEffect(player, PotionEffectType.POISON));
            else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.WITHER)
                return new PotionDamageCause(getPotionEffect(player, PotionEffectType.WITHER));

        }
        if (damageEvent instanceof EntityDamageByEntityEvent) {

            EntityDamageByEntityEvent sub = (EntityDamageByEntityEvent) damageEvent;
            if (sub.getDamager() instanceof ThrownPotion) {
                ThrownPotion thrownPotion = (ThrownPotion) sub.getDamager();
                PotionEffect potionEffect = null;
                for (PotionEffect pe : thrownPotion.getEffects()) {
                    if (pe.getType().equals(PotionEffectType.HARM)) {
                        potionEffect = pe;
                        break;
                    }
                }

                if (potionEffect != null) {

                    if (thrownPotion.getShooter() instanceof LivingEntity) {
                        return new PotionDamageCause.EntityPotionDamageCause(potionEffect, (LivingEntity) thrownPotion.getShooter());
                    }
                    return new PotionDamageCause(potionEffect);
                }
            }
        }
        return null;
    }

    private PotionEffect getPotionEffect(Player player, PotionEffectType potionEffectType) {
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (potionEffect.getType().equals(potionEffectType)) {
                return potionEffect;
            }
        }
        return null;
    }
}
