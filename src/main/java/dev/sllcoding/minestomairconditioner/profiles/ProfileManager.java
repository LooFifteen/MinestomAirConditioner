package dev.sllcoding.minestomairconditioner.profiles;

import net.minestom.server.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager {

    private static final Map<UUID, Profile> profiles = new HashMap<>();

    public static Profile getProfile(Player player) {
        if (profiles.containsKey(player.getUuid())) {
            return profiles.get(player.getUuid());
        } else {
            return reinstantiate(player);
        }
    }

    public static Profile reinstantiate(Player player) {
        Profile profile = new Profile(player);
        profiles.put(player.getUuid(), profile);
        return profile;
    }

}
