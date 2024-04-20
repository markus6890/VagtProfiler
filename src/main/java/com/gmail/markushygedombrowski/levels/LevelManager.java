package com.gmail.markushygedombrowski.levels;

import com.gmail.markushygedombrowski.settings.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class LevelManager {

    private ConfigManager configManager;
    private HashMap<Integer, Levels> levelMap = new HashMap<>();

    public LevelManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void load() {
        FileConfiguration config = configManager.getVagtLevelCfg();
        for (String key : config.getKeys(false)) {
            int level = Integer.parseInt(key);
            List<String> lore = config.getStringList(key + ".lore");
            List<String> permissions = config.getStringList(key + ".permissions");
            List<String> regionsAccess = config.getStringList(key + ".regionsAccess");
            int buffLevel = config.getInt(key + ".buffLevel");
            int shardsrate = config.getInt(key + ".shardsrate");
            double expboost = config.getDouble(key + ".expboost");
            int moneyboost = config.getInt(key + ".moneyboost");
            boolean randomBlomst = config.getBoolean(key + ".randomBlomst");
            boolean randomGlass = config.getBoolean(key + ".randomGlass");
            levelMap.put(level, new Levels(level, lore, permissions, regionsAccess, randomBlomst, randomGlass, buffLevel, shardsrate, expboost, moneyboost));
        }
    }

    public Levels getLevel(int level) {
        return levelMap.get(level);
    }

}

