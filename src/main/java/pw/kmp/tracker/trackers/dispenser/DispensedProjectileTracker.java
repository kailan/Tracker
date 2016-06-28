package pw.kmp.tracker.trackers.dispenser;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEntityEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.projectiles.BlockProjectileSource;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.trackers.Tracker;
import pw.kmp.tracker.trackers.projectile.ProjectileTracker;

import java.util.HashMap;
import java.util.Iterator;

public class DispensedProjectileTracker extends Tracker{
    private final HashMap<Block, LivingEntity> placedDispensers = new HashMap<>();
    private final HashMap<Entity, LivingEntity> ownedEntitys = new HashMap<>();

    private final ProjectileTracker projectileTracker;

    public DispensedProjectileTracker(ProjectileTracker projectileTracker) {
        this.projectileTracker = projectileTracker;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getBlock().getType() == Material.DISPENSER) {
            placedDispensers.put(event.getBlock(), event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        placedDispensers.remove(event.getBlock());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockExplode(EntityExplodeEvent event) {
        // Remove all blocks that are destroyed from explosion
        Iterator<Block> it = event.blockList().iterator();
        while(it.hasNext()) {
            Block block = it.next();
            placedDispensers.remove(block);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDispense(BlockDispenseEntityEvent event) {
        Block block = event.getBlock();
        LivingEntity placer = placedDispensers.get(block);
        if(placer != null) {
            ownedEntitys.put(event.getEntity(), placer);
        }
    }

    @Override
    public DamageCause resolveDamage(Player player, EntityDamageEvent damageEvent, Lifetime lifetime) {

        if(damageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) damageEvent;
            if(event.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) event.getDamager();
                Location launchLocation = projectileTracker.getLaunchLocation(projectile);
                Double projectileDistance = null;
                LivingEntity dispenserOwner = ownedEntitys.get(projectile);
                if(launchLocation != null) projectileDistance = event.getEntity().getLocation().distance(launchLocation);
                if(projectile.getShooter() instanceof BlockProjectileSource) {
                    return new DispensedProjectileCause(projectile, dispenserOwner, projectileDistance, projectile.getShooter());
                }
            }
        }
        return null;
    }

}
