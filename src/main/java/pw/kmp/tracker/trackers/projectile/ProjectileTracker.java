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
        Location location = event.getEntity().getLocation();
        setLaunchLocation(event.getEntity(), location);
    }

    @Override
    public DamageCause resolveDamage(Player player, EntityDamageEvent event, Lifetime lifetime) {
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            if (e.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) e.getDamager();
                Location loc = getLaunchLocation(projectile);
                Double distance = null;

                if (loc != null) distance = event.getEntity().getLocation().distance(loc);
                if(projectile.getShooter() instanceof LivingEntity) {
                    return new ProjectileCause(projectile, (LivingEntity) projectile.getShooter(), distance);
                }
            }
        }
        return null;
    }

    public Location getLaunchLocation(Projectile projectile) {
        return locations.get(projectile);
    }

    public Location setLaunchLocation(Projectile projectile, Location location) {
        if(location != null) {
            return locations.put(projectile, location);
        } else {
            return locations.remove(projectile);
        }
    }

}
