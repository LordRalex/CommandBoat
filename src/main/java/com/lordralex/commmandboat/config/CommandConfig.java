package com.lordralex.commmandboat.config;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @version 0.1
 * @author Lord_Ralex
 * @since 0.1
 */
public class CommandConfig extends Config {

    private FileConfiguration advanced;

    public CommandConfig() {
        super("commands.yml");
    }

    public CommandConfig(String fileName) {
        super(fileName);
    }
}
