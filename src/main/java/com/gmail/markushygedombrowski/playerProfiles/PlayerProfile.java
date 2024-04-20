package com.gmail.markushygedombrowski.playerProfiles;


import com.gmail.markushygedombrowski.levels.LevelUpEvent;
import com.gmail.markushygedombrowski.deliveredItems.DeliveredItems;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfile {

    private final double x = 0.1;
    private final double y = 2;
    private final UUID uuid;
    private final String name;
    private DeliveredItems deliveredItems;

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

    public void setXp(double xp) {
        setProperty("exp", xp);
        if ((double) getProperty("exp") >= getXpToNextLvl()) {
            LevelUpEvent event = new LevelUpEvent(false, Bukkit.getPlayer(uuid), ((Double) getProperty("level")).intValue(), this, (int) xp);
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
}
