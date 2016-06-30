package pw.kmp.tracker.listeners;

import org.bukkit.event.EventHandler;
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

    private void setMessage(PlayerDeathEvent event, String newMessage) {
        event.setDeathMessage(newMessage);
    }

    @EventHandler
    public void onTrackedDeathEvent(TrackedDeathEvent trackedDeathEvent) {

        DamageCauses causeSwitch = DamageCauses.valueOf(trackedDeathEvent.getCause().getCause().getClass().getSimpleName());
        PlayerDeathEvent event = trackedDeathEvent.getEvent();
        DamageCause cause = trackedDeathEvent.getCause().getCause();

        // Commented out means that our tracker would add either the same amount or less info than MC's default messages
        switch(causeSwitch) {
            case AnvilCause:
                //setMessage(event, event.getEntity().getDisplayName() + " was squashed by a falling anvil.");
                break;
            case EntityAnvilCause:
                setMessage(event, event.getEntity().getDisplayName() + " was squashed by " + ((AnvilCause.EntityAnvilCause) cause).getEntity().getName() + "'s falling anvil.");
                if(event.getEntity().equals(((AnvilCause.EntityAnvilCause) cause).getEntity()))
                    setMessage(event, event.getEntity().getDisplayName() + " was squashed by their own falling anvil.");
                break;
            case BlockCause:
                //setMessage(event, event.getEntity().getDisplayName() + " was killed by a " + ((BlockCause) cause).getDamager().getBlock().getType().toString().toLowerCase());
                break;
            case DispensedProjectileCause:
                if(((DispensedProjectileCause) cause).getEntity() != null)
                    setMessage(event, event.getEntity().getDisplayName() + " was shot by " + ((DispensedProjectileCause) cause).getEntity().getName() + "'s dispenser.");
                else
                    setMessage(event, event.getEntity().getDisplayName() + " was shot by a dispenser.");
                break;
            case ExplosionCause:
                //setMessage(event, event.getEntity().getDisplayName() + " was blown up.");
                break;
            case EntityExplosionCause:
                setMessage(event, event.getEntity().getDisplayName() + " was blown up by " + ((ExplosionCause.EntityExplosionCause) cause).getEntity().getName() + ".");
                break;
            case FallCause:
                //setMessage(event, event.getEntity().getDisplayName() + " fell from a height to their death.");
                break;
            case EntityFallCause:
                if (((FallCause.EntityFallCause) cause).getEntity() instanceof Player) {
                    setMessage(event, event.getEntity().getDisplayName() + " fell at the hands of " + ((FallCause.EntityFallCause) cause).getEntity().getName() + ".");
                } else {
                    setMessage(event, event.getEntity().getDisplayName() + " fell at the hands of a " + ((FallCause.EntityFallCause) cause).getEntity().getName() + ".");
                }
                break;
            case LavaDamageCause:
                //setMessage(event, event.getEntity().getDisplayName() + " burned to death in lava.");
                break;
            case LightningCause:
                //setMessage(event, event.getEntity().getDisplayName() + " was struck by lightning and electrocuted to death.");
                break;
            case MeleeCause:
                setMessage(event, event.getEntity().getDisplayName() + " was slain by " + ((MeleeCause) cause).getEntity().getName() + "'s " + ((MeleeCause) cause).getWeapon().getType().toString().toLowerCase().replace("_", " ") + ".");
                break;
            case MobAttackCause:
                // MC's mob tracking is pretty good :)
                //setMessage(event, event.getEntity().getDisplayName() + " was slain by " + ((MeleeCause) cause).getEntity().getName() + "'s " + ((MeleeCause) cause).getWeapon().getType().toString().toLowerCase().replace("_", ""));
                break;
            case PotionDamageCause:
                break;
            case EntityPotionDamageCause:
                setMessage(event, event.getEntity().getDisplayName() + " was killed with " + ((PotionDamageCause.EntityPotionDamageCause) cause).getEntity().getName() + "'s witchcraft.");
                break;
            case ProjectileCause:
                setMessage(event, event.getEntity().getDisplayName() + " shot by " + ((ProjectileCause) cause).getEntity().getName() + " (" + ((ProjectileCause) cause).getDistance() + ").");
                break;
            case VoidCause:
                break;
            case SuffocationCause:
                break;
            case WaterCause:
                break;
            case UnknownCause:
                break;
        }
    }
}
