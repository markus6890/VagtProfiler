package com.gmail.markushygedombrowski.achievements;

import com.gmail.markushygedombrowski.sql.Sql;

import java.sql.*;

public class SimpleAchievementSql {
    private final Sql sql;

    public SimpleAchievementSql(Sql sql) {
        this.sql = sql;
    }

    public void createTableIfNotExist() {
        try (Connection connection = sql.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "CompletedAchievements", null);

            if (!tables.next()) {
                // Table does not exist
                String createTableQuery = "CREATE TABLE CompletedAchievements ("
                        + "UUID VARCHAR(36),"
                        + "AchievementID VARCHAR(255)"
                        + ");";

                try (Statement statement = connection.createStatement()) {
                    statement.execute(createTableQuery);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveCompletedAchievement(String uuid, String achievementId) {
        System.out.println("SQL: Saving completed achievement: " + achievementId + " for UUID: " + uuid);
        String insertQuery = "INSERT INTO CompletedAchievements (UUID, AchievementID) VALUES (?, ?) ";

        try (Connection connection = sql.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, achievementId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean hasCompletedAchievement(String uuid, String achievementId) {
        String selectQuery = "SELECT * FROM CompletedAchievements WHERE UUID = ? AND AchievementID = ?";

        try (Connection connection = sql.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, achievementId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if a record exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
