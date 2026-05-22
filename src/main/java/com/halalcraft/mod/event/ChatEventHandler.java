package com.halalcraft.mod.event;

import com.halalcraft.mod.component.PlayerState;
import com.halalcraft.mod.manager.DataManager;
import com.halalcraft.mod.util.PenaltyUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.UUID;

public class ChatEventHandler {
    public static void register() {
        // In Fabric, we use a mixin or a specific API for chat.
        // Since we need to intercept chat, we'll use a Mixin to ServerPlayNetworkHandler.
    }

    public static void handleChatMessage(ServerPlayerEntity player, String message) {
        PlayerState state = DataManager.getState(player.getUuid());
        String lowercaseMsg = message.toLowerCase().trim();

        // 1. Shahada Check
        if (!state.hasAcceptedShahada && lowercaseMsg.equals("không có chúa trời nào ngoài allah và muhammad là sứ giả của ngài")) {
            state.hasAcceptedShahada = true;
            DataManager.saveState(player.getUuid());

            player.sendMessage(Text.literal("§a[HALALCRAFT] Shahada đã được chấp nhận. Bạn đã được tự do!").formatted(Formatting.BOLD), false);
            player.removeStatusEffect(net.minecraft.entity.effect.StatusEffects.BLINDNESS);
            player.removeStatusEffect(net.minecraft.entity.effect.StatusEffects.SLOWNESS);
            return;
        }

        // 2. Bismillah Check (for hunting)
        if (lowercaseMsg.equals("bismillah allahu akbar")) {
            state.lastBismillahTime = System.currentTimeMillis();
            player.sendMessage(Text.literal("§e[HALALCRAFT] Lời niệm đã được ghi nhận. Bạn có 30 giây để săn bắt Halal.").formatted(Formatting.ITALIC), false);
        }
    }
}
