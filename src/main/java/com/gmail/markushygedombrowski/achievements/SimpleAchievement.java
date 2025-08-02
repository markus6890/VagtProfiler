package com.gmail.markushygedombrowski.achievements;

import com.gmail.markushygedombrowski.deliveredItems.DeliveredItems;
import com.gmail.markushygedombrowski.deliveredItems.PLayerDeliveredItems;
import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import com.gmail.markushygedombrowski.settings.DataProperty;

public class SimpleAchievement {
    private final String id;
    private final String description;
    private final int requirement;
    private final double modifier;
    private final String type;
    private final DataProperty dataproperty;


    private final String group;

    public SimpleAchievement(String id, String description, int requirement, double modifier, String type, DataProperty dataproperty, String group) {
        this.id = id;
        this.description = description;
        this.requirement = requirement;
        this.modifier = modifier;
        this.type = type;
        this.dataproperty = dataproperty;
        this.group = group;
    }
    public boolean isCompleted(DeliveredItems deliveredItems, PlayerProfile playerProfile) {
        return dataproperty.getData(deliveredItems, playerProfile) >= requirement;
    }
    public String getType() {
        return type;
    }

    public double getModifier() {
        return modifier;
    }

    public int getRequirement() {
        return requirement;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
    public DataProperty getDataProperty() {
        return dataproperty;
    }
    public String getGroup() {
        return group;
    }
    public void debug() {
        System.out.println("Achievement ID: " + id);
        System.out.println("Description: " + description);
        System.out.println("Requirement: " + requirement);
        System.out.println("Modifier: " + modifier);
        System.out.println("Type: " + type);
        System.out.println("Data Property Value: " + dataproperty);
    }
}
