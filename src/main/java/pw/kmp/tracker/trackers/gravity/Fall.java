package pw.kmp.tracker.trackers.gravity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import pw.kmp.tracker.damage.Damage;

public class Fall {

    private final Player victim;
    private final Damage cause;
    private final LivingEntity attacker;
    private final Type type;

    private long groundTime = 0L;
    private boolean isFalling = false;

    public Fall(Player victim, LivingEntity attacker, Damage cause, Type type) {
        this.victim = victim;
        this.attacker = attacker;
        this.cause = cause;
        this.type = type;
    }

    public Player getVictim() {
        return victim;
    }

    public Damage getCause() {
        return cause;
    }

    public LivingEntity getAttacker() {
        return attacker;
    }

    public Type getType() {
        return type;
    }

    public long getGroundTime() {
        return groundTime;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setGroundTime(long groundTime) {
        this.groundTime = groundTime;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public enum Type { ATTACK, SPLEEF; }

}
