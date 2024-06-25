package com.gmail.markushygedombrowski.levels;

import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import com.gmail.markushygedombrowski.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class LevelRewards {
    private LevelManager levelManager;
    private Settings settings;
    public LevelRewards(LevelManager levelManager, Settings settings) {
        this.levelManager = levelManager;
        this.settings = settings;
    }

    public void giveReward(Player p, int level, PlayerProfile playerProfile, boolean giveFlowers, boolean giveGlass) {
        int salary = settings.getLonp() + (level * 1000);
        playerProfile.setProperty("salary", salary);
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
        if(levels.isRandomBlomst() && giveFlowers) {
            Random random = new Random();
            p.getInventory().addItem(new ItemStack(Material.RED_ROSE, 1, (short) random.nextInt(8)));
        }
        if(levels.isRandomGlass() && giveGlass) {
            Random random = new Random();
            p.getInventory().addItem(new ItemStack(Material.STAINED_GLASS, 1, (short) random.nextInt(16)));
        }
    }

    public void updatePlayerLevel(Player p, PlayerProfile playerProfile) {
        int level = playerProfile.castPropertyToInt(playerProfile.getProperty("level"));

        for (int i = 1; i <= level; i++) {
            giveReward(p, i, playerProfile,false,false);
        }
    }
}
