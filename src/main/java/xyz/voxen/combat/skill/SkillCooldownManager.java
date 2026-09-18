package xyz.voxen.combat.skill;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.network.ServerPlayerEntity;

/** Tick-time based cooldowns with no scheduled tasks or permanent player cache. */
public final class SkillCooldownManager {
    private static final Map<UUID, Map<String, Long>> COOLDOWNS = new HashMap<>();

    private SkillCooldownManager() { }

    public static boolean isReady(ServerPlayerEntity player, String skillId) {
        return expiresAt(player, skillId) <= player.getServerWorld().getTime();
    }

    public static void start(ServerPlayerEntity player, String skillId, int durationTicks) {
        COOLDOWNS.computeIfAbsent(player.getUuid(), ignored -> new HashMap<>())
                .put(skillId, player.getServerWorld().getTime() + durationTicks);
    }

    public static int remainingTicks(ServerPlayerEntity player, String skillId) {
        return (int) Math.max(0, expiresAt(player, skillId) - player.getServerWorld().getTime());
    }

    public static void removePlayer(ServerPlayerEntity player) {
        COOLDOWNS.remove(player.getUuid());
    }

    private static long expiresAt(ServerPlayerEntity player, String skillId) {
        return COOLDOWNS.getOrDefault(player.getUuid(), Map.of()).getOrDefault(skillId, 0L);
    }
}
