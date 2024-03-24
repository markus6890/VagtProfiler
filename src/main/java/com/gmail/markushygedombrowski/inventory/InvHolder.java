package com.gmail.markushygedombrowski.inventory;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class InvHolder {
    private final UUID uuid;
    private ItemStack[] inventory;
    private String block;
    public HashMap<String, InvHolder> inventories = new HashMap<>();
    private ItemStack[] gear;

    public InvHolder(UUID uuid, ItemStack[] inventory, ItemStack[] gear, String block) {
        this.uuid = uuid;
        this.inventory = inventory;
        this.block = block;
        this.gear = gear;
    }
    public InvHolder(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }
    public ItemStack[] getGear() {
        return gear;
    }
    public void setGear(ItemStack[] gear) {
        this.gear = gear;
    }
    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }
    public String getBlock() {
        return block;
    }

}
