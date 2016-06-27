package pw.kmp.tracker.trackers.explosion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.trackers.Tracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExplosionTracker extends Tracker {
    private final Map<Block, Player> placedTNT = new HashMap<>();
    private final Map<TNTPrimed, Player> ownerTNT =  new HashMap<>();


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        Map<Block, Player> updated = Maps.newHashMap();
        List<Block> toremove = Lists.newLinkedList();

        for(Block block : event.getBlocks()) {
            Player placer = placedTNT.get(block);
            if(placer != null) {
                toremove.add(block);
                updated.put(block.getRelative(event.getDirection()), placer);
            }
        }

        for(Block block : toremove) {
            Player newPlacer = updated.remove(block);
            placedTNT.put(block, newPlacer);
        }

        for(Map.Entry<Block, Player> entry : updated.entrySet()) {
            placedTNT.put(entry.getKey(), entry.getValue());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {

        if(event.isSticky()) {
            Block newBlock = event.getBlock().getRelative(event.getDirection());
            Block oldBlock = newBlock.getRelative(event.getDirection());
            Player player = placedTNT.get(oldBlock);
            if(player != null) {
                placedTNT.remove(oldBlock);
                placedTNT.put(newBlock, player);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getBlock().getType() == Material.TNT)
            placedTNT.put(event.getBlock(), event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.TNT)
            placedTNT.remove(event.getBlock());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTNTPrimeEvent(ExplosionPrimeEvent event) {
        if(event.getEntity() instanceof TNTPrimed) {
            TNTPrimed tnt = (TNTPrimed) event.getEntity();
            Block block = event.getEntity().getWorld().getBlockAt(event.getEntity().getLocation());
            if(block != null) {
                Player placer = placedTNT.remove(block);
                if (placer != null) {
                    ownerTNT.put(tnt, placer);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTNTChain(EntityExplodeEvent event) {
        // Transfer ownership to chain-activated TNT
        if(event.getEntity() instanceof TNTPrimed) {
            Player owner = ownerTNT.remove((TNTPrimed)event.getEntity());
            if(owner != null) {
                for(Block block : event.blockList()) {
                    if(block.getType() == Material.TNT) {
                        placedTNT.put(block, owner);
                    }
                }
            }
        }
    }

    @Override
    public DamageCause resolveDamage(Player player, EntityDamageEvent event, Lifetime lifetime) {
        if(event instanceof EntityDamageByEntityEvent) {
            if(((EntityDamageByEntityEvent) event).getDamager() instanceof TNTPrimed) {
                Player owner = ownerTNT.get(((EntityDamageByEntityEvent) event).getDamager());
                if(owner != null)
                    return new ExplosionCause.EntityExplosionCause(owner);
                return new ExplosionCause();
            }
        }
        return null;
    }
}
