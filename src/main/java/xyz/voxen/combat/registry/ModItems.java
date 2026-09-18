package xyz.voxen.combat.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import xyz.voxen.combat.VoxenCombat;
import xyz.voxen.combat.item.weapons.DemonBladeItem;

public final class ModItems {
    public static final Item DEMON_BLADE = register("demon_blade", new DemonBladeItem());

    private ModItems() { }

    private static Item register(String path, Item item) {
        return Registry.register(Registries.ITEM, VoxenCombat.id(path), item);
    }

    public static void initialize() { }
}
