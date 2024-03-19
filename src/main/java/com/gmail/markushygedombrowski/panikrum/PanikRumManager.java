package com.gmail.markushygedombrowski.panikrum;

import com.gmail.markushygedombrowski.settings.ConfigManager;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class PanikRumManager {
    private ConfigManager configManager;
    public PanikRumManager(ConfigManager configManager) {
        this.configManager = configManager;
    }
    private HashMap<String, PanikRum> panikRumHashMap = new HashMap<>();
    public void load(FileConfiguration config) {
        config.getConfigurationSection("panikrum").getKeys(false).forEach(key -> {
            String name = config.getString("panikrum." + key + ".name");
            Location location = (Location) config.get("panikrum." + key + ".location");
            PanikRum panikRum = new PanikRum(location, name);
            panikRumHashMap.put(name, panikRum);
        });
    }
    public PanikRum getPanikRum(String name) {
        return panikRumHashMap.get(name);
    }
    public void save(PanikRum panikRum) {
        FileConfiguration config = configManager.getPanikrumcfg();
        String name = panikRum.getName();
        config.set("panikrum." + name + ".name", name);
        config.set("panikrum." + name + ".location", panikRum.getLocation());
        configManager.savePanikrum();
        panikRumHashMap.put(name, panikRum);
    }
    public void remove(PanikRum panikRum) {
        FileConfiguration config = configManager.getPanikrumcfg();
        String name = panikRum.getName();
        config.set("panikrum." + name, null);
        configManager.savePanikrum();
        panikRumHashMap.remove(name);
    }
}
