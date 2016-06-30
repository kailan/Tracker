package pw.kmp.tracker.trackers.lava;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.damage.EntityCause;


public class LavaDamageCause extends DamageCause {
    private final Block location;

    public LavaDamageCause(Block location) {
        this.location = location;
    }

    public Block getLocation() {
        return location;
    }

    @Override
    public String getName() {
        return "Lava";
    }

    public static class EntityLavaDamageCause extends LavaDamageCause implements EntityCause {
        private final LivingEntity placer;

        public EntityLavaDamageCause(Block location, LivingEntity placer) {
            super(location);
            this.placer = placer;
        }

        @Override
        public LivingEntity getEntity() {
            return placer;
        }

    }
}
