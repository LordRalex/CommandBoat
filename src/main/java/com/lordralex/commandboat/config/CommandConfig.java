package com.lordralex.commandboat.config;

/**
 * @version 0.1
 * @author Lord_Ralex
 * @since 0.1
 */
public class CommandConfig extends Config {

    public CommandConfig() {
        super("commands.yml");
    }

    public CommandConfig(String fileName) {
        super(fileName);
    }
}
