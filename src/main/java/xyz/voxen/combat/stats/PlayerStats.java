package xyz.voxen.combat.stats;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

/** Validated mutable RPG state persisted on each player. Percentages are fractions. */
public final class PlayerStats {
    private static final String ROOT_KEY = "VoxenCombatStats";
    private static final double MAX_FLAT_STAT = 1_000_000.0;
    private static final double MAX_PERCENT = 1.0;
    private double strength;
    private double defense;
    private double critChance;
    private double critDamage;
    private double lifesteal;

    public double strength() { return strength; }
    public double defense() { return defense; }
    public double critChance() { return critChance; }
    public double critDamage() { return critDamage; }
    public double lifesteal() { return lifesteal; }

    public void setStrength(double value) { strength = flat(value); }
    public void setDefense(double value) { defense = flat(value); }
    public void setCritChance(double value) { critChance = percentage(value); }
    public void setCritDamage(double value) { critDamage = percentage(value); }
    public void setLifesteal(double value) { lifesteal = percentage(value); }

    public void writeToNbt(NbtCompound nbt) {
        NbtCompound values = new NbtCompound();
        values.putDouble("strength", strength);
        values.putDouble("defense", defense);
        values.putDouble("critChance", critChance);
        values.putDouble("critDamage", critDamage);
        values.putDouble("lifesteal", lifesteal);
        nbt.put(ROOT_KEY, values);
    }

    public void readFromNbt(NbtCompound nbt) {
        if (!nbt.contains(ROOT_KEY, NbtElement.COMPOUND_TYPE)) return;
        NbtCompound values = nbt.getCompound(ROOT_KEY);
        setStrength(values.getDouble("strength"));
        setDefense(values.getDouble("defense"));
        setCritChance(values.getDouble("critChance"));
        setCritDamage(values.getDouble("critDamage"));
        setLifesteal(values.getDouble("lifesteal"));
    }

    public void copyFrom(PlayerStats other) {
        setStrength(other.strength);
        setDefense(other.defense);
        setCritChance(other.critChance);
        setCritDamage(other.critDamage);
        setLifesteal(other.lifesteal);
    }

    private static double flat(double value) {
        return Double.isFinite(value) ? Math.clamp(value, 0.0, MAX_FLAT_STAT) : 0.0;
    }

    private static double percentage(double value) {
        return Double.isFinite(value) ? Math.clamp(value, 0.0, MAX_PERCENT) : 0.0;
    }
}
