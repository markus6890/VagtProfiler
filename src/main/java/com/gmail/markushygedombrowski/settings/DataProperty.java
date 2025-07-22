package com.gmail.markushygedombrowski.settings;

import com.gmail.markushygedombrowski.deliveredItems.DeliveredItems;
import com.gmail.markushygedombrowski.deliveredItems.PLayerDeliveredItems;
import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public enum DataProperty {
    BREAD_DELIVERED((items, profile) -> items.getBread()),
    SHARDS_DELIVERED((items, profile) -> items.getShards()),
    BLAZE_ROD_DELIVERED((items, profile) -> items.getBlazeRods()),
    IRON_BOOTS_DELIVERED((items, profile) -> items.getIronBoots()),
    IRON_HELMET_DELIVERED((items, profile) -> items.getIronHelmet()),
    IRON_CHESTPLATE_DELIVERED((items, profile) -> items.getIronChestplate()),
    IRON_LEGGINGS_DELIVERED((items, profile) -> items.getIronLeggings()),
    IRON_SWORD_DELIVERED((items, profile) -> items.getIronSword()),
    IRON_ARMOR_DELIVERED((items, profile) -> items.getTotalIronGear()),
    DIAMOND_SWORD_DELIVERED((items, profile) -> items.getDiamondSword()),
    DIAMOND_HELMET_DELIVERED((items, profile) -> items.getDiamondHelmet()),
    DIAMOND_CHESTPLATE_DELIVERED((items, profile) -> items.getDiamondChestplate()),
    DIAMOND_LEGGINGS_DELIVERED((items, profile) -> items.getDiamondLeggings()),
    DIAMOND_BOOTS_DELIVERED((items, profile) -> items.getDiamondBoots()),
    DIAMOND_ARMOR_DELIVERED((items, profile) -> items.getTotalDiamondGear()),
    TOTAL_ARMOR_DELIVERED((items, profile) -> items.getTotalGear()),
    TOTAL_ITEMS_DELIVERED((items, profile) -> items.getTotalItems()),
    PLAYER_KILLS((items, profile) -> profile.castPropertyToInt(profile.getProperty("kills"))),
    VAGT_POSTER((items,profile) -> profile.castPropertyToInt(profile.getProperty("vagtposter"))),
    PLAYER_DEATHS((items, profile) -> profile.castPropertyToInt(profile.getProperty("deaths")));

    private final BiFunction<DeliveredItems, PlayerProfile, Integer> dataRetriever;

    DataProperty(BiFunction<DeliveredItems, PlayerProfile, Integer> dataRetriever) {
        this.dataRetriever = dataRetriever;
    }

    public int getData(DeliveredItems playerDeliveredItems, PlayerProfile playerProfile) {
        return dataRetriever.apply(playerDeliveredItems, playerProfile);
    }
}
