package pw.kmp.tracker.trackers.lava;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.metadata.FixedMetadataValue;
import pw.kmp.tracker.TrackerPlugin;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.trackers.Tracker;


public class LavaDamageTracker extends Tracker {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBucketEmptyEvent(PlayerBucketEmptyEvent event) {
        if(event.getBucket() == Material.LAVA_BUCKET) {
            Location clicked = event.getBlockClicked().getLocation();
            BlockFace f = event.getBlockFace();
            Location lava = new Location(clicked.getWorld(), clicked.getX() + f.getModX(), clicked.getY() + f.getModY(), clicked.getZ() + f.getModZ());

            lava.getBlock().setMetadata("placer", new FixedMetadataValue(TrackerPlugin.get(), event.getPlayer().getName()));

        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockFromToEvent(BlockFromToEvent event) {
        if(event.getBlock().getType() == Material.LAVA || event.getBlock().getType() == Material.STATIONARY_LAVA)
            if(event.getBlock().hasMetadata("placer")) {
                event.getToBlock().setMetadata("placer", new FixedMetadataValue(TrackerPlugin.get(), event.getBlock().getMetadata("placer").get(0).asString()));
            }
    }

    @Override
    public DamageCause resolveDamage(Player player, EntityDamageEvent event, Lifetime lifetime) {
        if(event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            if(event.getEntity().getLocation().getBlock().hasMetadata("placer")) {
                return new LavaDamageCause.EntityLavaDamageCause(event.getEntity().getLocation().getBlock(), Bukkit.getPlayer(event.getEntity().getLocation().getBlock().getMetadata("placer").get(0).asString()));
            } else {
                return new LavaDamageCause(player.getLocation().getBlock());
            }
        }
        return null;
    }

}
