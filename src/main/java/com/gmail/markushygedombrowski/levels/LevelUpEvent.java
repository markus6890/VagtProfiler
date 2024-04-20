package com.gmail.markushygedombrowski.levels;


import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class LevelUpEvent extends Event implements Cancellable {
    private boolean isCancelled;
    private Player player;
    private int level;
    private static final HandlerList handlers = new HandlerList();
    private PlayerProfile playerProfile;
    private int xp;

    public LevelUpEvent(boolean isCancelled, Player player, int level, PlayerProfile playerProfile1, int xp) {
        this.isCancelled = isCancelled;
        this.player = player;
        this.level = level;
        this.playerProfile = playerProfile1;
        this.xp = xp;
    }
    public int getXp() {
        return xp;
    }
    public Player getPlayer() {
        return player;
    }
    public int getLevel() {
        return level;
    }

    public PlayerProfile getPlayerProfile() {
        return playerProfile;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
