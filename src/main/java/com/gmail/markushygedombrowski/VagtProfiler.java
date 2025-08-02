package com.gmail.markushygedombrowski;

import com.gmail.markushygedombrowski.achievements.AchievementUpdater;
import com.gmail.markushygedombrowski.achievements.SimpleAchievement;
import com.gmail.markushygedombrowski.achievements.SimpleAchievementManager;
import com.gmail.markushygedombrowski.achievements.SimpleAchievementSql;
import com.gmail.markushygedombrowski.buff.BuffManager;
import com.gmail.markushygedombrowski.deliveredItems.DeliveredItemsLoader;
import com.gmail.markushygedombrowski.deliveredItems.ItemProfileLoader;
import com.gmail.markushygedombrowski.inventory.ChangeInvOnWarp;
import com.gmail.markushygedombrowski.inventory.InvManager;
import com.gmail.markushygedombrowski.levels.LevelManager;
import com.gmail.markushygedombrowski.levels.LevelRewards;
import com.gmail.markushygedombrowski.levels.LevelUpListener;
import com.gmail.markushygedombrowski.panikrum.PanikRumManager;
import com.gmail.markushygedombrowski.playerProfiles.PlayerProfiles;
import com.gmail.markushygedombrowski.settings.*;
import com.gmail.markushygedombrowski.sql.Sql;
import com.gmail.markushygedombrowski.sql.SqlSettings;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

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
    private BuffManager buffManager;
    private LevelManager levelManager;
    private AchievementUpdater achievementUpdater;
    private SimpleAchievementSql simpleAchievementSql;
    private SimpleAchievementManager simpleAchievementManager;



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

        levelRewards = new LevelRewards(levelManager,settings);
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
        System.out.println("loading settings");
        settings = new Settings();
        settings.load(config);

        deliveredItemsLoader = new DeliveredItemsLoader(sql);
        playerProfiles = new PlayerProfiles(settings, sql, deliveredItemsLoader);
        playerProfiles.load();
        vagtFangePvpConfigManager = new VagtFangePvpConfigManager();
        vagtFangePvpConfigManager.load(configM.getVagtFangePvpcfg());

        itemProfileLoader = new ItemProfileLoader();
        itemProfileLoader.load(configM.getDeliveredItemsCfg());
        panikRumManager = new PanikRumManager(configM);
        panikRumManager.load(configM.getPanikrumcfg());
        invManager = new InvManager(sql);
        invManager.loadInventories();
        changeInventory = new ChangeInvOnWarp(invManager);

        buffManager = new BuffManager(configM);
        buffManager.load();
        levelManager = new LevelManager(configM);
        levelManager.load();
        simpleAchievementManager = new SimpleAchievementManager(configM, simpleAchievementSql);
        simpleAchievementManager.setPlayerProfiles(playerProfiles); // Inject PlayerProfiles
        simpleAchievementManager.load();
        achievementUpdater = new AchievementUpdater(simpleAchievementManager);

        playerProfiles.setAchievementUpdater(achievementUpdater);

        System.out.println("settings loaded");

    }

    private void loadSQL(FileConfiguration config) {
        System.out.println("loading sql");
        SqlSettings sqlSettings = new SqlSettings();
        sqlSettings.load(config);
        sql = new Sql(sqlSettings);
        simpleAchievementSql = new SimpleAchievementSql(sql);
        simpleAchievementSql.createTableIfNotExist();
        System.out.println("sql loaded");
    }

    public void reload() throws SQLException, IOException {
        reloadConfig();
        loadConfigManager();
        settings(getConfig());


    }

    public void loadConfigManager() {
        System.out.println("loading config manager");
        configM = new ConfigManager();
        configM.setup();
        configM.saveVagtFangePvp();
        configM.saveDeliveredItems();
        configM.saveRankup();
        configM.reloadRankup();
        configM.reloadDeliveredItems();
        configM.reloadVagtFangePvp();
        configM.reloadPanikrum();
        configM.reloadBuff();
        System.out.println("config manager loaded");
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

    public BuffManager getBuffManager() {
        return buffManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public AchievementUpdater getAchievementUpdater() {
        return achievementUpdater;
    }
    public SimpleAchievementManager getSimpleAchievementManager() {
        return simpleAchievementManager;
    }


}
