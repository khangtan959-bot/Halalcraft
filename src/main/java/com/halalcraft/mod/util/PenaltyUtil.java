package com.halalcraft.mod.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class PenaltyUtil {
    public static void punish(PlayerEntity player, String reason) {
        ServerWorld world = (ServerWorld) player.getWorld();

        // 1. Lightning Strike effect
        LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
        if (lightning != null) {
            lightning.setPos(player.getX(), player.getY(), player.getZ());
            world.spawnEntity(lightning);
        }

        // 2. Send message
        player.sendMessage(Text.literal("§c[HALALCRAFT] YOU HAVE VIOLATED THE LAW!").formatted(Formatting.BOLD, Formatting.RED), false);
        player.sendMessage(Text.literal("Reason: " + reason).formatted(Formatting.RED), false);

        // 3. Kill immediately regardless of gamemode
        player.kill();
    }
}
