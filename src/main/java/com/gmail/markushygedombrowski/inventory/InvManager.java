package com.gmail.markushygedombrowski.inventory;

import com.gmail.markushygedombrowski.sql.Sql;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class InvManager {
    private Sql sql;
    public final HashMap<UUID, InvHolder> playerInventories = new HashMap<>();
    public InvManager(Sql sql) {
        this.sql = sql;
        createTableIfNotExist();
    }

    public String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            // Serialize that array
            dataOutput.close();
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public void saveInventory(UUID uuid, String block, ItemStack[] inventory,ItemStack[] gear) throws SQLException {
        Connection connection = sql.getConnection();
        String inventoryData = itemStackArrayToBase64(inventory);
        String gearData = itemStackArrayToBase64(gear);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO inventories (uuid, block, inventory, gear) VALUES (?, ?, ?, ?)");
        statement.setString(1, uuid.toString());
        statement.setString(2, block);
        statement.setString(3, inventoryData);
        statement.setString(4, gearData);
        statement.executeUpdate();
        sql.closeAllSQL(connection, statement, null);
    }

    public void loadInventories() throws SQLException, IOException {
        playerInventories.clear();
        Connection connection = sql.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM inventories");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            UUID uuid = UUID.fromString(resultSet.getString("uuid"));
            String block = resultSet.getString("block");
            String inventoryData = resultSet.getString("inventory");
            String gearData = resultSet.getString("gear");
            ItemStack[] inventory = itemStackArrayFromBase64(inventoryData);
            ItemStack[] gear = itemStackArrayFromBase64(gearData);
            createInventory(uuid, block, inventory, gear);
            deleteInventory(uuid, block);
        }
        sql.closeAllSQL(connection, statement, resultSet);
    }

    public void deleteInventory(UUID uuid, String block) throws SQLException {
        Connection connection = sql.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM inventories WHERE uuid = ? AND block = ?");
            statement.setString(1, uuid.toString());
            statement.setString(2, block);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveAll() {
        playerInventories.forEach((uuid, invHolder) -> {
            invHolder.inventories.forEach((block, invHolder1) -> {
                try {
                    saveInventory(uuid, block, invHolder1.getInventory(), invHolder1.getGear());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        });

    }
    public void createInventory(UUID uuid, String block, ItemStack[] inventory, ItemStack[] gear) {
        if (!playerInventories.containsKey(uuid)) playerInventories.put(uuid, new InvHolder(uuid));
        if (playerInventories.get(uuid).inventories.containsKey(block)) {
            return;
        }
        playerInventories.get(uuid).inventories.put(block, new InvHolder(uuid, inventory,gear, block));
    }
    public void createTableIfNotExist() {
        try (Connection connection = sql.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "inventories", null);

            if (!tables.next()) {
                // Table does not exist
                String createTableQuery = "CREATE TABLE inventories ("
                        + "uuid VARCHAR(36),"
                        + "block VARCHAR(255),"
                        + "inventory TEXT,"
                        + "gear TEXT,"
                        + "PRIMARY KEY (uuid, block)"
                        + ");";

                try (Statement statement = connection.createStatement()) {
                    statement.execute(createTableQuery);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
