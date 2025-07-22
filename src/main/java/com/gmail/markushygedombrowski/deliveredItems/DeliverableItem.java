package com.gmail.markushygedombrowski.deliveredItems;

import org.bukkit.Material;
import java.util.Arrays;
import java.util.Optional;

public enum DeliverableItem {
    SHARDS(Material.PRISMARINE_SHARD),
    BREAD(Material.BREAD),
    IRON_HELMET(Material.IRON_HELMET),
    IRON_CHESTPLATE(Material.IRON_CHESTPLATE),
    IRON_LEGGINGS(Material.IRON_LEGGINGS),
    IRON_BOOTS(Material.IRON_BOOTS),
    IRON_SWORD(Material.IRON_SWORD),
    DIAMOND_HELMET(Material.DIAMOND_HELMET),
    DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE),
    DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS),
    DIAMOND_BOOTS(Material.DIAMOND_BOOTS),
    DIAMOND_SWORD(Material.DIAMOND_SWORD),
    HEADS(Material.SKULL_ITEM),
    BLAZE_RODS(Material.BLAZE_ROD);

    private final Material material;

    DeliverableItem(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public static Optional<DeliverableItem> fromMaterial(Material material) {
        return Arrays.stream(values())
                .filter(item -> item.getMaterial() == material)
                .findFirst();
    }

    public static boolean isGear(DeliverableItem item) {
        return item.name().contains("HELMET") ||
                item.name().contains("CHESTPLATE") ||
                item.name().contains("LEGGINGS") ||
                item.name().contains("BOOTS") ||
                item.name().contains("SWORD");
    }

    public static boolean isIronGear(DeliverableItem item) {
        return isGear(item) && item.name().startsWith("IRON_");
    }

    public static boolean isDiamondGear(DeliverableItem item) {
        return isGear(item) && item.name().startsWith("DIAMOND_");
    }
}