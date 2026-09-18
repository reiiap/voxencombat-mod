package xyz.voxen.combat.item.weapons;

/** Immutable weapon values. Percentages are stored as fractions: 10% is 0.10. */
public record WeaponStats(double baseDamage, double strength, double critChance, double critDamage) {
    public WeaponStats {
        if (!Double.isFinite(baseDamage) || baseDamage < 0 || !Double.isFinite(strength) || strength < 0
                || !Double.isFinite(critChance) || critChance < 0 || critChance > 1
                || !Double.isFinite(critDamage) || critDamage < 0) {
            throw new IllegalArgumentException("Weapon stats must be finite and within their valid ranges");
        }
    }
}
