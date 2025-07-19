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
            System.out.println("Checking achievement: " + achievement.getId() + " for player: " + profile.getName());
            if(profile.hasCompletedAchievement(achievement.getId())) {
                System.out.println("Achievement already completed: " + achievement.getId() + " for player: " + profile.getName());
                continue;
            }
            if (achievement.isCompleted(deliveredItems, profile) && !profile.hasCompletedAchievement(achievement.getId())) {
                System.out.println("Achievement completed: " + achievement.getId() + " for player: " + profile.getName());
                profile.addCompletedAchievement(achievement);
                achievementManager.getSimpleAchievementSql().saveCompletedAchievement(profile.getUuid().toString(), achievement.getId());
            }
        }
    }
}
