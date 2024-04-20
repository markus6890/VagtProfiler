package com.gmail.markushygedombrowski.settings;

import com.gmail.markushygedombrowski.VagtProfiler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private VagtProfiler plugin = VagtProfiler.getPlugin(VagtProfiler.class);


    public FileConfiguration vagtFangePvpcfg;
    public File vagtFangePvpFile;
    public FileConfiguration deliveredItemsCfg;
    public File deliveredItemsFile;
    public FileConfiguration rankupCfg;
    public File rankupFile;
    public FileConfiguration panikrumcfg;
    public File panikrumFile;
    public FileConfiguration buffCfg;
    public File buffFile;
    public FileConfiguration vagtLevelCfg;
    public File vagtLevelFile;



    public void setup() {
        List<File> configList = new ArrayList<>();
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();

        }
        vagtFangePvpFile = new File(plugin.getDataFolder(), "vagtFangePvp.yml");
        deliveredItemsFile = new File(plugin.getDataFolder(), "deliveredItems.yml");
        rankupFile = new File(plugin.getDataFolder(), "rankup.yml");
        panikrumFile = new File(plugin.getDataFolder(), "panikrum.yml");
        buffFile = new File(plugin.getDataFolder(), "buff.yml");
        vagtLevelFile = new File(plugin.getDataFolder(), "vagtLevel.yml");
        configList.add(vagtFangePvpFile);
        configList.add(deliveredItemsFile);
        configList.add(rankupFile);
        configList.add(panikrumFile);
        configList.add(buffFile);
        configList.add(vagtLevelFile);
        configList.forEach(file -> {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not create " + file.getName() + "File");
                }
            }
        });
        vagtFangePvpcfg = YamlConfiguration.loadConfiguration(vagtFangePvpFile);
        deliveredItemsCfg = YamlConfiguration.loadConfiguration(deliveredItemsFile);
        rankupCfg = YamlConfiguration.loadConfiguration(rankupFile);
        panikrumcfg = YamlConfiguration.loadConfiguration(panikrumFile);
        buffCfg = YamlConfiguration.loadConfiguration(buffFile);
        vagtLevelCfg = YamlConfiguration.loadConfiguration(vagtLevelFile);


    }
    public FileConfiguration getBuffCfg() {
        return buffCfg;
    }

    public FileConfiguration getVagtLevelCfg() {
        return vagtLevelCfg;
    }
    public FileConfiguration getVagtFangePvpcfg() {
        return vagtFangePvpcfg;
    }

    public FileConfiguration getDeliveredItemsCfg() {
        return deliveredItemsCfg;
    }

    public FileConfiguration getRankupCfg() {
        return rankupCfg;
    }

    public FileConfiguration getPanikrumcfg() {
        return panikrumcfg;
    }


    public void saveDeliveredItems() {
        try {
            deliveredItemsCfg.save(deliveredItemsFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not save deliveredItems.yml File");
        }
    }
    public void savePanikrum() {
        try {
            panikrumcfg.save(panikrumFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not save panikrum.yml File");
        }
    }
    public void saveVagtLevel() {
        try {
            vagtLevelCfg.save(vagtLevelFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not save vagtLevel.yml File");
        }
    }
    public void reloadVagtLevel() {
        vagtLevelCfg = YamlConfiguration.loadConfiguration(vagtLevelFile);
    }


    public void reloadDeliveredItems() {
        deliveredItemsCfg = YamlConfiguration.loadConfiguration(deliveredItemsFile);
    }

    public void reloadVagtFangePvp() {
        vagtFangePvpcfg = YamlConfiguration.loadConfiguration(vagtFangePvpFile);
    }

    public void reloadRankup() {
        rankupCfg = YamlConfiguration.loadConfiguration(rankupFile);
    }

    public void reloadPanikrum() {
        panikrumcfg = YamlConfiguration.loadConfiguration(panikrumFile);
    }

    public void reloadBuff() {
        buffCfg = YamlConfiguration.loadConfiguration(buffFile);
    }

    public void saveVagtFangePvp() {
        try {
            vagtFangePvpcfg.save(vagtFangePvpFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not save vagtFangePvp.yml File");
        }
    }

    public void saveRankup() {
        try {
            rankupCfg.save(rankupFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not save rankup.yml File");
        }
    }
    public void saveBuff() {
        try {
            buffCfg.save(buffFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not save buff.yml File");
        }
    }
}
