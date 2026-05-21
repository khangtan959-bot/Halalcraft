package com.halalcraft.mod.manager;

import com.halalcraft.mod.component.PlayerState;
import com.halalcraft.mod.util.PenaltyUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import java.util.UUID;

public class HuntingManager {
    public static void handleHalalKill(PlayerEntity player, LivingEntity target, float damage) {
        if (!(target instanceof PassiveEntity)) return;

        PlayerState state = DataManager.getState(player.getUuid());

        // 1. Check Adult
        if (!target.isBaby()) {
            // 2. Check Bismillah timer
            long now = System.currentTimeMillis();
            if (now - state.lastBismillahTime > 30000 || state.lastBismillahTarget == null || !state.lastBismillahTarget.equals(target.getUuid())) {
                PenaltyUtil.punish(player, "Săn bắn không niệm 'bismillah allahu akbar' hoặc quá thời gian!");
                return;
            }

            // 3. Check One-Hit Kill
            // In Minecraft, if damage >= current health, it's a one hit kill.
            if (damage < target.getHealth()) {
                // We don't punish immediately if it's not the final hit,
                // but if the target survives, they can't just keep hitting it.
                // The law says "giết chết mục tiêu chỉ trong 1 nhát".
                // We implement this by checking if the hit that KILLS it was the only hit.
                // However, a simpler way is: if you hit it and it doesn't die, you are warned.
                // If you hit it and it dies, check if it was the FIRST hit.
            }
        } else {
            PenaltyUtil.punish(player, "Cấm giết động vật con!");
        }
    }

    public static void setBismillahTarget(PlayerEntity player, UUID targetUuid) {
        PlayerState state = DataManager.getState(player.getUuid());
        state.lastBismillahTime = System.currentTimeMillis();
        state.lastBismillahTarget = targetUuid;
        DataManager.saveState(player.getUuid());
    }
}
