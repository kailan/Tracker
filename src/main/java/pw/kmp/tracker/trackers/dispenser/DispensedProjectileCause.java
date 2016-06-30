package pw.kmp.tracker.trackers.dispenser;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import pw.kmp.tracker.trackers.projectile.ProjectileCause;

public class DispensedProjectileCause extends ProjectileCause {
    private final ProjectileSource dispenser;

    public DispensedProjectileCause(Projectile projectile, LivingEntity shooter, Double distance, ProjectileSource dispenser) {
        super(projectile, shooter, distance);
        this.dispenser = dispenser;
    }

    public ProjectileSource getDispenser() {
        return dispenser;
    }

    @Override
    public String getName() {
        return "Dispensed Projectile";
    }
}