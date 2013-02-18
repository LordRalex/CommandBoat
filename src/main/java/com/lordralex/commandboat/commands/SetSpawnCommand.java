package com.lordralex.commandboat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @version 0.1
 * @author Lord_Ralex
 * @since 0.1
 */
public class SetSpawnCommand extends CBCommand {

    public SetSpawnCommand() {
        this(null, null);
    }

    public SetSpawnCommand(String aPerm, String aHelp) {
        super("setspawn", aPerm, aHelp);
    }

    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (cs instanceof Player) {
            Player pl = (Player) cs;
            pl.getWorld().setSpawnLocation(pl.getLocation().getBlockX(), pl.getLocation().getBlockY(), pl.getLocation().getBlockZ());
            pl.sendMessage(ChatColor.YELLOW + "Spawn for " + pl.getWorld().getName() + " was set");
        } else {
            cs.sendMessage(ChatColor.RED + "You must be a player to use this command");
        }
        return true;
    }
}
