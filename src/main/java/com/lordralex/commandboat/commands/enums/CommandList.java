package com.lordralex.commandboat.commands.enums;

import com.lordralex.commandboat.commands.teleportation.*;

/**
 * @version 0.1
 * @author Lord_Ralex
 * @since 0.1
 */
public enum CommandList {

    Spawn("spawn", SpawnCommand.class),
    SetSpawn("setspawn", SetSpawnCommand.class);
    private final Class cl;
    private final String name;

    private CommandList(String aN, Class c) {
        name = aN;
        cl = c;
    }

    public Class getCommandClass() {
        return cl;
    }

    public static CommandList getCommand(String aN) {
        for (CommandList c : CommandList.values()) {
            if (c.name.equalsIgnoreCase(aN)) {
                return c;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
