package com.gmail.markushygedombrowski.deliveredItems;

import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import org.bukkit.Material;
import java.util.*;

public class PLayerDeliveredItems implements DeliveredItems {
    private final UUID uuid;
    private final Map<DeliverableItem, Integer> items;
    private PlayerProfile profile;

    public PLayerDeliveredItems(UUID uuid, int shards, int bread, int ironHelmet, int ironChestplate,
            int ironLeggings, int ironBoots, int ironSword, int diamondHelmet, int diamondChestplate, 
            int diamondLeggings, int diamondBoots, int diamondSword, int heads, int blazeRods) {
        this.uuid = uuid;
        this.items = new EnumMap<>(DeliverableItem.class);
        
        // Initialize with provided values
        items.put(DeliverableItem.SHARDS, shards);
        items.put(DeliverableItem.BREAD, bread);
        items.put(DeliverableItem.IRON_HELMET, ironHelmet);
        items.put(DeliverableItem.IRON_CHESTPLATE, ironChestplate);
        items.put(DeliverableItem.IRON_LEGGINGS, ironLeggings);
        items.put(DeliverableItem.IRON_BOOTS, ironBoots);
        items.put(DeliverableItem.IRON_SWORD, ironSword);
        items.put(DeliverableItem.DIAMOND_HELMET, diamondHelmet);
        items.put(DeliverableItem.DIAMOND_CHESTPLATE, diamondChestplate);
        items.put(DeliverableItem.DIAMOND_LEGGINGS, diamondLeggings);
        items.put(DeliverableItem.DIAMOND_BOOTS, diamondBoots);
        items.put(DeliverableItem.DIAMOND_SWORD, diamondSword);
        items.put(DeliverableItem.HEADS, heads);
        items.put(DeliverableItem.BLAZE_RODS, blazeRods);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getAmount(DeliverableItem item) {
        return items.getOrDefault(item, 0);
    }

    @Override
    public void setAmount(DeliverableItem item, int amount) {
        items.put(item, amount);
    }

    @Override
    public void incrementAmount(DeliverableItem item, int amount) {
        items.merge(item, amount, Integer::sum);
        updateAchievements();
    }

    private void updateAchievements() {
        Optional.ofNullable(profile).ifPresent(p -> {
            System.out.println("Updating achievements for player: " + p.getName());
            p.updateAchievements();
        });
    }

    @Override
    public void setPlayerProfile(PlayerProfile playerProfile) {
        this.profile = playerProfile;
    }

    @Override
    public void debug() {
        System.out.println("UUID: " + uuid);
        items.forEach((item, amount) -> 
            System.out.println(item.name() + ": " + amount));
    }

    @Override
    public int getTotalItems() {
        return items.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public int getTotalGear() {
        return items.entrySet().stream()
                .filter(entry -> DeliverableItem.isGear(entry.getKey()))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    @Override
    public int getTotalIronGear() {
        return items.entrySet().stream()
                .filter(entry -> DeliverableItem.isIronGear(entry.getKey()))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    @Override
    public int getTotalDiamondGear() {
        return items.entrySet().stream()
                .filter(entry -> DeliverableItem.isDiamondGear(entry.getKey()))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }
}