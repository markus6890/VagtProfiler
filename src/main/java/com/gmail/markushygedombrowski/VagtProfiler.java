package com.gmail.markushygedombrowski;

import com.gmail.markushygedombrowski.sql.Sql;
import com.gmail.markushygedombrowski.sql.SqlSettings;
import org.bukkit.plugin.java.JavaPlugin;

public class VagtProfiler extends JavaPlugin {

        @Override
        public void onEnable() {

            saveDefaultConfig();
            SqlSettings sqlSettings = new SqlSettings();
            sqlSettings.load(getConfig());
            Sql sql = new Sql(sqlSettings);

            System.out.println("-----------------------------");
            System.out.println("VagtProfiler has been enabled!");
            System.out.println("-----------------------------");
        }

        @Override
        public void onDisable() {
            System.out.println("-----------------------------");
            System.out.println("VagtProfiler has been disabled!");
            System.out.println("-----------------------------");
        }
}
