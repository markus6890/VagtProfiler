package com.gmail.markushygedombrowski.levels;

import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class LevelRewards {
    private LevelManager levelManager;

    public LevelRewards(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public void giveReward(Player p, int level, PlayerProfile playerProfile) {
        Levels levels = levelManager.getLevel(level);
        if (levels == null) {
            return;
        }
        if(levels.getBuffLevel() > 0) {
            playerProfile.setProperty("buff", levels.getBuffLevel());
        }
        if(levels.getShardsrate() > 0) {
            playerProfile.setProperty("shardsrate", levels.getShardsrate());
        }
        if(levels.getExpboost() > 0) {
            playerProfile.setProperty("expboost", levels.getExpboost());
        }
        if(levels.getMoneyboost() > 0) {
            playerProfile.setProperty("moneyboost", levels.getMoneyboost());
        }
        if(levels.getPermissions() != null){
            for (String permission : levels.getPermissions()) {
                if (p.hasPermission(permission)) {
                    continue;
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set " + permission + " true prison");
            }
        }
        if(levels.getRegionsAccess() != null) {
            playerProfile.setProperty("regionsAccess", levels.getRegionsAccess());
        }
        if(levels.isRandomBlomst()) {
            Random random = new Random();
            p.getInventory().addItem(new ItemStack(Material.RED_ROSE, 1, (short) random.nextInt(8)));
        }
        if(levels.isRandomGlass()) {
            Random random = new Random();
            p.getInventory().addItem(new ItemStack(Material.STAINED_GLASS, 1, (short) random.nextInt(16)));
        }
    }

    public void updatePlayerLevel(Player p, PlayerProfile playerProfile) {
        int level = ((Double) playerProfile.getProperty("level")).intValue();
        for (int i = 1; i <= level; i++) {
            giveReward(p, i, playerProfile);
        }
    }
}
