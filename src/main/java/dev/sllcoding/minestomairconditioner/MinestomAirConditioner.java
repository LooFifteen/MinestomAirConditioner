package dev.sllcoding.minestomairconditioner;

import dev.sllcoding.minestomairconditioner.checks.CheckManager;
import dev.sllcoding.minestomairconditioner.commands.CommandManager;
import dev.sllcoding.minestomairconditioner.listeners.PlayerListener;
import dev.sllcoding.minestomairconditioner.utils.ComponentUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.minestom.MinestomAudiences;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extensions.Extension;

public class MinestomAirConditioner extends Extension {

    private MinestomAirConditioner instance;
    private MinestomAudiences adventure;

    @Override
    public void initialize() {
        instance = this;
        adventure = MinestomAudiences.create(this);

        Audience console = adventure.console();
        try {
            console.sendMessage(ComponentUtil.get(Component.text("Starting...")));
            CheckManager.setup();
            CommandManager.setup();

            MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerLoginEvent.class, PlayerListener.PLAYER_LOGIN_EVENT);
            console.sendMessage(ComponentUtil.get(Component.text("Successfully started.")));
        } catch (Exception e) {
            e.printStackTrace();
            console.sendMessage(ComponentUtil.get(Component.text("Failed to start up. Shutting down.")));
            MinecraftServer.getExtensionManager().unloadExtension(this.getDescription().getName());
        }
    }

    @Override
    public void terminate() {
        Audience console = adventure.console();
        console.sendMessage(ComponentUtil.get(Component.text("Shutting down.")));
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }

    public MinestomAirConditioner getInstance() {
        return instance;
    }

    public MinestomAudiences getAdventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Cannot retrieve audience provider while plugin is not enabled");
        }
        return this.adventure;
    }

}
