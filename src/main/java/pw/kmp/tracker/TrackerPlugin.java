package pw.kmp.tracker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pw.kmp.tracker.listeners.DamageListener;
import pw.kmp.tracker.listeners.DebugListener;
import pw.kmp.tracker.listeners.LifetimeListener;
import pw.kmp.tracker.trackers.TrackerManager;
import pw.kmp.tracker.trackers.block.BlockTracker;
import pw.kmp.tracker.trackers.gravity.GravityTracker;
import pw.kmp.tracker.trackers.mob.MobTracker;
import pw.kmp.tracker.trackers.projectile.ProjectileTracker;

public class TrackerPlugin extends JavaPlugin {

    private static TrackerPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new LifetimeListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        // debug
        Bukkit.getPluginManager().registerEvents(new DebugListener(), this);

        TrackerManager manager = Trackers.getManager();
        manager.registerTracker(new BlockTracker());
        manager.registerTracker(new ProjectileTracker());
        manager.registerTracker(new MobTracker());
        manager.registerTracker(new GravityTracker());

    }

    public static TrackerPlugin get() {
        return instance;
    }

}
