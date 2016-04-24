package pw.kmp.tracker.damage;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.joda.time.Instant;
import pw.kmp.tracker.trackers.UnknownCause;

public class Damage {

    private final EntityDamageEvent event;
    private final Instant time;
    private double damage;
    private Player damaged;
    private DamageCause cause;

    public Damage(EntityDamageEvent event, Instant time, double damage, Player damaged) {
        this.event = event;
        this.time = time;
        this.damage = damage;
        this.damaged = damaged;
        this.cause = new UnknownCause();
    }

    public EntityDamageEvent getEvent() {
        return event;
    }

    public Instant getTime() {
        return time;
    }

    public double getDamage() {
        return damage;
    }

    public Player getDamaged() {
        return damaged;
    }

    public DamageCause getCause() {
        return cause;
    }

    public void setCause(DamageCause cause) {
        this.cause = cause;
    }

}
