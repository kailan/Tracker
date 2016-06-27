package pw.kmp.tracker.trackers.meelee;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.damage.EntityCause;

public class MeleeCause extends DamageCause implements EntityCause {
    private LivingEntity attacker;
    private Material weapon;

    public MeleeCause(LivingEntity attacker, Material weapon) {
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

    public Material getWeapon() {
        return weapon;
    }
}
