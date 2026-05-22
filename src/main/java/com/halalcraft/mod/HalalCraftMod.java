package com.halalcraft.mod;

import com.halalcraft.mod.command.ResetIslamCommand;
import com.halalcraft.mod.event.JoinEventHandler;
import com.halalcraft.mod.event.ChatEventHandler;
import com.halalcraft.mod.manager.DataManager;
import com.halalcraft.mod.manager.PrayerManager;
import com.halalcraft.mod.manager.ZakatManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HalalCraftMod implements ModInitializer {
    public static final String MOD_ID = "halalcraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing HalalCraft - Survival of the Faithful");

        // Initialize Data Manager
        DataManager.init();

        // Register Events
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            JoinEventHandler.onPlayerJoin(handler.getPlayer());
        });

        ChatEventHandler.register();

        // Tick Managers
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            ZakatManager.tick(server);
            PrayerManager.tick(server);
        });

        // Commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ResetIslamCommand.register(dispatcher);
        });
    }
}
