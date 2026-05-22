package com.halalcraft.mod.util;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import java.util.Optional;

public class EndPortalScanner {
    public static boolean isNearEndPortal(PlayerEntity player) {
        ServerWorld world = (ServerWorld) player.getWorld();
        BlockPos playerPos = player.getBlockPos();
        int radius = 64;

        // Scan for End Portal blocks in a cube around the player
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos checkPos = playerPos.add(x, y, z);
                    if (world.getBlockState(checkPos).getBlock() == Blocks.END_PORTAL_FRAME) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
