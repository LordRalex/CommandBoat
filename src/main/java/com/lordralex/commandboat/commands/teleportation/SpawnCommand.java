package com.lordralex.commandboat.commands.teleportation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.lordralex.commandboat.commands.CBCommand;

/**
 * @version 0.1
 * @author Lord_Ralex
 * @since 0.1
 */
public class SpawnCommand extends CBCommand {

    public SpawnCommand() {
        this(null, null, null);
    }

    public SpawnCommand(String aP, String aH) {
        this(aP, aH, null);
    }

    public SpawnCommand(String aPerm, String aHelp, String aNoPerm) {
        super("spawn", aPerm, aHelp, aNoPerm);
    }

    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (args.length == 0) {
            if (cs instanceof Player) {
                args = new String[]{cs.getName()};
            } else {
                cs.sendMessage(ChatColor.RED + "Invalid usage: " + getHelp());
                return true;
            }
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            cs.sendMessage(ChatColor.RED + "That player is not online");
            return true;
        }
        if (!target.getName().equalsIgnoreCase(cs.getName())) {
            if (!cs.hasPermission(permission + ".other")) {
                cs.sendMessage(ChatColor.RED + "You do not have permission to teleport others");
                return true;
            }
        }
        target.teleport(target.getWorld().getSpawnLocation());
        target.sendMessage(ChatColor.YELLOW + "You were teleported to spawn");
        if (!target.getName().equalsIgnoreCase(cs.getName())) {
            cs.sendMessage(ChatColor.YELLOW + target.getName() + " was teleported to the spawn");
        }
        return true;
    }
}
