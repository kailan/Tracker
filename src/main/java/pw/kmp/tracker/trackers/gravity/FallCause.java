package pw.kmp.tracker.trackers.gravity;

import org.bukkit.entity.LivingEntity;
import pw.kmp.tracker.damage.Damage;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.damage.EntityCause;

public class FallCause extends DamageCause {

    @Override
    public String getName() {
        return "Fall Damage";
    }

    public static class EntityFallCause extends FallCause implements EntityCause {

        private final Damage causeDamage;
        private final LivingEntity attacker;

        EntityFallCause(Damage causeDamage, LivingEntity attacker) {
            this.causeDamage = causeDamage;
            this.attacker = attacker;
        }

        public Damage getCauseDamage() {
            return causeDamage;
        }

        @Override
        public LivingEntity getEntity() {
            return attacker;
        }

    }

}
