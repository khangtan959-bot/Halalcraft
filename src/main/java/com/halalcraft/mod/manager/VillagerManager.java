package com.halalcraft.mod.manager;

import com.halalcraft.mod.util.PenaltyUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VillagerManager {
    private static final Map<UUID, Long> confinementStart = new HashMap<>();

    public static void checkConfinement(VillagerEntity villager, ServerWorld world) {
        BlockPos pos = villager.getBlockPos();
        boolean isCaged = true;

        // Check 3x3x3 area around villager
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockState state = world.getBlockState(pos.offset(x, y, z));
                    if (state.isAir()) {
                        isCaged = false;
                        break;
                    }
                }
                if (!isCaged) break;
            }
            if (!isCaged) break;
        }

        if (isCaged) {
            UUID uuid = villager.getUuid();
            long start = confinementStart.getOrDefault(uuid, System.currentTimeMillis());
            confinementStart.put(uuid, start);

            if (System.currentTimeMillis() - start > 10000) {
                // Find nearest player who might have caged them
                for (PlayerEntity player : world.getPlayers()) {
                    if (player.distanceTo(villager) < 10) {
                        PenaltyUtil.punish(player, "Cấm giam cầm Dân làng trong không gian nhỏ!");
                        break;
                    }
                }
            }
        } else {
            confinementStart.remove(villager.getUuid());
        }
    }
}
