package xyz.voxen.combat.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import xyz.voxen.combat.network.InfernalSlashPayload;
import xyz.voxen.combat.network.StatsPayload;

/** Client entry point, isolated under src/client so a dedicated server never loads client classes. */
public final class VoxenCombatClient implements ClientModInitializer {
    private static final KeyBinding INFERNAL_SLASH = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.voxencombat.infernal_slash", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.voxencombat.combat"));

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(StatsPayload.ID, (payload, context) -> ClientCombatState.update(payload));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientCombatState.tick();
            while (INFERNAL_SLASH.wasPressed() && client.player != null) ClientPlayNetworking.send(new InfernalSlashPayload());
        });
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> ClientCombatHud.render(drawContext));
    }
}
