package pw.kmp.tracker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pw.kmp.tracker.listeners.DamageListener;
import pw.kmp.tracker.listeners.DebugListener;
import pw.kmp.tracker.listeners.LifetimeListener;
import pw.kmp.tracker.trackers.TrackerManager;
import pw.kmp.tracker.trackers.explosion.ExplosionTracker;
import pw.kmp.tracker.trackers.block.BlockTracker;
import pw.kmp.tracker.trackers.anvil.AnvilTracker;
import pw.kmp.tracker.trackers.gravity.GravityTracker;
import pw.kmp.tracker.trackers.meelee.MeleeTracker;
import pw.kmp.tracker.trackers.lava.LavaDamageTracker;
import pw.kmp.tracker.trackers.mob.MobTracker;
import pw.kmp.tracker.trackers.projectile.ProjectileTracker;
import pw.kmp.tracker.trackers.space.VoidTracker;
import pw.kmp.tracker.trackers.water.WaterDamageTracker;

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
        manager.registerTracker(new ExplosionTracker());
        manager.registerTracker(new MeleeTracker());
        manager.registerTracker(new LavaDamageTracker());
        manager.registerTracker(new VoidTracker());
        manager.registerTracker(new AnvilTracker());
        manager.registerTracker(new WaterDamageTracker());
    }

    public static TrackerPlugin get() {
        return instance;
    }

}
