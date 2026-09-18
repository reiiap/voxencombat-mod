package xyz.voxen.combat.combat;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import xyz.voxen.combat.item.weapons.DemonBladeItem;
import xyz.voxen.combat.network.CombatNetworking;
import xyz.voxen.combat.skill.SkillCooldownManager;
import xyz.voxen.combat.stats.PlayerStatsService;

public final class CombatEvents {
    private CombatEvents() { }

    public static void initialize() {
        AttackEntityCallback.EVENT.register(CombatEvents::onAttack);
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> CombatNetworking.sendStats(handler.player));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> SkillCooldownManager.removePlayer(handler.player));
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> CombatNetworking.sendStats(newPlayer));
    }

    private static ActionResult onAttack(net.minecraft.entity.player.PlayerEntity player, net.minecraft.world.World world,
                                         net.minecraft.util.Hand hand, Entity entity, net.minecraft.util.hit.EntityHitResult hitResult) {
        if (world.isClient || hand != net.minecraft.util.Hand.MAIN_HAND || !(player instanceof ServerPlayerEntity attacker)
                || !(entity instanceof LivingEntity target) || !DemonBladeItem.isDemonBlade(attacker.getMainHandStack())) {
            return ActionResult.PASS;
        }
        DamageResult result = DamageCalculator.calculate(new DamageContext(attacker, target, PlayerStatsService.get(attacker),
                DemonBladeItem.STATS, 1.0), attacker.getRandom());
        if (target.damage(attacker.getDamageSources().playerAttack(attacker), result.damage())) {
            double lifesteal = PlayerStatsService.get(attacker).lifesteal();
            if (lifesteal > 0) attacker.heal((float) (result.damage() * lifesteal));
            if (result.critical()) target.getWorld().addParticle(net.minecraft.particle.ParticleTypes.CRIT,
                    target.getX(), target.getBodyY(0.5), target.getZ(), 0, 0.1, 0);
        }
        return ActionResult.FAIL;
    }
}
