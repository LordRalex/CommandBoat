package com.lordralex.commandboat.bukkit;

import com.lordralex.commandboat.commands.*;
import com.lordralex.commandboat.commands.enums.CommandList;
import com.lordralex.commmandboat.config.CommandConfig;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @version 0.1
 * @author Lord_Ralex
 * @since 0.1
 */
public class CommandBoat extends JavaPlugin {

    private static CommandBoat instance = null;
    private CommandConfig config = null;

    @Override
    public void onLoad() {
        try {
            saveDefaultConfig();
            config = new CommandConfig();
            config.load();
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "An error has occured", ex);
            Bukkit.getPluginManager().disablePlugin(this);
        } catch (InvalidConfigurationException ex) {
            getLogger().log(Level.SEVERE, "An error has occured", ex);
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        //load commands
        List<CBCommand> commands = new ArrayList<CBCommand>();
        List<String> cmds = getConfig().getStringList("enabled-commands");
        if (cmds != null) {
            for (String cmd : cmds) {
                CommandList cL = CommandList.getCommand(cmd);
                if (cL == null) {
                    continue;
                }
                try {
                    String help = config.getString(cL.getName() + ".help");
                    String perm = config.getString(cL.getName() + ".permission");
                    CBCommand command = (CBCommand) cL.getCommandClass().getConstructor(String.class, String.class).newInstance(perm, help);
                    commands.add(command);
                } catch (NoSuchMethodException ex) {
                    getLogger().log(Level.SEVERE, "Issue loading command " + cL.getName(), ex);
                } catch (SecurityException ex) {
                    getLogger().log(Level.SEVERE, "Issue loading command " + cL.getName(), ex);
                } catch (InstantiationException ex) {
                    getLogger().log(Level.SEVERE, "Issue loading command " + cL.getName(), ex);
                } catch (IllegalAccessException ex) {
                    getLogger().log(Level.SEVERE, "Issue loading command " + cL.getName(), ex);
                } catch (ClassCastException ex) {
                    getLogger().log(Level.SEVERE, "Issue loading command " + cL.getName(), ex);
                } catch (IllegalArgumentException ex) {
                    getLogger().log(Level.SEVERE, "Issue loading command " + cL.getName(), ex);
                } catch (InvocationTargetException ex) {
                    getLogger().log(Level.SEVERE, "Issue loading command " + cL.getName(), ex);
                }
            }
        }

        //enables all commands loaded and builds the commandbook.commands.* perm
        Map<String, Boolean> children = new HashMap<String, Boolean>();
        for (CBCommand cmd : commands) {
            try {
                cmd.enable();
                children.put(cmd.getPermission(), true);
            } catch (NullPointerException e) {
                getLogger().severe(e.getMessage());
            } catch (InvalidPluginException e) {
                getLogger().severe(e.getMessage());
            }
        }
        Permission starperm = new Permission("commandbook.commands.*", PermissionDefault.OP, children);
        starperm.recalculatePermissibles();

        //hook into vault
    }

    @Override
    public void onDisable() {
    }

    public static CommandBoat getInstance() {
        return instance;
    }
}
