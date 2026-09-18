package xyz.voxen.combat.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.voxen.combat.stats.PlayerStats;
import xyz.voxen.combat.stats.PlayerStatsAccess;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin implements PlayerStatsAccess {
    @Unique private final PlayerStats voxencombat$stats = new PlayerStats();

    @Override
    public PlayerStats voxencombat$getStats() {
        return voxencombat$stats;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void voxencombat$writeStats(NbtCompound nbt, CallbackInfo ci) {
        voxencombat$stats.writeToNbt(nbt);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void voxencombat$readStats(NbtCompound nbt, CallbackInfo ci) {
        voxencombat$stats.readFromNbt(nbt);
    }
}
