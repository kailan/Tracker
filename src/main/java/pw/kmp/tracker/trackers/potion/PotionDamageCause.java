package pw.kmp.tracker.trackers.potion;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import pw.kmp.tracker.damage.DamageCause;
import pw.kmp.tracker.damage.EntityCause;

public class PotionDamageCause extends DamageCause {
   private final PotionEffect potionEffect;
   private final int amplifier;
   private final int duration;

    public PotionDamageCause(PotionEffect potionEffect) {
        this.potionEffect = potionEffect;
        this.amplifier = potionEffect.getAmplifier();
        this.duration = potionEffect.getDuration();
    }

    public PotionEffect getPotionEffect() {
        return potionEffect;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String getName() {
        return "Potion";
    }

    public static class EntityPotionDamageCause extends PotionDamageCause implements EntityCause {
        private final LivingEntity damager;

        public EntityPotionDamageCause(PotionEffect potionEffect, LivingEntity damager) {
            super(potionEffect);
            this.damager = damager;
        }

        @Override
        public LivingEntity getEntity() {
            return damager;
        }
    }
}

