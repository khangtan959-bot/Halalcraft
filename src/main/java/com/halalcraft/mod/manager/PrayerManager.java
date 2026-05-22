package com.halalcraft.mod.manager;

import com.halalcraft.mod.component.PlayerState;
import com.halalcraft.mod.util.PenaltyUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.minecraft.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.RotationAxis;
import java.util.HashMap;
import java.util.Map;

public class PrayerManager {
    private static final Map<PlayerEntity, Long> countdowns = new HashMap<>();
    private static final long[] CYCLE_INTERVALS = {
        300 * 20L, // 5 min
        150 * 20L, // 2.5 min
        150 * 20L, // 2.5 min
        150 * 20L, // 2.5 min
        150 * 20L  // 2.5 min
    };

    public static void tick(MinecraftServer server) {
        for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {
            PlayerState state = DataManager.getState(player.getUuid());
            if (!state.hasAcceptedShahada) continue;

            long currentTime = server.getWorld().getTime();
            long limit = state.lastPrayerTime + (CYCLE_INTERVALS[state.prayerCycleIndex]);

            // Special logic for Nether/End limit (7.5 min)
            if (player.getWorld().getRegistryKey().getValue().toString().contains("nether") ||
                player.getWorld().getRegistryKey().getValue().toString().contains("the_end")) {
                limit = state.lastPrayerTime + (450 * 20L);
            }

            long timeLeft = limit - currentTime;

            if (timeLeft <= 0) {
                PenaltyUtil.punish(player, "Bạn đã bỏ lỡ giờ cầu nguyện!");
                return;
            }

            // Display Actionbar
            String timeText = formatTime(timeLeft / 20L);
            player.sendMessage(Text.literal("§6§lCầu nguyện trong: §f" + timeText).formatted(Formatting.GOLD), true);
        }
    }

    private static String formatTime(long seconds) {
        long m = seconds / 60;
        long s = seconds % 60;
        return String.format("%02d:%02d", m, s);
    }

    public static void recordPrayer(PlayerEntity player) {
        PlayerState state = DataManager.getState(player.getUuid());
        state.lastPrayerTime = player.getWorld().getTime();
        state.prayerCycleIndex = (state.prayerCycleIndex + 1) % CYCLE_INTERVALS.length;
        DataManager.saveState(player.getUuid());

        player.sendMessage(Text.literal("§a[SALAH] §fBạn đã hoàn thành nghi lễ cầu nguyện.").formatted(Formatting.BOLD), false);
    }
}
