package dev.sllcoding.minestomairconditioner.checks.impl;

import dev.sllcoding.minestomairconditioner.checks.Check;
import dev.sllcoding.minestomairconditioner.profiles.Profile;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;

public class NoFallACheck extends Check {

    private boolean lastOnGround, lastLastOnGround;

    public NoFallACheck(Profile profile) {
        super(profile);
        profile.getPlayer().addEventCallback(PlayerMoveEvent.class, event -> {
            Position to = event.getNewPosition();

            boolean onGround = isNearGround(event.getPlayer().getInstance(), to);
            boolean lastOnGround = this.lastOnGround;
            this.lastOnGround = onGround;
            boolean lastLastOnGround = this.lastLastOnGround;
            this.lastLastOnGround = lastOnGround;

            if (!onGround && !lastOnGround && !lastLastOnGround) {
                if (event.getPlayer().isOnGround()) {
                    violate(event);
                }
            }
        });
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
        return "NoFall A";
    }

    protected void violate(PlayerMoveEvent event) {
        if (super.violate()) event.setCancelled(true);
    }

}
