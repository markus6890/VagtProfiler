package com.gmail.markushygedombrowski.playerProfiles;


import com.gmail.markushygedombrowski.settings.Settings;
import com.gmail.markushygedombrowski.deliveredItems.DeliveredItems;
import com.gmail.markushygedombrowski.deliveredItems.DeliveredItemsLoader;
import com.gmail.markushygedombrowski.deliveredItems.PLayerDeliveredItems;
import com.gmail.markushygedombrowski.sql.Sql;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfiles {
    private Map<UUID, PlayerProfile> profileMap = new HashMap<>();
    private Settings settings;

    private Sql sql;
    private DeliveredItemsLoader deliveredItemsLoader;

    public PlayerProfiles(Settings settings, Sql sql, DeliveredItemsLoader deliveredItemsLoader) {

        this.settings = settings;
        this.sql = sql;
        this.deliveredItemsLoader = deliveredItemsLoader;
    }

    public void load() throws SQLException {
        try (Connection connection = sql.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM vagtprofile");
             ResultSet resultSet = statement.executeQuery()) {

            profileMap.clear();

            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("UUID"));
                String name = resultSet.getString("name");
                int deaths = resultSet.getInt("deaths");
                int kills = resultSet.getInt("kills");
                int pvs = resultSet.getInt("pvs");
                int level = resultSet.getInt("level");
                int exp = resultSet.getInt("exp");
                int vagtposter = resultSet.getInt("vagtposter");
                int salary = resultSet.getInt("salary");
                int achevments = resultSet.getInt("achevments");
                int shardsrate = resultSet.getInt("shardsrate");
                DeliveredItems deliveredItems = deliveredItemsLoader.loadDeliveredItems(uuid);
                if(shardsrate == 0){
                    shardsrate = 1;
                }
                PlayerProfile profile = new PlayerProfile(uuid, name, pvs, level, salary, deaths, kills, exp, vagtposter, achevments, deliveredItems,shardsrate);
                profileMap.put(uuid, profile);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveAll() {
        profileMap.values().forEach(profile -> {
            try {
                save(profile);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


    }

    public Map<UUID, PlayerProfile> getProfileMap() {
        return profileMap;
    }


    public void save(PlayerProfile profile) throws SQLException {
        Connection connection = sql.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO vagtprofile (UUID, name, deaths, kills, pvs, level, nextlevelexp, exp, vagtposter, salary, achevments,shardsrate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ?) ON DUPLICATE KEY UPDATE name = ?, deaths = ?, kills = ?, pvs = ?, level = ?, nextlevelexp = ?, exp = ?, vagtposter = ?, salary = ?, achevments = ?, shardsrate = ?");

        statement.setString(1, profile.getUuid().toString());
        statement.setString(2, profile.getName());
        statement.setInt(3, profile.getDeaths());
        statement.setInt(4, profile.getKills());
        statement.setInt(5, profile.getPv());
        statement.setInt(6, profile.getLvl());
        statement.setDouble(7, profile.getXpToNextLvl());
        statement.setInt(8, profile.getXp());
        statement.setInt(9, profile.getVagtposter());
        statement.setInt(10, profile.getLon());
        statement.setInt(11, profile.getAchievements());
        statement.setInt(12, profile.getShardrate());

        statement.setString(13, profile.getName());
        statement.setInt(14, profile.getDeaths());
        statement.setInt(15, profile.getKills());
        statement.setInt(16, profile.getPv());
        statement.setInt(17, profile.getLvl());
        statement.setDouble(18, profile.getXpToNextLvl());
        statement.setInt(19, profile.getXp());
        statement.setInt(20, profile.getVagtposter());
        statement.setInt(21, profile.getLon());
        statement.setInt(22, profile.getAchievements());
        statement.setInt(23, profile.getShardrate());

        statement.executeUpdate();
        deliveredItemsLoader.saveDeliveredItems(profile.getDeliveredItems());
        profileMap.put(profile.getUuid(), profile);
        sql.closeAllSQL(connection, statement, null);
    }

    public PlayerProfile getPlayerProfile(UUID uuid) {
        return profileMap.get(uuid);
    }

    public void createVagt(Player p, PlayerProfile profile) throws SQLException {
        if (profile != null) return;

        int lon = settings.getLonp();
        if (p.hasPermission("officer")) {
            lon = settings.getLonoffi();
        } else if (p.hasPermission("a-vagt")) {
            lon = settings.getLona();
        } else if (p.hasPermission("b-vagt")) {
            lon = settings.getLonb();
        } else if (p.hasPermission("c-vagt")) {
            lon = settings.getLonc();
        }

        PLayerDeliveredItems deliveredItems = new PLayerDeliveredItems(p.getUniqueId(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        profile = new PlayerProfile(p.getUniqueId(), p.getName(), 1, 1, lon, 0, 0, 0, 0, 0, deliveredItems,1);
        System.out.println("name" + profile.getName());
        System.out.println("UUID" + profile.getUuid());
        System.out.println("løn" + profile.getLon());
        System.out.println("deaths" + profile.getDeaths());
        System.out.println("kills" + profile.getKills());
        save(profile);
    }

    public void removeVagt(PlayerProfile profile) {
        try {
            Connection connection = sql.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM vagtprofile WHERE UUID = ?");
            statement.setString(1, profile.getUuid().toString());
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM DeliveredItems WHERE UUID = ?");
            statement.setString(1, profile.getUuid().toString());
            statement.executeUpdate();
            sql.closeAllSQL(connection, statement, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        profileMap.remove(profile.getUuid());
    }

}
