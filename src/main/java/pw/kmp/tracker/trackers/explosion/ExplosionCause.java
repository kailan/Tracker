package pw.kmp.tracker.trackers.explosion;

import org.bukkit.entity.LivingEntity;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.damage.EntityCause;

public class ExplosionCause extends DamageCause {

    public static class EntityExplosionCause extends ExplosionCause implements EntityCause {
        private final LivingEntity attacker;

        EntityExplosionCause(LivingEntity attacker) {
            this.attacker = attacker;
        }

        @Override
        public LivingEntity getEntity() {
            return attacker;
        }
    }

    @Override
    public String getName() {
        return "Explosion";
    }
}
