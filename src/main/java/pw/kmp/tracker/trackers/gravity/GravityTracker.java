package pw.kmp.tracker.trackers.gravity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import pw.kmp.tracker.TrackerPlugin;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.damage.EntityCause;
import pw.kmp.tracker.event.TrackedDamageEvent;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.trackers.Tracker;
import pw.kmp.tracker.util.BlockChecker;

import java.util.Map;
import java.util.WeakHashMap;

// TODO: Spleefing
public class GravityTracker extends Tracker {

    public static final long MAX_SUPPORT_TIME   = 20L; // maximum time supported before fall cancel
    public static final long MAX_KNOCKBACK_TIME = 20L; // maximum time for knockback to cause fall

    private final Map<Player, Fall> falls = new WeakHashMap<>();

    protected void scheduleFallCheck(final Fall fall, final long delay) {
        Bukkit.getScheduler().runTaskLater(TrackerPlugin.get(), () -> checkFall(fall), delay);
    }

    protected void checkFall(Fall fall) {
        if (BlockChecker.isSupported(fall.getVictim()) && !BlockChecker.isInLava(fall.getVictim())) {
            fall.setFalling(false);
            if (fall.getGroundTime() > MAX_SUPPORT_TIME) {
                falls.remove(fall.getVictim(), fall);
            } else {
                fall.setGroundTime(fall.getGroundTime() + 1);
                scheduleFallCheck(fall, 1);
            }
        } else {
            fall.setGroundTime(0);
            fall.setFalling(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(TrackedDamageEvent event) {
        if (!(event.getDamage().getCause() instanceof EntityCause)) return;
        if (falls.containsKey(event.getDamage().getDamaged())) return;

        DamageCause cause = event.getDamage().getCause();
        Player victim = event.getDamage().getDamaged();
        Fall fall = new Fall(victim, ((EntityCause) cause).getEntity(), event.getDamage(), Fall.Type.ATTACK);
        falls.put(victim, fall);

        fall.setFalling(!BlockChecker.isSupported(victim));
        if (!fall.isFalling()) scheduleFallCheck(fall, MAX_KNOCKBACK_TIME);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;
        if (!falls.containsKey(event.getPlayer())) return;

        Player player = event.getPlayer();
        if (BlockChecker.isSupported(player)) {
            Fall fall = falls.get(player);
            fall.setFalling(false);
            fall.setGroundTime(1);
            scheduleFallCheck(fall, 1);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        falls.remove(event.getEntity());
    }

    @Override
    public DamageCause resolveDamage(Player player, EntityDamageEvent event, Lifetime lifetime) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL) && !falls.containsKey(player)) {
            return new FallCause();
        } else if (falls.containsKey(player)) {
            switch (event.getCause()) {
                case FALL:
                case VOID:
                case LAVA:
                case SUICIDE:
                    return getFallDamage(player);
                case FIRE_TICK:
                    return BlockChecker.isInLava(player) ? getFallDamage(player) : null;
            }
        }
        return null;
    }

    protected DamageCause getFallDamage(Player player) {
        Fall fall = falls.get(player);
        return new FallCause.EntityFallCause(fall.getCause(), fall.getAttacker());
    }

}
