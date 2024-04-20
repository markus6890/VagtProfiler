package com.gmail.markushygedombrowski.buff;

import com.gmail.markushygedombrowski.settings.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;

public class BuffManager {

    private HashMap<Integer, BuffLevels> buffLevels = new HashMap<>();
    private ConfigManager configManager;

    public BuffManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void load(){
        buffLevels.clear();
        FileConfiguration config = configManager.getBuffCfg();
        for(String key : config.getKeys(false)){
            int level = Integer.parseInt(key);
            int speed = config.getInt(key + ".speed");
            int strength = config.getInt(key + ".strength");
            int absorption = config.getInt(key + ".absorption");
            int extraHearts = config.getInt(key + ".extraHearts");
            int maxHealth = config.getInt(key + ".maxHealth");
            buffLevels.put(level, new BuffLevels(level, speed, strength, absorption, extraHearts, maxHealth));
        }
    }

    public BuffLevels getBuffLevel(int level){
        return buffLevels.get(level);
    }



}
