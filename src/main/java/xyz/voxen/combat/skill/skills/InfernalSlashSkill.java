package xyz.voxen.combat.skill.skills;

import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import xyz.voxen.combat.combat.DamageCalculator;
import xyz.voxen.combat.combat.DamageContext;
import xyz.voxen.combat.combat.DamageResult;
import xyz.voxen.combat.item.weapons.DemonBladeItem;
import xyz.voxen.combat.skill.CombatSkill;
import xyz.voxen.combat.skill.SkillCooldownManager;
import xyz.voxen.combat.stats.PlayerStatsService;

public final class InfernalSlashSkill implements CombatSkill {
    public static final InfernalSlashSkill INSTANCE = new InfernalSlashSkill();
    private static final String ID = "infernal_slash";
    private static final double RADIUS = 4.0;
    private static final double DAMAGE_MULTIPLIER = 1.25;

    private InfernalSlashSkill() { }

    @Override public String id() { return ID; }

    @Override
    public boolean execute(ServerPlayerEntity player) {
        if (!player.isAlive() || !DemonBladeItem.isDemonBlade(player.getMainHandStack())
                || !SkillCooldownManager.isReady(player, ID)) return false;
        Box area = player.getBoundingBox().expand(RADIUS);
        List<LivingEntity> targets = player.getServerWorld().getEntitiesByClass(LivingEntity.class, area,
                target -> target.isAlive() && target != player && player.canSee(target));
        for (LivingEntity target : targets) {
            DamageResult result = DamageCalculator.calculate(new DamageContext(player, target,
                    PlayerStatsService.get(player), DemonBladeItem.STATS, DAMAGE_MULTIPLIER), player.getRandom());
            if (target.damage(player.getDamageSources().playerAttack(player), result.damage())) {
                applyLifesteal(player, result.damage());
                if (result.critical()) target.getWorld().addParticle(ParticleTypes.CRIT, target.getX(), target.getBodyY(0.5), target.getZ(), 0, 0.1, 0);
            }
        }
        player.getServerWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_BLAZE_SHOOT, player.getSoundCategory(), 0.8F, 0.8F);
        SkillCooldownManager.start(player, ID, DemonBladeItem.INFERNAL_SLASH_COOLDOWN_TICKS);
        return true;
    }

    private static void applyLifesteal(ServerPlayerEntity player, float damage) {
        double lifesteal = PlayerStatsService.get(player).lifesteal();
        if (lifesteal > 0.0) player.heal((float) (damage * lifesteal));
    }
}
