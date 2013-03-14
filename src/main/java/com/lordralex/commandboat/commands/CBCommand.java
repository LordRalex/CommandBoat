package com.lordralex.commandboat.commands;

import com.lordralex.commandboat.bukkit.CommandBoat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.InvalidPluginException;

/**
 * @version 0.1
 * @author Lord_Ralex
 * @since 0.1
 */
public abstract class CBCommand implements CommandExecutor, Listener {

    protected String name, permission, help, nopermission;
    protected boolean enabled = false;

    protected CBCommand(String aN, String aP, String aH, String aNoPerm) {
        if (name == null) {
            throw new NullPointerException("Command name cannot be null");
        }
        name = aN;
        permission = (aP == null ? "commandboat." + name : aP);
        help = (aH == null || aH.isEmpty() ? "No help set up for /" + name : aH);
        nopermission = (aNoPerm == null || aNoPerm.isEmpty() ? ChatColor.RED + "You do not have permission to use this command" : aNoPerm);
    }

    protected CBCommand(String aN, String aP, String aH) {
        this(aN, aP, aH, null);
    }

    protected CBCommand(String aN) {
        this(aN, null, null, null);
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

    public final Command getCommand() {
        return Bukkit.getPluginCommand(name);
    }

    public final boolean enable() throws InvalidPluginException {
        enabled = false;
        PluginCommand cmd = CommandBoat.getInstance().getCommand(name);
        if (cmd == null) {
            throw new NullPointerException("No command with the name " + name + " was found within Bukkit");
        }
        if (!cmd.getPlugin().getName().equalsIgnoreCase(CommandBoat.getInstance().getName())) {
            throw new InvalidPluginException("The command " + name + " is not tied to the CommandBoat plugin, but instead to " + cmd.getPlugin().getName());
        }
        cmd.setPermission(permission);
        cmd.setUsage(help);
        cmd.setPermissionMessage(nopermission);
        cmd.setExecutor(this);
        if (hasListener()) {
            Bukkit.getPluginManager().registerEvents(this, CommandBoat.getInstance());
        }
        enabled = true;
        return enabled;
    }

    public boolean hasListener() {
        return false;
    }
}
