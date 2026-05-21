package com.halalcraft.mod.mixin;

import com.halalcraft.mod.event.ChatEventHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerGamePacketListenerMP;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ChatMixin {
    @Inject(method = "onChatMessage", at = @At("HEAD"), cancellable = true)
    private void onChat(Text message, CallbackInfo ci) {
        ServerPlayNetworkHandler handler = (ServerPlayNetworkHandler) (Object) this;
        if (handler.getPlayer() != null) {
            ChatEventHandler.handleChatMessage(handler.getPlayer(), message.getString());
        }
    }
}
