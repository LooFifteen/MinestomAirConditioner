package dev.sllcoding.minestomairconditioner;

import dev.sllcoding.minestomairconditioner.checks.Checks;
import dev.sllcoding.minestomairconditioner.listeners.PlayerListener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extensions.Extension;

public class MinestomAirConditioner extends Extension {

    @Override
    public void initialize() {
        Checks.setup();

        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerLoginEvent.class, PlayerListener.PLAYER_LOGIN_EVENT);
    }

    @Override
    public void terminate() {

    }

}
