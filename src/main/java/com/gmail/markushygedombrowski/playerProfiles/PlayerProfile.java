package com.gmail.markushygedombrowski.playerProfiles;


import com.gmail.markushygedombrowski.achievements.SimpleAchievement;
import com.gmail.markushygedombrowski.levels.LevelUpEvent;
import com.gmail.markushygedombrowski.deliveredItems.DeliveredItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfile {

    private final double x = 0.1;
    private final double y = 2;
    private final UUID uuid;
    private final String name;
    private DeliveredItems deliveredItems;
    private HashMap<String, SimpleAchievement> completedAchievements = new HashMap<>();

    private Map<String, Object> properties;


    public PlayerProfile(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.properties = new HashMap<>();
    }


    public DeliveredItems getDeliveredItems() {
        return deliveredItems;
    }

    public void setDeliveredItems(DeliveredItems deliveredItems) {
        this.deliveredItems = deliveredItems;
    }
    public void addExp(double xp) {
        if (getProperty("exp") == null) {
            setProperty("exp", 0.0);
        }

        double xpMultiplier = (Double) getProperty("expmultiplier");
        double addedXp = xpMultiplier * xp;
        double currentXp = castPropertyToInt(getProperty("exp"));
        setXp(currentXp + addedXp);
    }
    public void setXp(double xp) {
        setProperty("exp", xp);
        if (castPropertyToInt(getProperty("exp"))>= getXpToNextLvl()) {
            int level = castPropertyToInt(getProperty("level"));

            LevelUpEvent event = new LevelUpEvent(false, Bukkit.getPlayer(uuid), level, this, (int) xp);
            Bukkit.getPluginManager().callEvent(event);
        }

    }

    public double getXpToNextLvl() {
        if(getProperty("level") instanceof Double)
            return Math.pow(((Double)getProperty("level") / x), y);

        return Math.pow(((int)getProperty("level") / x), y);
    }


    // get players info
    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public double getExpSpecificLevel(int level) {
        return (int) Math.pow((level / x), y);
    }

    public void sendMessage(Player player, String message) {
        if(player.hasPermission("sended")) {
            return;
        }else {

        }
    }
    public boolean hasProperty(String key) {
        return properties.containsKey(key);
    }

    public void removeProperty(String key) {
        properties.remove(key);
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }
    public int castPropertyToInt(Object key) {
        if (key instanceof Double) {
            return  (((Double) key).intValue());
        }
        return (int) key;
    }
    public HashMap<String, SimpleAchievement> getCompletedAchievements() {
        return completedAchievements;
    }
    public void addCompletedAchievement(SimpleAchievement achievement) {
        completedAchievements.put(achievement.getId(), achievement);
    }
    public boolean hasCompletedAchievement(String achievementId) {
        return completedAchievements.containsKey(achievementId);
    }
}
