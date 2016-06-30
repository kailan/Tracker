package pw.kmp.tracker.trackers.mob;

import org.bukkit.entity.LivingEntity;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.damage.EntityCause;

public class MobAttackCause extends DamageCause implements EntityCause {

    private final LivingEntity entity;

    public MobAttackCause(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public String getName() {
        return "Mob";
    }

    @Override
    public LivingEntity getEntity() {
        return entity;
    }

}
