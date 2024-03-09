package com.gmail.markushygedombrowski;

import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import com.gmail.markushygedombrowski.playerProfiles.PlayerProfiles;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class LevelUpListener implements Listener {
    private PlayerProfiles playerProfiles;

    public LevelUpListener(PlayerProfiles playerProfiles) {
        this.playerProfiles = playerProfiles;
    }

    @EventHandler
    public void onLevelUp(LevelUpEvent event) {
        PlayerProfile playerProfile = event.getPlayerProfile();
        if(playerProfile.getXp() >= playerProfile.getXpToNextLvl()) {
            levelUp(event.getPlayer(), playerProfile);
        }

    }
    private void levelUp(Player p, PlayerProfile profile) {

        p.sendMessage("§6§l--------§a§lLevel Up!§6§l--------");
        p.sendMessage("Tillykke du er nu level §b" + profile.getLvl());
        p.sendMessage("Du skal bruge §b" + profile.getXpToNextLvl() + " §3exp til næste level");
        p.sendMessage("§6§l--------§a§lLevel Up!§6§l--------");
        profile.setXp(profile.getXp() - profile.getXpToNextLvl());
        profile.setLvl(profile.getLvl() + 1);

        try {
            playerProfiles.save(profile);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
