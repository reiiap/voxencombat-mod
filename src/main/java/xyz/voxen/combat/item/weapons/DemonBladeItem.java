package xyz.voxen.combat.item.weapons;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.SwordItem;
import xyz.voxen.combat.skill.skills.InfernalSlashSkill;

public final class DemonBladeItem extends SwordItem {
    public static final WeaponStats STATS = new WeaponStats(40.0, 25.0, 0.10, 0.25);
    public static final int INFERNAL_SLASH_COOLDOWN_TICKS = 12 * 20;

    public DemonBladeItem() {
        super(ToolMaterial.NETHERITE, new Item.Settings().maxCount(1));
    }

    public static boolean isDemonBlade(ItemStack stack) {
        return stack.getItem() instanceof DemonBladeItem;
    }

    public InfernalSlashSkill skill() {
        return InfernalSlashSkill.INSTANCE;
    }
}
