package dev.sllcoding.minestomairconditioner.events.global;

import dev.sllcoding.minestomairconditioner.checks.Check;
import dev.sllcoding.minestomairconditioner.profiles.Profile;
import net.minestom.server.event.CancellableEvent;
import net.minestom.server.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Ran when a player is detected by a check. When cancelled, a violation is not given out and the original action is not cancelled.
 */
public class PlayerDetectionEvent extends Event implements CancellableEvent {

    private final Profile profile;
    private final Check check;
    private boolean cancelled;

    public PlayerDetectionEvent(@NotNull Profile profile, @NotNull Check check) {
        this.profile = profile;
        this.check = check;
    }

    public Profile getProfile() {
        return profile;
    }

    public Check getCheck() {
        return check;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

}
