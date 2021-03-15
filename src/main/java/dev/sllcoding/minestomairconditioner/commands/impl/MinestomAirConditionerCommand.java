package dev.sllcoding.minestomairconditioner.commands.impl;

import net.minestom.server.command.builder.Command;

public class MinestomAirConditionerCommand extends Command {

    public MinestomAirConditionerCommand() {
        super("minestomairconditioner", "mac", "airconditioner");
        setDefaultExecutor((sender, context) -> {
            // TODO: Open management GUI.
        });

        addSubcommand(new Info());
    }

    protected static class Info extends Command {
        public Info() {
            super("info", "information");
            setDefaultExecutor((sender, context) -> sender.sendMessage("You are running MinestomAirConditioner InDev"));
        }
    }

}
