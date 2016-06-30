package pw.kmp.tracker.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.PlayerDeathEvent;
import pw.kmp.tracker.damage.Damage;

public class TrackedDeathEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final PlayerDeathEvent event;
    private final Player player;
    private final Damage cause;

    public TrackedDeathEvent(PlayerDeathEvent event, Player player, Damage cause) {
        this.event = event;
        this.player = player;
        this.cause = cause;
    }

    public PlayerDeathEvent getEvent() {
        return event;
    }

    public Player getPlayer() {
        return player;
    }

    public Damage getCause() {
        return cause;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
