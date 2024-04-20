package com.gmail.markushygedombrowski.levels;

import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class LevelRewards {


    public void giveReward(Player p, int level, PlayerProfile playerProfile) {
        switch (level) {
            case 25:
                playerProfile.setProperty("shardsrate", 2);
                break;
            case 23:
                Random random = new Random();
                ItemStack glass = new ItemStack(Material.GLASS, 1, (short) random.nextInt(15));
                p.getInventory().addItem(glass);
                break;
            case 20:
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set vagta.celle true prison");
                break;
            case 18:
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set vagtb.celle true prison");
                break;
            case 10:
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set vagtc.celle true prison");
                break;

        }

    }

    public void updatePlayerLevel(Player p, PlayerProfile playerProfile) {
        int level = ((Double) playerProfile.getProperty("level")).intValue();
        if (level >= 25) {
            if (((Double) playerProfile.getProperty("shardsrate")).intValue() == 1) {
                playerProfile.setProperty("shardsrate", 2);
            }
        }
        if (level >= 20) {
            if (p.hasPermission("vagta.celle")) {
                return;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set vagta.celle true prison");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set vagtb.celle true prison");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set vagtc.celle true prison");
        } else if (level >= 18) {
            if (p.hasPermission("vagtb.celle")) {
                return;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set vagtb.celle true prison");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set vagtc.celle true prison");
        } else if (level >= 10) {
            if (p.hasPermission("vagtc.celle")) {
                return;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set vagtc.celle true prison");
        }
    }
}
