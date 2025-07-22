package com.gmail.markushygedombrowski.deliveredItems;

import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import org.bukkit.Material;

import java.util.UUID;

public interface DeliveredItems {
    UUID getUUID();
    
    // Generic getters and setters
    int getAmount(DeliverableItem item);
    void setAmount(DeliverableItem item, int amount);
    void incrementAmount(DeliverableItem item, int amount);
    
    // Legacy getters - for backward compatibility
    default int getShards() { return getAmount(DeliverableItem.SHARDS); }
    default int getBread() { return getAmount(DeliverableItem.BREAD); }
    default int getIronHelmet() { return getAmount(DeliverableItem.IRON_HELMET); }
    default int getIronChestplate() { return getAmount(DeliverableItem.IRON_CHESTPLATE); }
    default int getIronLeggings() { return getAmount(DeliverableItem.IRON_LEGGINGS); }
    default int getIronBoots() { return getAmount(DeliverableItem.IRON_BOOTS); }
    default int getIronSword() { return getAmount(DeliverableItem.IRON_SWORD); }
    default int getDiamondHelmet() { return getAmount(DeliverableItem.DIAMOND_HELMET); }
    default int getDiamondChestplate() { return getAmount(DeliverableItem.DIAMOND_CHESTPLATE); }
    default int getDiamondLeggings() { return getAmount(DeliverableItem.DIAMOND_LEGGINGS); }
    default int getDiamondBoots() { return getAmount(DeliverableItem.DIAMOND_BOOTS); }
    default int getDiamondSword() { return getAmount(DeliverableItem.DIAMOND_SWORD); }
    default int getHeads() { return getAmount(DeliverableItem.HEADS); }
    default int getBlazeRods() { return getAmount(DeliverableItem.BLAZE_RODS); }
    
    // Legacy setters - for backward compatibility
    default void setShards(int amount) { setAmount(DeliverableItem.SHARDS, amount); }
    default void setBread(int amount) { setAmount(DeliverableItem.BREAD, amount); }
    default void setIronHelmet(int amount) { setAmount(DeliverableItem.IRON_HELMET, amount); }
    default void setIronChestplate(int amount) { setAmount(DeliverableItem.IRON_CHESTPLATE, amount); }
    default void setIronLeggings(int amount) { setAmount(DeliverableItem.IRON_LEGGINGS, amount); }
    default void setIronBoots(int amount) { setAmount(DeliverableItem.IRON_BOOTS, amount); }
    default void setIronSword(int amount) { setAmount(DeliverableItem.IRON_SWORD, amount); }
    default void setDiamondHelmet(int amount) { setAmount(DeliverableItem.DIAMOND_HELMET, amount); }
    default void setDiamondChestplate(int amount) { setAmount(DeliverableItem.DIAMOND_CHESTPLATE, amount); }
    default void setDiamondLeggings(int amount) { setAmount(DeliverableItem.DIAMOND_LEGGINGS, amount); }
    default void setDiamondBoots(int amount) { setAmount(DeliverableItem.DIAMOND_BOOTS, amount); }
    default void setDiamondSword(int amount) { setAmount(DeliverableItem.DIAMOND_SWORD, amount); }
    default void setHeads(int amount) { setAmount(DeliverableItem.HEADS, amount); }
    default void setBlazeRods(int amount) { setAmount(DeliverableItem.BLAZE_RODS, amount); }
    
    // Totals
    int getTotalItems();
    int getTotalGear();
    int getTotalIronGear();
    int getTotalDiamondGear();
    
    // Material-based operations
    default void incrementItem(Material material, int amount) {
        DeliverableItem.fromMaterial(material)
            .ifPresent(item -> incrementAmount(item, amount));
    }
    
    default int get(Material material) {
        return DeliverableItem.fromMaterial(material)
            .map(this::getAmount)
            .orElse(0);
    }
    
    // Profile management
    void setPlayerProfile(PlayerProfile playerProfile);
    void debug();
}