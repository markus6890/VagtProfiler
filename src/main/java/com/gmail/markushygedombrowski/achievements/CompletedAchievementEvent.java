package com.gmail.markushygedombrowski.achievements;

import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import com.gmail.markushygedombrowski.playerProfiles.PlayerProfiles;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class CompletedAchievementEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final SimpleAchievement achievement;
    private final PlayerProfile playerProfile;
    private boolean isCancelled;


    private Player player;
    public CompletedAchievementEvent(SimpleAchievement achievement, PlayerProfile playerProfile, boolean isCancelled, Player player) {
        this.achievement = achievement;
        this.playerProfile = playerProfile;
        this.isCancelled = isCancelled;
        this.player = player;
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
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {

    }
    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
