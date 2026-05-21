package com.halalcraft.mod.mixin;

import com.halalcraft.mod.manager.CombatManager;
import com.halalcraft.mod.manager.HuntingManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private boolean onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity target = (LivingEntity) (Object) this;

        if (source.getAttacker() instanceof PlayerEntity player) {
            // Handle Combat and Hunting Laws
            CombatManager.handleAttack(player, target, amount);
            HuntingManager.handleHalalKill(player, target, amount);
        }

        return false; // Continue with normal damage
    }
}
