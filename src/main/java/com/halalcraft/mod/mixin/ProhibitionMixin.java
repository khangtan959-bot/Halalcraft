package com.halalcraft.mod.mixin;

import com.halalcraft.mod.util.PenaltyUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EnchantmentListComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class ProhibitionMixin {
    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void checkProhibitions(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // 1. Enchantment Check
        for (ItemStack stack : player.getInventory().main) {
            if (stack.hasEnchantments()) {
                PenaltyUtil.punish(player, "Cấm sử dụng trang bị có Phù phép (Enchantments)!");
                return;
            }
        }

        // 2. Potion Check (holding potion)
        ItemStack mainHand = player.getMainHandStack();
        if (mainHand.getItem() instanceof net.minecraft.item.PotionItem) {
            // we can't simply punish for holding, but we punish for using.
            // however, for a strict mod, holding might be enough.
            // let's check if they are actually drinking.
        }
    }
}
