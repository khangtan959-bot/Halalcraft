package com.halalcraft.mod.manager;

import com.halalcraft.mod.component.PlayerState;
import com.halalcraft.mod.util.MaterialUtil;
import com.halalcraft.mod.util.PenaltyUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStack;
import net.minecraft.server.minecraft.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.HashMap;
import java.util.Map;

public class ZakatManager {
    private static final Map<PlayerEntity, Long> nextZakatTick = new HashMap<>();
    private static final long ZAKAT_INTERVAL = 3 * 60 * 20L; // 3 minutes in ticks

    public static void tick(MinecraftServer server) {
        for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {
            long nextTick = nextZakatTick.getOrDefault(player, server.getWorld().getTime() + ZAKAT_INTERVAL);

            if (server.getWorld().getTime() >= nextTick) {
                performZakat(player);
                nextZakatTick.put(player, server.getWorld().getTime() + ZAKAT_INTERVAL);
            }
        }
    }

    private static void performZakat(PlayerEntity player) {
        boolean collected = false;

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isEmpty()) continue;

            if (MaterialUtil.isRawMaterial(stack.getItem())) {
                int count = stack.getCount();
                int toLose = (int) Math.ceil(count / 2.0);

                stack.setCount(count - toLose);
                collected = true;
            }
        }

        if (collected) {
            player.sendMessage(Text.literal("§e[ZAKAT] §fThuế tài nguyên đã được thu (50% raw materials).").formatted(Formatting.ITALIC), false);
        }
    }
}
