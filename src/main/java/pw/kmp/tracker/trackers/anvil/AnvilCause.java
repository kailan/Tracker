package pw.kmp.tracker.trackers.anvil;

import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.damage.EntityCause;

public class AnvilCause extends DamageCause {
    private final FallingBlock anvil;

    public AnvilCause(FallingBlock anvil) {
        this.anvil = anvil;
    }

    @Override
    public String getName() {
        return "Anvil";
    }

    public static class EntityAnvilCause extends AnvilCause implements EntityCause {
        private final LivingEntity attacker;

        public EntityAnvilCause(FallingBlock anvil, LivingEntity entity) {
            super(anvil);
            attacker = entity;
        }

        @Override
        public LivingEntity getEntity() {
            return attacker;
        }
    }

}
