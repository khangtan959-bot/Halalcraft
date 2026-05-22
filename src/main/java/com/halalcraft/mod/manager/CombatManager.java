package com.halalcraft.mod.manager;

import com.halalcraft.mod.component.PlayerState;
import com.halalcraft.mod.util.PenaltyUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.PassiveEntity;
import net.minecraft.entity.PassiveEntity.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import java.util.UUID;

public class CombatManager {
    public static void handleAttack(PlayerEntity player, LivingEntity target, float damage) {
        PlayerState state = DataManager.getState(player.getUuid());

        // 1. Check for Pork ban
        if (target.getType() == EntityType.PIG) {
            PenaltyUtil.punish(player, "Tuyệt đối cấm đánh hoặc giết heo!");
            return;
        }

        // 2. Check First Strike (Non-combat)
        if (!state.isCombatActive && !isTargetAggressive(target)) {
            PenaltyUtil.punish(player, "Không được chủ động tấn công quái vật trước!");
            return;
        }

        // 3. Update Combat State
        state.isCombatActive = true;
        state.combatTarget = target.getUuid();
        DataManager.saveState(player.getUuid());
    }

    public static void checkCombatDistance(PlayerEntity player, ServerWorld world) {
        PlayerState state = DataManager.getState(player.getUuid());
        if (!state.isCombatActive || state.combatTarget == null) return;

        Entity target = world.getEntity(state.combatTarget);
        if (target == null || (target instanceof LivingEntity && !((LivingEntity) target).isAlive())) {
            state.isCombatActive = false;
            state.combatTarget = null;
            DataManager.saveState(player.getUuid());
            return;
        }

        double dist = player.distanceTo(target);
        if (dist > 7.0) {
            if (state.combatGraceStartTime == 0) {
                state.combatGraceStartTime = System.currentTimeMillis();
            } else if (System.currentTimeMillis() - state.combatGraceStartTime > 3000) {
                PenaltyUtil.punish(player, "Đã chạy quá xa vùng chiến đấu (> 7 blocks)!");
            }
        } else {
            state.combatGraceStartTime = 0;
        }
    }

    private static boolean isTargetAggressive(LivingEntity target) {
        // Simple check: if it's a monster or already targeting the player
        return target.isInCombat() || target.getType().getGroup().contains(net.minecraft.entity.EntityGroup.MONSTERS);
    }
}
