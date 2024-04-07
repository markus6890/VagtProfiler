package com.gmail.markushygedombrowski.deliveredItems;



import com.gmail.markushygedombrowski.sql.Sql;

import java.sql.*;
import java.util.UUID;

public class DeliveredItemsLoader {

    private final Sql sql;

    public DeliveredItemsLoader(Sql sql) {
        this.sql = sql;
        createTableIfNotExist();
    }

    public DeliveredItems loadDeliveredItems(UUID uuid) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = sql.getConnection();
            statement = connection.prepareStatement("SELECT * FROM DeliveredItems WHERE uuid = ?");
            sql.createDeliveredItemsTableIfNotExists();
            statement.setString(1, uuid.toString());


            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createDeliveredItemsFromResultSet(resultSet);
            } else {
                return createNewDeliveredItems(connection, uuid);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sql.closeAllSQL(connection, statement, resultSet);
        }
        return null;
    }
    public void createTableIfNotExist() {
        try (Connection connection = sql.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "DeliveredItems", null);

            if (!tables.next()) {
                // Table does not exist
                String createTableQuery = "CREATE TABLE DeliveredItems ("
                        + "uuid VARCHAR(36) PRIMARY KEY,"
                        + "seed INT,"
                        + "bread INT,"
                        + "ironHelmet INT,"
                        + "ironChestplate INT,"
                        + "ironLeggings INT,"
                        + "ironBoots INT,"
                        + "ironSword INT,"
                        + "diamondHelmet INT,"
                        + "diamondChestplate INT,"
                        + "diamondLeggings INT,"
                        + "diamondBoots INT,"
                        + "diamondSword INT,"
                        + "heads INT,"
                        + "blazeRods INT"
                        + ");";

                try (Statement statement = connection.createStatement()) {
                    statement.execute(createTableQuery);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DeliveredItems createDeliveredItemsFromResultSet(ResultSet resultSet) throws SQLException {
        return new PLayerDeliveredItems(
                UUID.fromString(resultSet.getString("uuid")),
                resultSet.getInt("seed"),
                resultSet.getInt("bread"),
                resultSet.getInt("ironHelmet"),
                resultSet.getInt("ironChestplate"),
                resultSet.getInt("ironLeggings"),
                resultSet.getInt("ironBoots"),
                resultSet.getInt("ironSword"),
                resultSet.getInt("diamondHelmet"),
                resultSet.getInt("diamondChestplate"),
                resultSet.getInt("diamondLeggings"),
                resultSet.getInt("diamondBoots"),
                resultSet.getInt("diamondSword"),
                resultSet.getInt("heads"),
                resultSet.getInt("blazeRods"));
    }

    private DeliveredItems createNewDeliveredItems(Connection connection, UUID uuid) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO DeliveredItems (uuid, seed, bread, ironHelmet, ironChestplate, ironLeggings, ironBoots, ironSword, diamondHelmet, diamondChestplate, diamondLeggings, diamondBoots, diamondSword, heads, blazeRods) VALUES (?, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)")) {
            statement.setString(1, uuid.toString());
            statement.executeUpdate();
        }
        return new PLayerDeliveredItems(uuid, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public void saveDeliveredItems(DeliveredItems deliveredItems) {
        String updateQuery = "UPDATE DeliveredItems SET seed = ?, bread = ?, ironHelmet = ?, ironChestplate = ?, ironLeggings = ?, ironBoots = ?, ironSword = ?, diamondHelmet = ?, diamondChestplate = ?, diamondLeggings = ?, diamondBoots = ?, diamondSword = ?, heads = ?, blazeRods = ? WHERE uuid = ?";

        try (Connection connection = sql.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setInt(1, deliveredItems.getShards());
            statement.setInt(2, deliveredItems.getBread());
            statement.setInt(3, deliveredItems.getIronHelmet());
            statement.setInt(4, deliveredItems.getIronChestplate());
            statement.setInt(5, deliveredItems.getIronLeggings());
            statement.setInt(6, deliveredItems.getIronBoots());
            statement.setInt(7, deliveredItems.getIronSword());
            statement.setInt(8, deliveredItems.getDiamondHelmet());
            statement.setInt(9, deliveredItems.getDiamondChestplate());
            statement.setInt(10, deliveredItems.getDiamondLeggings());
            statement.setInt(11, deliveredItems.getDiamondBoots());
            statement.setInt(12, deliveredItems.getDiamondSword());
            statement.setInt(13, deliveredItems.getHeads());
            statement.setInt(14, deliveredItems.getBlazeRods());
            statement.setString(15, deliveredItems.getUUID().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}