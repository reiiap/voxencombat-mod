package xyz.voxen.combat;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import xyz.voxen.combat.combat.CombatEvents;
import xyz.voxen.combat.network.CombatNetworking;
import xyz.voxen.combat.registry.ModItems;

/** Common entry point. This class intentionally has no client-only dependencies. */
public final class VoxenCombat implements ModInitializer {
    public static final String MOD_ID = "voxencombat";

    @Override
    public void onInitialize() {
        ModItems.initialize();
        CombatNetworking.initializeCommon();
        CombatEvents.initialize();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
