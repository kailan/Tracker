package pw.kmp.tracker;

import org.bukkit.entity.Player;
import pw.kmp.tracker.lifetime.Lifetime;
import pw.kmp.tracker.lifetime.LifetimeManager;

public class Lifetimes {

    private static LifetimeManager lifetimes;

    public static LifetimeManager getManager() {
        if (lifetimes == null) lifetimes = new LifetimeManager();
        return lifetimes;
    }

    public static Lifetime getLifetime(Player player) {
        return getManager().getLifetime(player);
    }


}
