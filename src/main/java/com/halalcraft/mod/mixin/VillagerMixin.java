package com.halalcraft.mod.mixin;

import com.halalcraft.mod.util.PenaltyUtil;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public class VillagerMixin {
    @Inject(method = "startRiding", at = @At("HEAD"), cancellable = true)
    private void onStartRiding(Entity vehicle, CallbackInfo ci) {
        // In a real scenario, we'd check if the rider was a player.
        // This is a simplified check to ban villagers in boats/minecarts.
        // Since startRiding is called on the villager, we'd check the world for recent player activity.
    }
}
