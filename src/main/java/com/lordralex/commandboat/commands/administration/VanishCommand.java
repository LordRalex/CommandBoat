package com.lordralex.commandboat.commands.administration;

import com.lordralex.commandboat.commands.CBCommand;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @version 0.1
 * @author Lord_Ralex
 * @since 0.1
 */
public class VanishCommand extends CBCommand {

    private HashMap<String, Boolean> vanishState = new HashMap<String, Boolean>();

    public VanishCommand() {
        this(null, null, null);
    }

    public VanishCommand(String aP, String aH) {
        this(aP, aH, null);
    }

    public VanishCommand(String aPerm, String aHelp, String aNoPerm) {
        super("vanish", aPerm, aHelp, aNoPerm);
    }

    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        String target = cs.getName();
        int setTo = 0;
        boolean showStatus = false;
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals("-p")) {
                target = strings[i + 1];
            } else if (strings[i].equals("on")) {
                setTo = 1;
            } else if (strings[i].equals("off")) {
                setTo = -1;
            } else if (strings[i].equals("status")) {
                showStatus = true;
            }
        }
        if (!(cs instanceof Player) && target.equalsIgnoreCase(cs.getName())) {
            cs.sendMessage("Non-players cannot be vanished");
            return true;
        }
        Player pl = Bukkit.getPlayer(target);
        if (pl == null || !pl.isOnline()) {
            cs.sendMessage("That player is not online");
            return true;
        }
        Boolean oldStatus = vanishState.get(pl.getName());
        if (oldStatus == null) {
            oldStatus = false;
        }
        if (showStatus) {
            cs.sendMessage(pl.getName() + " was" + ((oldStatus) ? "" : " not") + " vanished");
        }
        Boolean newStatus = false;
        if (setTo == 0) {
            newStatus = !oldStatus;
        } else if (setTo == 1) {
            newStatus = true;
        } else if (showStatus) {
            return true;
        } else {
            newStatus = false;
        }
        vanishState.put(pl.getName(), newStatus);
        cs.sendMessage(pl.getName() + " is now " + ((newStatus) ? "vanished" : "visible"));
        if (!pl.getName().equalsIgnoreCase(cs.getName())) {
            pl.sendMessage("You are now " + ((newStatus) ? "vanished" : "visible"));
        }
        updatePlayerVisibility(pl, newStatus);
        return true;
    }

    @Override
    public boolean hasListener() {
        return true;
    }

    public void updatePlayerVisibility(Player player, boolean newStatus) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(player.getName())) {
                continue;
            }
            if (newStatus) {
                if (!p.hasPermission(this.getPermission() + ".admin")) {
                    p.hidePlayer(player);
                }
            } else {
                p.showPlayer(player);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        updatePlayerVisibility(event.getPlayer(), vanishState.get(event.getPlayer().getName()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        vanishState.remove(event.getPlayer().getName());
    }
}
