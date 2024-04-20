package com.gmail.markushygedombrowski.levels;

import com.gmail.markushygedombrowski.settings.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

public class LevelManager {

    private ConfigManager configManager;
    private HashMap<Integer, List<String>> levelLore = new HashMap<>();

    public LevelManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void load() {
        FileConfiguration config = configManager.getVagtLevelCfg();
        for (String key: config.getKeys(false)) {
            int level = Integer.parseInt(key);
            List<String> lore = config.getStringList(key + ".lore");
            levelLore.put(level, lore);
        }
    }
    public List<String> getLevelLore(int level) {
        return levelLore.get(level);
    }
}

