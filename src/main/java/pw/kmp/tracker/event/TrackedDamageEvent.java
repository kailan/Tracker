package pw.kmp.tracker.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pw.kmp.tracker.damage.Damage;

public class TrackedDamageEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private final Damage damage;
    private boolean cancelled = false;

    public TrackedDamageEvent(Damage damage) {
        this.damage = damage;
    }

    public Damage getDamage() {
        return damage;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

}
