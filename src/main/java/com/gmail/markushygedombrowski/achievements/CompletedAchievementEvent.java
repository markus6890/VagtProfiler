package com.gmail.markushygedombrowski.achievements;

import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class CompletedAchievementEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final SimpleAchievement achievement;
    private final PlayerProfile playerProfile;

    public CompletedAchievementEvent(SimpleAchievement achievement, PlayerProfile playerProfile) {
        this.achievement = achievement;
        this.playerProfile = playerProfile;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
    public PlayerProfile getPlayerProfile() {
        return playerProfile;
    }

    public SimpleAchievement getAchievement() {
        return achievement;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
