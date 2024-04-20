package com.gmail.markushygedombrowski.settings;


import com.gmail.markushygedombrowski.VagtProfiler;
import com.gmail.markushygedombrowski.playerProfiles.PlayerProfiles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.sql.SQLException;

public class Reconfigurations implements CommandExecutor {

    private VagtProfiler plugin;
    private PlayerProfiles playerProfiles;
    public Reconfigurations(VagtProfiler plugin, PlayerProfiles playerProfiles) {
        this.plugin = plugin;
        this.playerProfiles = playerProfiles;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!(sender.hasPermission("HLreload"))) {
            sender.sendMessage("§aYou do not have permission to do that");
            return true;
        }
        if(alias.equalsIgnoreCase("printproperties")) {
            sender.sendMessage("§aProperties:");
            playerProfiles.getPropertiesNames().forEach(sender::sendMessage);
            return true;
        }
        try {
            plugin.reload();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        sender.sendMessage("§a§lPlugin reloadet!");

        return true;
    }
}
