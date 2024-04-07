package com.gmail.markushygedombrowski.playerProfiles;


import com.gmail.markushygedombrowski.settings.Settings;
import com.gmail.markushygedombrowski.deliveredItems.DeliveredItems;
import com.gmail.markushygedombrowski.deliveredItems.DeliveredItemsLoader;
import com.gmail.markushygedombrowski.deliveredItems.PLayerDeliveredItems;
import com.gmail.markushygedombrowski.sql.Sql;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.entity.Player;

import java.sql.*;
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
        createTableIfNotExist();
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

    public void createTableIfNotExist() {
        try (Connection connection = sql.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "vagtprofile", null);

            if (!tables.next()) {
                // Table does not exist
                String createTableQuery = "CREATE TABLE vagtprofile ("
                        + "UUID VARCHAR(36) PRIMARY KEY,"
                        + "name VARCHAR(255),"
                        + "properties TEXT"
                        + ");";

                try (Statement statement = connection.createStatement()) {
                    statement.execute(createTableQuery);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void migrateData() throws SQLException {
        // Load all the data from the old database
        try (Connection connection = sql.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM vagtprofile_old");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Create a new PlayerProfile using the new constructor
                UUID uuid = UUID.fromString(resultSet.getString("UUID"));
                String name = resultSet.getString("name");
                PlayerProfile profile = new PlayerProfile(uuid, name);

                // For each property in the old PlayerProfile, add it to the properties map of the new PlayerProfile
                profile.setProperty("pv", resultSet.getInt("pvs"));
                profile.setProperty("level", resultSet.getInt("level"));
                profile.setProperty("salary", resultSet.getInt("salary"));
                profile.setProperty("deaths", resultSet.getInt("deaths"));
                profile.setProperty("kills", resultSet.getInt("kills"));
                profile.setProperty("exp", resultSet.getInt("exp"));
                profile.setProperty("achievements", resultSet.getInt("achievements"));
                profile.setProperty("shardrate", resultSet.getInt("shardsrate"));
                profile.setProperty("vagtposter", resultSet.getInt("vagtposter"));

                // Save the new PlayerProfile to the new database
                save(profile);
            }
        }
    }


    public void save(PlayerProfile profile) throws SQLException {
        try (Connection connection = sql.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO vagtprofile (UUID, name, properties) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE name = ?, properties = ?");

            statement.setString(1, profile.getUuid().toString());
            statement.setString(2, profile.getName());
            statement.setString(3, new Gson().toJson(profile.getProperties()));
            statement.setString(4, profile.getName());
            statement.setString(5, new Gson().toJson(profile.getProperties()));

            statement.executeUpdate();
            profileMap.put(profile.getUuid(), profile);
        }
    }

    public void load() throws SQLException {
        try (Connection connection = sql.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM vagtprofile");
             ResultSet resultSet = statement.executeQuery()) {

            profileMap.clear();

            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("UUID"));
                String name = resultSet.getString("name");
                String propertiesJson = resultSet.getString("properties");
                Map<String, Object> properties = new Gson().fromJson(propertiesJson, new TypeToken<Map<String, Object>>() {
                }.getType());

                PlayerProfile profile = new PlayerProfile(uuid, name);
                for (Map.Entry<String, Object> entry : properties.entrySet()) {
                    profile.setProperty(entry.getKey(), entry.getValue());
                }
                profile.setDeliveredItems(deliveredItemsLoader.loadDeliveredItems(uuid));
                profileMap.put(uuid, profile);
            }
        }
    }


    public Map<UUID, PlayerProfile> getProfileMap() {
        return profileMap;
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
        profile = new PlayerProfile(p.getUniqueId(), p.getName());
        profile.setProperty("pv", 1);
        profile.setProperty("level", 1);
        profile.setProperty("salary", lon);
        profile.setProperty("deaths", 0);
        profile.setProperty("kills", 0);
        profile.setProperty("exp", 0);
        profile.setProperty("achievements", 0);
        profile.setProperty("shardrate", 1);
        profile.setProperty("vagtposter", 0);

        System.out.println("name" + profile.getName());
        System.out.println("UUID" + profile.getUuid());
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
