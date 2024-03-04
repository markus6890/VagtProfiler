package com.gmail.markushygedombrowski.settings;


import com.gmail.markushygedombrowski.VagtProfiler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reconfigurations implements CommandExecutor {

    private VagtProfiler plugin;
    private Settings settings;
    public Reconfigurations(VagtProfiler plugin, Settings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!(sender.hasPermission("HLreload"))) {
            sender.sendMessage("§aYou do not have permission to do that");
            return true;
        }
        plugin.reload();

        sender.sendMessage("§a§lPlugin reloadet!");

        return true;
    }
}
