package pw.kmp.tracker.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.event.TrackedDeathEvent;
import pw.kmp.tracker.trackers.anvil.AnvilCause;
import pw.kmp.tracker.trackers.dispenser.DispensedProjectileCause;
import pw.kmp.tracker.trackers.explosion.ExplosionCause;
import pw.kmp.tracker.trackers.gravity.FallCause;
import pw.kmp.tracker.trackers.meelee.MeleeCause;
import pw.kmp.tracker.trackers.potion.PotionDamageCause;
import pw.kmp.tracker.trackers.projectile.ProjectileCause;
import tc.oc.minecraft.api.entity.Player;

public class DeathListener implements Listener {
    public enum DamageCauses {
        AnvilCause, EntityAnvilCause,
        BlockCause,
        DispensedProjectileCause,
        ExplosionCause, EntityExplosionCause,
        FallCause, EntityFallCause,
        LavaDamageCause,
        LightningCause,
        MeleeCause,
        MobAttackCause,
        PotionDamageCause, EntityPotionDamageCause,
        ProjectileCause,
        VoidCause,
        SuffocationCause,
        WaterCause,
        UnknownCause

    }

    private void setMessage(PlayerDeathEvent event, String newMessage, Object... names) {
        for(int i = 0 ; i < names.length ; i++)
            names[i] = ChatColor.RED.toString() + names[i] + ChatColor.GRAY;

        event.setDeathMessage( String.format(ChatColor.GRAY + newMessage, names));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTrackedDeathEvent(TrackedDeathEvent trackedDeathEvent) {

        DamageCauses causeSwitch = DamageCauses.valueOf(trackedDeathEvent.getCause().getCause().getClass().getSimpleName());
        PlayerDeathEvent event = trackedDeathEvent.getEvent();
        DamageCause cause = trackedDeathEvent.getCause().getCause();

        // Commented out means that our tracker would add either the same amount or less info than MC's default messages
        switch(causeSwitch) {
            case EntityAnvilCause:
                setMessage(event,  "%s was squashed by %s falling anvil", event.getEntity().getDisplayName(), ((AnvilCause.EntityAnvilCause) cause).getEntity().getName() + "'s");
                if(event.getEntity().equals(((AnvilCause.EntityAnvilCause) cause).getEntity()))
                    setMessage(event,  "%s was squashed by their own falling anvil", event.getEntity().getDisplayName());
                break;
            case DispensedProjectileCause:
                if(((DispensedProjectileCause) cause).getEntity() != null)
                    setMessage(event,  "%s was shot by %s dispenser"  + "(%s)", event.getEntity().getDisplayName(), ((DispensedProjectileCause) cause).getEntity().getName() + "'s", ChatColor.GRAY.toString() + ((DispensedProjectileCause) cause).getDistance().intValue());
                else
                    setMessage(event,  "%s was shot by a dispenser " + "(%s)", event.getEntity().getDisplayName(), ChatColor.GRAY.toString() + ((DispensedProjectileCause) cause).getDistance().intValue());
                break;
            case EntityExplosionCause:
                setMessage(event,  "%s was blown up by %s", event.getEntity().getDisplayName(), ((ExplosionCause.EntityExplosionCause) cause).getEntity().getName());
                break;
            case MeleeCause:
                if(((MeleeCause) cause).getEntity() instanceof Player)
                    setMessage(event,  "%s was slain by %s %s", event.getEntity().getDisplayName(), ((MeleeCause) cause).getEntity().getName() + "'s", ChatColor.GRAY + ((MeleeCause) cause).getWeapon().getType().toString().toLowerCase().replace("_", " "));
                else {
                    setMessage(event, "%s was slain by a %s", event.getEntity().getDisplayName(), ChatColor.GRAY + ((MeleeCause) cause).getEntity().getName());
                }
                break;
            case EntityPotionDamageCause:
                setMessage(event,  "%s was killed with %s witchcraft", event.getEntity().getDisplayName(), ((PotionDamageCause.EntityPotionDamageCause) cause).getEntity().getName() + "'s");
                break;
            case ProjectileCause:
                setMessage(event, "%s was shot by %s (%s)", event.getEntity().getDisplayName(), ((ProjectileCause) cause).getEntity().getName(),  ChatColor.GRAY.toString() +((ProjectileCause) cause).getDistance().intValue());
                break;
            case EntityFallCause: // TODO: Get this working as @kblks expects it to work. Faults: Falling credit is not timed, and burning -> triggers fall cause
                //setMessage(event,  "%s fell at the hands of " + (((FallCause.EntityFallCause) cause).getEntity() instanceof Player ? "" : "a ") + "%s", event.getEntity().getDisplayName(), ((FallCause.EntityFallCause) cause).getEntity().getName());
                //break;
            case ExplosionCause:
                //setMessage(event,  "%s was blown up.");
            case AnvilCause:
                //setMessage(event,  "%s was squashed by a falling anvil.");
            case BlockCause:
                //setMessage(event,  "%s was killed by a " + ((BlockCause) cause).getDamager().getBlock().getType().toString().toLowerCase());
            case FallCause:
                //setMessage(event,  "%s fell from a height to their death.");
            case MobAttackCause:
                // MC's mob tracking is pretty good :)
                //setMessage(event,  "%s was slain by " + ((MeleeCause) cause).getEntity().getName() + "'s " + ((MeleeCause) cause).getWeapon().getType().toString().toLowerCase().replace("_", ""));
            case LavaDamageCause:
                //setMessage(event,  "%s burned to death in lava.");
            case LightningCause:
                //setMessage(event,  "%s was struck by lightning and electrocuted to death.");
            case PotionDamageCause:
            case VoidCause:
            case SuffocationCause:
            case WaterCause:
            case UnknownCause:
                event.setDeathMessage(event.getDeathMessage().replace(event.getEntity().getDisplayName(), ChatColor.RED + event.getEntity().getDisplayName() + ChatColor.GRAY));
                break;
        }
    }
}
