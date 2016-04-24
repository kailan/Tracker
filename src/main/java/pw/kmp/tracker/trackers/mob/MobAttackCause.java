package pw.kmp.tracker.trackers.mob;

import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.damage.EntityCause;

public class MobAttackCause extends DamageCause implements EntityCause {

    private final Creature entity;

    public MobAttackCause(Creature entity) {
        this.entity = entity;
    }

    @Override
    public String getName() {
        return "Attack";
    }

    @Override
    public LivingEntity getEntity() {
        return entity;
    }

}
