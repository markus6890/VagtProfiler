package com.gmail.markushygedombrowski.achievements;

import com.gmail.markushygedombrowski.playerProfiles.PlayerProfiles;
import com.gmail.markushygedombrowski.settings.ConfigManager;
import com.gmail.markushygedombrowski.settings.DataProperty;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleAchievementManager {
    private HashMap<String, SimpleAchievement> achievements = new HashMap<>();
    private final ConfigManager configManager;
    private PlayerProfiles playerProfiles;
    private final SimpleAchievementSql simpleAchievementSql;
    private HashMap<String, Integer> achievementGroups = new HashMap<>();


    public SimpleAchievementManager(ConfigManager configManager, SimpleAchievementSql simpleAchievementSql) {
        this.configManager = configManager;

        this.simpleAchievementSql = simpleAchievementSql;
    }
    public void setPlayerProfiles(PlayerProfiles playerProfiles) {
        this.playerProfiles = playerProfiles;
    }

    public PlayerProfiles getPlayerProfiles() {
        return playerProfiles;
    }
    public void load() {
        achievements.clear();
        FileConfiguration config = configManager.getAchievementsCfg();
        for (String key : config.getConfigurationSection("achievements.simple").getKeys(false)) {
            String group = config.getString("achievements.simple." + key + ".group");
            String description = config.getString("achievements.simple." + key + ".description");
            int requirement = config.getInt("achievements.simple." + key + ".requirement");
            double modifier = config.getDouble("achievements.simple." + key + ".modifier");
            String type = config.getString("achievements.simple." + key + ".type");
            String dataProperty = config.getString("achievements.simple." + key + ".dataproperty");


            DataProperty property = DataProperty.valueOf(dataProperty);
            SimpleAchievement achievement = new SimpleAchievement(key, description, requirement, modifier, type, property,group);
            achievements.put(key, achievement);
            if (!achievementGroups.containsKey(group)) {
                achievementGroups.put(group, 1);
            } else {
                achievementGroups.put(group, achievementGroups.get(group) + 1);
            }

        }
        addCompletedAchievements();
    }

    public void save() {
        FileConfiguration config = configManager.getAchievementsCfg();
        config.set("achievements.simple", null);
        for (SimpleAchievement achievement : achievements.values()) {
            String path = "achievements.simple." + achievement.getId();

            config.set(path + ".group", achievement.getGroup());
            config.set(path + ".description", achievement.getDescription());
            config.set(path + ".requirement", achievement.getRequirement());
            config.set(path + ".modifier", achievement.getModifier());
            config.set(path + ".type", achievement.getType());
            config.set(path + ".dataproperty", achievement.getDataProperty().name());
        }
        configManager.saveAchievements();
    }

    public void addCompletedAchievements() {
        playerProfiles.getProfileMap().forEach( (playerUUID, profile) -> {
            for (SimpleAchievement achievement : achievements.values()) {
                if(simpleAchievementSql.hasCompletedAchievement(profile.getUuid().toString(), achievement.getId())) {
                    profile.addCompletedAchievement(achievement);
                }
            }
        });
    }



    public void addAchievement(SimpleAchievement achievement) {
        achievements.put(achievement.getId(), achievement);
    }

    public SimpleAchievement getAchievement(String id) {
        return achievements.get(id);
    }
    public HashMap<String, Integer> getAchievementGroups() {
        return achievementGroups;
    }

    public HashMap<String, SimpleAchievement> getAllAchievements() {
        return achievements;
    }
    public SimpleAchievementSql getSimpleAchievementSql() {
        return simpleAchievementSql;
    }
    public List<SimpleAchievement> getAchievementsByGroup(String group) {
        return achievements.values().stream()
                .filter(simpleAchievement -> simpleAchievement.getGroup().equalsIgnoreCase(group))
                .collect(Collectors.toList());
    }



}
