package xyz.voxen.combat.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import xyz.voxen.combat.network.StatsPayload;

final class ClientCombatHud {
    private ClientCombatHud() { }
    static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;
        StatsPayload stats = ClientCombatState.stats();
        String line = String.format("STR %.0f  DEF %.0f  CRIT %.0f%%  LS %.0f%%  Slash %.1fs",
                stats.strength(), stats.defense(), stats.critChance() * 100, stats.lifesteal() * 100,
                stats.infernalSlashTicks() / 20.0);
        context.drawTextWithShadow(client.textRenderer, line, 8, 8, 0xFFCC66);
    }
}
