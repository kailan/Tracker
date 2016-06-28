package pw.kmp.tracker.trackers.meelee;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.damage.EntityCause;

public class MeleeCause extends DamageCause implements EntityCause {
    private LivingEntity attacker;
    private ItemStack weapon;

    public MeleeCause(LivingEntity attacker, ItemStack weapon) {
        this.attacker = attacker;
        this.weapon = weapon;
    }

    @Override
    public String getName() {
        return "Meelee";
    }

    @Override
    public LivingEntity getEntity() {
        return attacker;
    }

    public ItemStack getWeapon() {
        return weapon;
    }
}
