package pw.kmp.tracker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import pw.kmp.tracker.Lifetimes;
import pw.kmp.tracker.lifetime.Lifetime;

public class LifetimeListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Lifetimes.getManager().addLifetime(event.getPlayer(), new Lifetime());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Lifetimes.getManager().removeLifetime(event.getEntity());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Lifetimes.getManager().addLifetime(event.getPlayer(), new Lifetime());
    }

}
