package dev.sllcoding.minestomairconditioner.commands;

import dev.sllcoding.minestomairconditioner.commands.impl.MinestomAirConditionerCommand;
import net.minestom.server.MinecraftServer;

public class CommandManager {

    public static void setup() {
        MinecraftServer.getCommandManager().register(new MinestomAirConditionerCommand());
    }

}
