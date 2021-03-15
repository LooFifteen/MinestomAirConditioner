package dev.sllcoding.minestomairconditioner.checks;

import dev.sllcoding.minestomairconditioner.events.global.PlayerDetectionEvent;
import dev.sllcoding.minestomairconditioner.profiles.Profile;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;

public abstract class Check {

    private final Profile profile;

    public Check(Profile profile) {
        this.profile = profile;
    }

    public abstract String getName();

    public Profile getProfile() {
        return profile;
    }

    public Player getPlayer() {
        return profile.getPlayer();
    }

    protected boolean violate() {
        PlayerDetectionEvent event = new PlayerDetectionEvent(profile, this);
        MinecraftServer.getGlobalEventHandler().callEvent(PlayerDetectionEvent.class, event);
        if (!event.isCancelled()) {
            profile.violate(this);
            return true;
        } else {
            return false;
        }
    }

}
