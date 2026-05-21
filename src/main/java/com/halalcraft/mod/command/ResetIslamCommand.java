package com.halalcraft.mod.command;

import com.mojang.brigadier.command.literals.LiteralArgumentBuilder;
import net.minecraft.command.CommandDispatcher;
import net.minecraft.command.entity.EntitySelectorArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ResetIslamCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<ServerCommandSource>literal("resetislam")
            .requires(source -> source.hasPermissionLevel(2))
            .then(net.minecraft.server.command.CommandRegistrationCallback.argument("player", EntitySelectorArgumentType.player())
                .executes(context -> {
                    PlayerEntity target = EntitySelectorArgumentType.getPlayer(context.getSource(), context.getArgument("player"));
                    com.halalcraft.mod.manager.DataManager.resetState(target.getUuid());
                    context.getSource().sendFeedback(() -> Text.literal("Đã reset trạng thái Islam cho " + target.getName().getString()), true);
                    return 1;
                })
            )
        );
    }
}
