package xyz.voxen.combat.client;

import xyz.voxen.combat.network.StatsPayload;

final class ClientCombatState {
    private static StatsPayload stats = new StatsPayload(0, 0, 0, 0, 0, 0);
    private ClientCombatState() { }
    static void update(StatsPayload payload) { stats = payload; }
    static void tick() {
        if (stats.infernalSlashTicks() > 0) {
            stats = new StatsPayload(stats.strength(), stats.defense(), stats.critChance(), stats.critDamage(),
                    stats.lifesteal(), stats.infernalSlashTicks() - 1);
        }
    }
    static StatsPayload stats() { return stats; }
}
