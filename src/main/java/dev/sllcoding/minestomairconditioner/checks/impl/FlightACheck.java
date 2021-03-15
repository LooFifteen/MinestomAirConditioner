package dev.sllcoding.minestomairconditioner.checks.impl;

import dev.sllcoding.minestomairconditioner.checks.Check;
import dev.sllcoding.minestomairconditioner.profiles.Profile;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;

public class FlightACheck extends Check {

    private boolean lastOnGround, lastLastOnGround;
    private double lastDistY;

    public FlightACheck(Profile profile) {
        super(profile);
        profile.getPlayer().addEventCallback(PlayerMoveEvent.class, event -> {
            Position to = event.getNewPosition();
            Position from = event.getPlayer().getPosition();

            double distY = to.getY() - from.getY();
            double lastDistY = this.lastDistY;
            this.lastDistY = distY;

            boolean onGround = isNearGround(event.getPlayer().getInstance(), to);
            boolean lastOnGround = this.lastOnGround;
            this.lastOnGround = onGround;
            boolean lastLastOnGround = this.lastLastOnGround;
            this.lastLastOnGround = lastOnGround;

            double predictedDist = (lastDistY - 0.08d) * 0.9800000190734863d;

            if (!onGround && !lastOnGround && !lastLastOnGround && Math.abs(predictedDist) >= 0.005d) {
                if (!isRoughlyEqual(distY, predictedDist)) {
                    violate(event);
                }
            }
        });
    }

    public boolean isRoughlyEqual(double d1, double d2) {
        return Math.abs(d1 - d2) < 0.015;
    }

    public boolean isNearGround(Instance instance, Position position) {
        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                Position pos = position.clone().add(x, -1, z);
                if (!instance.getBlock(new BlockPosition((int) Math.floor(pos.getX()), (int) Math.floor(pos.getY()), (int) Math.floor(pos.getZ()))).getName().equals("minecraft:air")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return "Flight A";
    }

    protected void violate(PlayerMoveEvent event) {
        if (super.violate()) event.setCancelled(true);
    }

}
