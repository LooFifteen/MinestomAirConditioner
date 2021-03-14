package dev.sllcoding.minestomairconditioner.listeners;

import dev.sllcoding.minestomairconditioner.profiles.ProfileManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.EventCallback;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.utils.time.TimeUnit;

public class PlayerListener {

    public static EventCallback<PlayerLoginEvent> PLAYER_LOGIN_EVENT = event -> {
        event.getPlayer().setGameMode(GameMode.CREATIVE);

        MinecraftServer.getSchedulerManager().buildTask(() -> ProfileManager.reinstantiate(event.getPlayer())).delay(10, TimeUnit.SECOND).schedule();
    };

}
