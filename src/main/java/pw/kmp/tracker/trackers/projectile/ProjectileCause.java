package pw.kmp.tracker.trackers.projectile;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.damage.EntityCause;

public class ProjectileCause extends DamageCause implements EntityCause {

    private final Projectile projectile;
    private final LivingEntity shooter;
    private final Double distance;

    public ProjectileCause(Projectile projectile, LivingEntity shooter, Double distance) {
        this.projectile = projectile;
        this.shooter = shooter;
        this.distance = distance;
    }

    @Override
    public String getName() {
        switch (projectile.getType()) {
            case ARROW:
            //case SPECTRAL_ARROW:
            //case TIPPED_ARROW:
                return "Archery";
            case SNOWBALL:
                return "Snowball";
            case ENDER_PEARL:
                return "Ender Pearl";
            case EGG:
                return "Egg";
            default:
                return "Projectile";
        }
    }

    public Projectile getProjectile() {
        return projectile;
    }

    @Override
    public LivingEntity getEntity() {
        return shooter;
    }

    public Double getDistance() {
        return distance;
    }

}
