package com.gmail.markushygedombrowski.levels;

import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import com.gmail.markushygedombrowski.playerProfiles.PlayerProfiles;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class LevelUpListener implements Listener {
    private PlayerProfiles playerProfiles;
    private LevelRewards levelRewards;
    public LevelUpListener(PlayerProfiles playerProfiles, LevelRewards levelRewards) {
        this.playerProfiles = playerProfiles;
        this.levelRewards = levelRewards;
    }

    @EventHandler
    public void onLevelUp(LevelUpEvent event) {
        PlayerProfile playerProfile = event.getPlayerProfile();
        if((int)playerProfile.getProperty("level") >= playerProfile.getXpToNextLvl()) {
            levelUp(event.getPlayer(), playerProfile);
        }

    }
    private void levelUp(Player p, PlayerProfile profile) {
        double xpToNextLvl = profile.getXpToNextLvl();
        profile.setProperty("level", (double) profile.getProperty("level") + 1);
        profile.setProperty("exp", (double) profile.getProperty("exp") - xpToNextLvl);
        p.sendMessage("§6§l--------§a§lLevel Up!§6§l--------");
        p.sendMessage("Tillykke du er nu level §b" + profile.getProperty("level") + "§3!");
        p.sendMessage("Du skal bruge §b" + profile.getXpToNextLvl() + " §3exp til næste level");
        p.sendMessage("§6§l--------§a§lLevel Up!§6§l--------");
        levelRewards.giveReward(p, (int)profile.getProperty("level"), profile);
        try {
            playerProfiles.save(profile);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
