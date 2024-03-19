package com.gmail.markushygedombrowski;

import com.gmail.markushygedombrowski.deliveredItems.DeliveredItemsLoader;
import com.gmail.markushygedombrowski.deliveredItems.ItemProfileLoader;
import com.gmail.markushygedombrowski.levels.LevelUpListener;
import com.gmail.markushygedombrowski.panikrum.PanikRumManager;
import com.gmail.markushygedombrowski.playerProfiles.PlayerProfiles;
import com.gmail.markushygedombrowski.settings.ConfigManager;
import com.gmail.markushygedombrowski.settings.Reconfigurations;
import com.gmail.markushygedombrowski.settings.Settings;
import com.gmail.markushygedombrowski.settings.VagtFangePvpConfigManager;
import com.gmail.markushygedombrowski.sql.Sql;
import com.gmail.markushygedombrowski.sql.SqlSettings;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class VagtProfiler extends JavaPlugin {
    private Settings settings;
    private Sql sql;
    private DeliveredItemsLoader deliveredItemsLoader;
    private PlayerProfiles playerProfiles;
    private ConfigManager configM;
    private VagtFangePvpConfigManager vagtFangePvpConfigManager;
    private static VagtProfiler instance;
    private ItemProfileLoader itemProfileLoader;
    private PanikRumManager panikRumManager;

    @Override
    public void onEnable() {
        Reconfigurations reconfigurations = new Reconfigurations(this);
        getCommand("vagtreload").setExecutor(reconfigurations);

        instance = this;
        loadConfigManager();
        saveDefaultConfig();
        loadSQL(getConfig());
        settings(getConfig());




        LevelUpListener levelUpListener = new LevelUpListener(playerProfiles);
        getServer().getPluginManager().registerEvents(levelUpListener, this);
        System.out.println("-----------------------------");
        System.out.println("VagtProfiler has been enabled!");
        System.out.println("-----------------------------");
        saveEvery10Minutes();
    }

    @Override
    public void onDisable() {
        playerProfiles.saveAll();
        System.out.println("-----------------------------");
        System.out.println("VagtProfiler has been disabled!");
        System.out.println("-----------------------------");
    }

    public void saveEvery10Minutes() {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            playerProfiles.saveAll();
        }, 0, 12000);

    }

    private void settings(FileConfiguration config) {
        settings = new Settings();
        settings.load(config);

        deliveredItemsLoader = new DeliveredItemsLoader(sql);
        playerProfiles = new PlayerProfiles(settings, sql, deliveredItemsLoader);

        vagtFangePvpConfigManager = new VagtFangePvpConfigManager();
        vagtFangePvpConfigManager.load(configM.getVagtFangePvpcfg());

        itemProfileLoader = new ItemProfileLoader();
        itemProfileLoader.load(configM.getDeliveredItemsCfg());
        panikRumManager = new PanikRumManager(configM);
        panikRumManager.load(configM.getPanikrumcfg());

        try {
            playerProfiles.load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSQL(FileConfiguration config) {
        SqlSettings sqlSettings = new SqlSettings();
        sqlSettings.load(config);
        sql = new Sql(sqlSettings);
    }

    public void reload() {
        reloadConfig();
        FileConfiguration config = getConfig();
        loadConfigManager();
        settings(getConfig());




    }

    public void loadConfigManager() {
        configM = new ConfigManager();
        configM.setup();
        configM.saveVagtFangePvp();
        configM.saveDeliveredItems();
        configM.saveRankup();
        configM.reloadRankup();
        configM.reloadDeliveredItems();
    }

    public PlayerProfiles getPlayerProfiles() {
        return playerProfiles;
    }

    public Settings getSettings() {
        return settings;
    }

    public ItemProfileLoader getItemProfileLoader() {
        return itemProfileLoader;
    }

    public VagtFangePvpConfigManager getVagtFangePvpConfigManager() {
        return vagtFangePvpConfigManager;
    }
    public ConfigManager getConfigManager() {
        return configM;
    }
    public PanikRumManager getPanikRumManager() {
        return panikRumManager;
    }
    public static VagtProfiler getInstance() {
        return instance;
    }

}
