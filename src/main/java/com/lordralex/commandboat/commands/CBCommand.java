package com.lordralex.commandboat.commands;

import com.lordralex.commandboat.bukkit.CommandBoat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.InvalidPluginException;

/**
 * @version 0.1
 * @author Lord_Ralex
 * @since 0.1
 */
public abstract class CBCommand implements CommandExecutor {

    protected String name, permission, help;
    protected boolean enabled = false;

    public CBCommand(String aN, String aP, String aH) {
        if (name == null) {
            throw new NullPointerException("Command name cannot be null");
        }
        name = aN;
        permission = (aP == null ? "commandboat." + name : aP);
        help = (aH == null ? "No help set up for /" + name : aH);
    }

    public String getName() {
        return name;
    }

    public String getHelp() {
        return help;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Command getCommand() {
        return Bukkit.getPluginCommand(name);
    }

    public final boolean enable() throws InvalidPluginException {
        PluginCommand cmd = CommandBoat.getInstance().getCommand(name);
        if (cmd == null) {
            throw new NullPointerException("No command with that name");
        }
        if (!cmd.getPlugin().getName().equalsIgnoreCase(CommandBoat.getInstance().getName())) {
            throw new InvalidPluginException("The command " + name + " is not tied to the CommandBoat plugin");
        }
        cmd.setPermission(permission);
        cmd.setUsage(help);
        cmd.setExecutor(this);
        enabled = true;
        return enabled;
    }
}
