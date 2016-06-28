package pw.kmp.tracker.trackers.anvil;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFallEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.trackers.Tracker;

import java.util.HashMap;
import java.util.Iterator;

public class AnvilTracker extends Tracker{
    private final HashMap<Block, LivingEntity> placedAnvils = new HashMap<>();
    private final HashMap<FallingBlock, LivingEntity> ownedAnvils  =new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getBlock().getType() == Material.ANVIL)
            placedAnvils.put(event.getBlock(), event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        placedAnvils.remove(event.getBlock());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockExplode(EntityExplodeEvent event) {
        // Remove all blocks that are destroyed from explosion
        Iterator<Block> it = event.blockList().iterator();
        while(it.hasNext()) {
            Block block = it.next();
            placedAnvils.remove(block);
        }
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAnvilFall(BlockFallEvent event) {
        if(event.getBlock().getType() == Material.ANVIL) {
            LivingEntity placer = placedAnvils.get(event.getBlock());
            if(placer != null)
                ownedAnvils.put(event.getEntity(), placedAnvils.get(event.getBlock()));

        }
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAnvilLand(EntityChangeBlockEvent event) {
        if(event.getEntityType() == EntityType.FALLING_BLOCK && event.getTo() == Material.ANVIL) {
            LivingEntity owner = ownedAnvils.get((FallingBlock) event.getEntity());
            if(owner != null) {
                placedAnvils.put(event.getBlock(), ownedAnvils.get(event.getEntity()));
                ownedAnvils.put((FallingBlock) event.getEntity(), null);
            }
        }
    }

    @Override
    public DamageCause resolveDamage(Player player, EntityDamageEvent damageEvent, Lifetime lifetime) {
        if(damageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) damageEvent;

            if(event.getDamager() instanceof FallingBlock) {
                FallingBlock anvil = (FallingBlock) event.getDamager();
                LivingEntity  owner = ownedAnvils.get(anvil);
                if(owner != null)
                    return new AnvilCause.EntityAnvilCause(anvil, owner);
                else
                    return new AnvilCause(anvil);
            }
        }

        return null;
    }
}
