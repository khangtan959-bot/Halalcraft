package com.halalcraft.mod.manager;

import com.halalcraft.mod.component.PlayerState;
import com.halalcraft.mod.util.PenaltyUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import java.util.HashMap;
import java.util.Map;

public class RitualManager {
    private static final Map<PlayerEntity, Integer> wuduSteps = new HashMap<>();
    private static final Map<PlayerEntity, Long> sneakStartTime = new HashMap<>();

    public static void handleWaterAction(PlayerEntity player) {
        int steps = wuduSteps.getOrDefault(player, 0) + 1;
        wuduSteps.put(player, steps);

        if (steps >= 6) {
            player.sendMessage(net.minecraft.text.Text.literal("§a[WUDU] §fĐã hoàn thành tẩy trần. Hãy hướng về phía BẮC và Ngồi trong 10s.").formatted(net.minecraft.util.Formatting.ITALIC), false);
        }
    }

    public static void handleSneakTick(PlayerEntity player) {
        if (!player.isSneaking()) {
            sneakStartTime.remove(player);
            return;
        }

        // Check direction (North is roughly -Z)
        float yaw = player.getYaw();
        if (yaw > -45 && yaw < 45) { // North
            long start = sneakStartTime.getOrDefault(player, System.currentTimeMillis());
            sneakStartTime.put(player, start);

            if (System.currentTimeMillis() - start >= 10000) {
                PrayerManager.recordPrayer(player);
                wuduSteps.remove(player);
                sneakStartTime.remove(player);
            }
        } else {
            sneakStartTime.remove(player);
        }
    }

    public static void resetRitual(PlayerEntity player) {
        wuduSteps.remove(player);
        sneakStartTime.remove(player);
    }
}
