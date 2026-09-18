package xyz.voxen.combat.stats;

import net.minecraft.server.network.ServerPlayerEntity;

public final class PlayerStatsService {
    private PlayerStatsService() { }

    public static PlayerStats get(ServerPlayerEntity player) {
        return ((PlayerStatsAccess) player).voxencombat$getStats();
    }
}
