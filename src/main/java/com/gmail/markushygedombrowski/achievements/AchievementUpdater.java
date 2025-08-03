package com.gmail.markushygedombrowski.achievements;

import com.gmail.markushygedombrowski.deliveredItems.DeliveredItems;
import com.gmail.markushygedombrowski.deliveredItems.PLayerDeliveredItems;
import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AchievementUpdater {

    private final SimpleAchievementManager achievementManager;
    public AchievementUpdater(SimpleAchievementManager achievementManager) {
        this.achievementManager = achievementManager;
    }

    public void updateAchievements(PlayerProfile profile, DeliveredItems deliveredItems) {
        for (SimpleAchievement achievement : achievementManager.getAllAchievements().values()) {
            if(profile.hasCompletedAchievement(achievement.getId())) {
                continue;
            }
            if (achievement.isCompleted(deliveredItems, profile) && !profile.hasCompletedAchievement(achievement.getId())) {
                Player player = Bukkit.getPlayer(profile.getUuid());
                CompletedAchievementEvent event = new CompletedAchievementEvent(achievement, profile, false,player);
                Bukkit.getPluginManager().callEvent(event);
                profile.addCompletedAchievement(achievement);
                achievementManager.getSimpleAchievementSql().saveCompletedAchievement(profile.getUuid().toString(), achievement.getId());
            }
        }
    }
}
