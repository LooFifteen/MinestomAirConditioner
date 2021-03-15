package dev.sllcoding.minestomairconditioner.checks.impl;

import dev.sllcoding.minestomairconditioner.checks.Check;
import dev.sllcoding.minestomairconditioner.profiles.Profile;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.item.Material;
import net.minestom.server.utils.Position;

public class SpeedACheck extends Check {

    private double lastDist;
    private boolean lastOnGround;

    public SpeedACheck(Profile profile) {
        super(profile);
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerMoveEvent.class, event -> {
            Position to = event.getNewPosition();
            Position from = event.getPlayer().getPosition();

            double distX = to.getX() - from.getX();
            double distZ = to.getZ() - from.getZ();
            double dist = (distX * distX) + (distZ * distZ);
            double lastDist = this.lastDist;
            this.lastDist = dist;

            boolean onGround = event.getPlayer().isOnGround();
            boolean lastOnGround = this.lastOnGround;
            this.lastOnGround = onGround;

            float friction = 0.91f;
            double shiftedLastDist = lastDist * friction;
            double equalness = dist - shiftedLastDist;
            double scaledEqualness = equalness * 133;

            if (!onGround && !lastOnGround) {

                if (scaledEqualness >= 1 && !(scaledEqualness >= 60)) {
                    if (event.getPlayer().getInventory().getChestplate().getMaterial().equals(Material.ELYTRA)) return;
                    violate(event);
                }
            }
        });
    }

    @Override
    public String getName() {
        return "Speed A";
    }

    protected void violate(PlayerMoveEvent event) {
        if (super.violate()) event.setCancelled(true);
    }

}
