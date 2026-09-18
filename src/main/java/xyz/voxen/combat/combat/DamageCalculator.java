package xyz.voxen.combat.combat;

import net.minecraft.util.math.random.Random;

/** The only location for RPG damage math; percentage values are fractional. */
public final class DamageCalculator {
    private DamageCalculator() { }

    public static DamageResult calculate(DamageContext context, Random random) {
        double rawDamage = (context.weaponStats().baseDamage()
                + (context.attackerStats().strength() + context.weaponStats().strength()) * 0.5)
                * context.damageMultiplier();
        double defense = context.target() instanceof xyz.voxen.combat.stats.PlayerStatsAccess statsAccess
                ? statsAccess.voxencombat$getStats().defense() : 0.0;
        double mitigated = rawDamage * (100.0 / (100.0 + defense));
        double critChance = Math.clamp(context.attackerStats().critChance() + context.weaponStats().critChance(), 0.0, 1.0);
        boolean critical = random.nextDouble() < critChance;
        double criticalDamage = context.attackerStats().critDamage() + context.weaponStats().critDamage();
        double finalDamage = critical ? mitigated * (1.0 + criticalDamage) : mitigated;
        return new DamageResult((float) Math.clamp(finalDamage, 0.0, 1_000_000.0), critical);
    }
}
