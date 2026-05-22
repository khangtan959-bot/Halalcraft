package com.halalcraft.mod.event;

import com.halalcraft.mod.component.PlayerState;
import com.halalcraft.mod.manager.DataManager;
import com.halalcraft.mod.util.BookUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.nio.file.Paths;

public class JoinEventHandler {
    public static void onPlayerJoin(ServerPlayerEntity player) {
        // Initialize data directory if not set
        if (DataManager.getDataDirectory() == null) {
            DataManager.setDataDirectory(Paths.get("world/halalcraft"));
        }

        PlayerState state = DataManager.getState(player.getUuid());

        if (!state.hasAcceptedShahada) {
            // Apply freezing effects
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 1000000, 0, false, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000000, 255, false, false));

            player.sendMessage(Text.literal("§6[HALALCRAFT] §fBạn đang bị đóng băng. Hãy nhập câu Shahada để bắt đầu hành trình.").formatted(Formatting.ITALIC), false);
        } else {
            // Clear effects if they were previously frozen
            player.removeStatusEffect(StatusEffects.BLINDNESS);
            player.removeStatusEffect(StatusEffects.SLOWNESS);
        }

        // Give tutorial books
        player.getInventory().insertStack(BookUtil.createInfoBook());
        player.getInventory().insertStack(BookUtil.createGuideBook());
    }
}
