package xyz.voxen.combat.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.voxen.combat.stats.PlayerStatsAccess;

@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin {
    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void voxencombat$copyStats(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ((PlayerStatsAccess) this).voxencombat$getStats().copyFrom(((PlayerStatsAccess) oldPlayer).voxencombat$getStats());
    }
}
