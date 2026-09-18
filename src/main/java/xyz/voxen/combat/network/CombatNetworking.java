package xyz.voxen.combat.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import xyz.voxen.combat.skill.skills.InfernalSlashSkill;
import xyz.voxen.combat.skill.SkillCooldownManager;
import xyz.voxen.combat.stats.PlayerStats;
import xyz.voxen.combat.stats.PlayerStatsService;

public final class CombatNetworking {
    private CombatNetworking() { }
    public static void initializeCommon() {
        PayloadTypeRegistry.playC2S().register(InfernalSlashPayload.ID, InfernalSlashPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(StatsPayload.ID, StatsPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(InfernalSlashPayload.ID, (payload, context) -> {
            InfernalSlashSkill.INSTANCE.execute(context.player());
            sendStats(context.player());
        });
    }
    public static void sendStats(ServerPlayerEntity player) {
        PlayerStats stats = PlayerStatsService.get(player);
        ServerPlayNetworking.send(player, new StatsPayload(stats.strength(), stats.defense(), stats.critChance(),
                stats.critDamage(), stats.lifesteal(), SkillCooldownManager.remainingTicks(player, InfernalSlashSkill.INSTANCE.id())));
    }
}
