package com.gmail.markushygedombrowski.achievements;

import com.gmail.markushygedombrowski.deliveredItems.DeliveredItems;
import com.gmail.markushygedombrowski.deliveredItems.PLayerDeliveredItems;
import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;

public class AchievementUpdater {

    private final SimpleAchievementManager achievementManager;

    public AchievementUpdater(SimpleAchievementManager achievementManager) {
        this.achievementManager = achievementManager;
    }

    public void updateAchievements(PlayerProfile profile, DeliveredItems deliveredItems) {
        for (SimpleAchievement achievement : achievementManager.getAllAchievements().values()) {
            if (achievement.isCompleted(deliveredItems, profile) && !profile.hasCompletedAchievement(achievement.getId())) {
                profile.addCompletedAchievement(achievement);
                achievementManager.getSimpleAchievementSql().saveCompletedAchievement(profile.getUuid().toString(), achievement.getId());
            }
        }
    }
}
