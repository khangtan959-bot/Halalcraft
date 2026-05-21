package com.halalcraft.mod.mixin;

import com.halalcraft.mod.util.PenaltyUtil;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.WorldActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBlock.class)
public class ChestMixin {
    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, net.minecraft.util.Hand hand, net.minecraft.util.hit.BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (world instanceof ServerWorld serverWorld) {
            // Check if the chest is a natural loot chest
            // In 1.21.1, we check the BlockEntity for a LootTable
            net.minecraft.block.entity.BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof net.minecraft.block.entity.ChestBlockEntity chest) {
                if (chest.getLootTable() != null) {
                    // It's a natural chest. We let them open it (to look),
                    // but we need to prevent item taking.
                    // To prevent item taking, we need to mixin into the Slot/Container logic.
                }
            }
        }
        return null;
    }
}
