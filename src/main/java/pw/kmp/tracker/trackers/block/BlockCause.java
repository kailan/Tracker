package pw.kmp.tracker.trackers.block;

import org.bukkit.block.BlockState;
import pw.kmp.tracker.damage.DamageCause;

public class BlockCause extends DamageCause {
    private final BlockState damager;

    public BlockCause(BlockState damager) {
        this.damager = damager;
    }

    public BlockState getDamager() {
        return damager;
    }

    @Override
    public String getName() {
        return "Block";
    }
}
