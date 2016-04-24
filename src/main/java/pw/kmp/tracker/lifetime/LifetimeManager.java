package pw.kmp.tracker.lifetime;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.WeakHashMap;

public class LifetimeManager {

    private final Map<Player, Lifetime> lifetimes = new WeakHashMap<>();

    public Lifetime getLifetime(Player player) {
        return lifetimes.get(player);
    }

    public void removeLifetime(Player player) {
        lifetimes.remove(player);
    }

    public void addLifetime(Player player, Lifetime lifetime) {
        lifetimes.put(player, lifetime);
    }

}
