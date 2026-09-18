package xyz.voxen.combat.combat;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import xyz.voxen.combat.item.weapons.WeaponStats;
import xyz.voxen.combat.stats.PlayerStats;

public record DamageContext(ServerPlayerEntity attacker, LivingEntity target, PlayerStats attackerStats,
                            WeaponStats weaponStats, double damageMultiplier) {
    public DamageContext {
        if (attacker == null || target == null || attackerStats == null || weaponStats == null
                || !Double.isFinite(damageMultiplier) || damageMultiplier < 0) {
            throw new IllegalArgumentException("Damage context must contain valid combat state");
        }
    }
}
