package com.gmail.markushygedombrowski;

import com.gmail.markushygedombrowski.deliveredItems.DeliveredItemsLoader;
import com.gmail.markushygedombrowski.deliveredItems.ItemProfileLoader;
import com.gmail.markushygedombrowski.inventory.ChangeInvOnWarp;
import com.gmail.markushygedombrowski.inventory.InvManager;
import com.gmail.markushygedombrowski.levels.LevelRewards;
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

import java.io.IOException;
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
    private ChangeInvOnWarp changeInventory;
    private InvManager invManager;
    private LevelRewards levelRewards;

    @Override
    public void onEnable() {


        instance = this;
        loadConfigManager();
        saveDefaultConfig();
        loadSQL(getConfig());
        try {
            settings(getConfig());
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        levelRewards = new LevelRewards();
        LevelUpListener levelUpListener = new LevelUpListener(playerProfiles, levelRewards);
        getServer().getPluginManager().registerEvents(levelUpListener, this);
        Reconfigurations reconfigurations = new Reconfigurations(this, playerProfiles);
        getCommand("vagtreload").setExecutor(reconfigurations);
        System.out.println("-----------------------------");
        System.out.println("VagtProfiler has been enabled!");
        System.out.println("-----------------------------");
        saveEvery10Minutes();
    }

    @Override
    public void onDisable() {
        playerProfiles.saveAll();
        invManager.saveAll();
        System.out.println("-----------------------------");
        System.out.println("VagtProfiler has been disabled!");
        System.out.println("-----------------------------");
    }

    public void saveEvery10Minutes() {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            playerProfiles.saveAll();
        }, 0, 12000);

    }

    private void settings(FileConfiguration config) throws SQLException, IOException {
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
        invManager = new InvManager(sql);
        invManager.loadInventories();
        changeInventory = new ChangeInvOnWarp(invManager);

        playerProfiles.load();

    }

    private void loadSQL(FileConfiguration config) {
        SqlSettings sqlSettings = new SqlSettings();
        sqlSettings.load(config);
        sql = new Sql(sqlSettings);
    }

    public void reload() throws SQLException, IOException {
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

    public ChangeInvOnWarp getChangeInventory() {
        return changeInventory;
    }

    public InvManager getInvManager() {
        return invManager;
    }

    public LevelRewards getLevelRewards() {
        return levelRewards;
    }

}
