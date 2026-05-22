package com.halalcraft.mod.mixin;

import com.halalcraft.mod.manager.CombatManager;
import com.halalcraft.mod.manager.RitualManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // 1. Combat distance check
        CombatManager.checkCombatDistance(player, player.getWorld());

        // 2. Ritual sneak check
        RitualManager.handleSneakTick(player);

        // 3. Villager Confinement Check
        net.minecraft.entity.passive.VillagerEntity nearbyVillager = null;
        for (net.minecraft.entity.Entity entity : player.getWorld().getEntities()) {
            if (entity instanceof net.minecraft.entity.passive.VillagerEntity villager) {
                if (player.distanceTo(villager) < 10) {
                    com.halalcraft.mod.manager.VillagerManager.checkConfinement(villager, (net.minecraft.server.world.ServerWorld) player.getWorld());
                }
            }
        }
    }
}
