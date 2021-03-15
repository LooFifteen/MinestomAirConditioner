package dev.sllcoding.minestomairconditioner.profiles;

import dev.sllcoding.minestomairconditioner.checks.Check;
import dev.sllcoding.minestomairconditioner.checks.CheckManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.time.TimeUnit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Profile {

    private final Player player;
    private final List<Check> checks = new ArrayList<>();

    private int violations = 0;

    private boolean kicking = false;

    public Profile(Player player) {
        this.player = player;
        CheckManager.getChecks().forEach(checkClass -> {
            try {
                checks.add(checkClass.getDeclaredConstructor(Profile.class).newInstance(this));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    public Player getPlayer() {
        return player;
    }

    public List<Check> getChecks() {
        return checks;
    }

    public void violate(Check check) {
        violations++;
        if (violations >= 15 && !kicking) {
            kicking = true;
            player.kick("You have been detected using " + check.getName());
            String message = "[MinestomAirConditioner] " + player.getUsername() + " has been kicked for " + check.getName();
            System.out.println(message);
            for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                // TODO: Permission check.
                player.sendMessage(message);
            }
            violations = 0;
            MinecraftServer.getSchedulerManager().buildTask(() -> kicking = false).delay(10, TimeUnit.SECOND).schedule();
        } else {
            String message = "[MinestomAirConditioner] " + player.getUsername() + " has been detected using " + check.getName();
            System.out.println(message);
            for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                // TODO: Permission check.
                player.sendMessage(message);
            }
        }
    }

}
