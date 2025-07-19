package com.gmail.markushygedombrowski.deliveredItems;

import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import org.bukkit.Material;

import java.util.UUID;

public interface DeliveredItems {
    UUID getUUID();
    int getShards();
    int getBread();
    int getIronHelmet();
    int getIronChestplate();
    int getIronLeggings();
    int getIronBoots();
    int getIronSword();
    int getDiamondHelmet();
    int getDiamondChestplate();
    int getDiamondLeggings();
    int getDiamondBoots();
    int getDiamondSword();
    int getHeads();
    int getBlazeRods();
    int getTotalItems();
    int getTotalGear();
    int getTotalIronGear();
    int getTotalDiamondGear();

    void setShards(int shards);
    void setBread(int bread);
    void setIronHelmet(int ironHelmet);
    void setIronChestplate(int ironChestplate);
    void setIronLeggings(int ironLeggings);
    void setIronBoots(int ironBoots);
    void setIronSword(int ironSword);
    void setDiamondHelmet(int diamondHelmet);
    void setDiamondChestplate(int diamondChestplate);
    void setDiamondLeggings(int diamondLeggings);
    void setDiamondBoots(int diamondBoots);
    void setDiamondSword(int diamondSword);
    void setHeads(int heads);
    void setBlazeRods(int blazeRods);
    void setPlayerProfile(PlayerProfile playerProfile);
    void debug();
    void incrementItem(Material item, int amount);

    int get(Material material);

}
