package xyz.voxen.combat.skill;

import net.minecraft.server.network.ServerPlayerEntity;

public interface CombatSkill {
    String id();

    boolean execute(ServerPlayerEntity player);
}
