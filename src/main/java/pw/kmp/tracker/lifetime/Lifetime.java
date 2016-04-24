package pw.kmp.tracker.lifetime;

import org.joda.time.Instant;
import pw.kmp.tracker.damage.Damage;
import pw.kmp.tracker.damage.DamageCause;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Lifetime {

    private Instant start;
    private List<Damage> damage;

    public Lifetime() {
        start = Instant.now();
        damage = new ArrayList<>();
    }

    public Instant getStart() {
        return start;
    }

    public List<Damage> getDamage() {
        return damage;
    }

    public Damage getLastDamage(Class<? extends DamageCause> cause) {
        for (ListIterator<Damage> i = damage.listIterator(damage.size()); i.hasPrevious();) {
            Damage d = i.previous();
            if (d.getCause().getClass().isAssignableFrom(cause)) {
                return d;
            }
        }
        return null;
    }

    public Damage getLastDamage() {
        if (damage.isEmpty()) return null;
        return damage.get(damage.size()-1);
    }

    public void addDamage(Damage d) {
        damage.add(d);
    }

}
