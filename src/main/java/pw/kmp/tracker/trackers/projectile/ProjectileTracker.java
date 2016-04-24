package pw.kmp.tracker.trackers.projectile;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.trackers.Tracker;

import java.util.Map;
import java.util.WeakHashMap;

public class ProjectileTracker extends Tracker {

    private final Map<Projectile, Location> locations = new WeakHashMap<>();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof LivingEntity) {
            locations.put(event.getEntity(), ((LivingEntity) event.getEntity().getShooter()).getLocation());
        }
    }

    @Override
    public DamageCause resolveDamage(Player player, EntityDamageEvent event, Lifetime lifetime) {
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            if (e.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) e.getDamager();
                if (!(projectile.getShooter() instanceof LivingEntity)) return null;

                Location loc = locations.get(projectile);
                Double distance = null;
                if (loc != null) distance = event.getEntity().getLocation().distance(loc);
                return new ProjectileCause(projectile, (LivingEntity) projectile.getShooter(), distance);
            }
        }
        return null;
    }

}
