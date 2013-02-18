package com.lordralex.commandboat.bukkit;

import com.lordralex.commandboat.commands.*;
import com.lordralex.commmandboat.config.Config;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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

    private static CommandBoat instance;
    private Config config;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        config = new Config();
        config.load();
    }

    @Override
    public void onEnable() {
        instance = this;
        //load commands
        List<CBCommand> commands = new ArrayList<CBCommand>();
        List<String> cmds = getConfig().getStringList("enabled-commands");
        for (String cmd : cmds) {
            CommandList cL = CommandList.getCommand(cmd);
            if (cL == null) {
                return;
            }
            try {
                String help = config.get(cL.name + ".help");
                String perm = config.get(cL.name + ".permission");
                CBCommand command = (CBCommand) cL.getCommandClass().getConstructor(String.class, String.class).newInstance(perm, help);
                commands.add(command);
            } catch (NoSuchMethodException ex) {
                getLogger().log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                getLogger().log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                getLogger().log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                getLogger().log(Level.SEVERE, null, ex);
            } catch (ClassCastException ex) {
                getLogger().log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                getLogger().log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                getLogger().log(Level.SEVERE, null, ex);
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

        //hook into vault
    }

    @Override
    public void onDisable() {
    }

    public static CommandBoat getInstance() {
        return instance;
    }

    private enum CommandList {

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
    }
}
