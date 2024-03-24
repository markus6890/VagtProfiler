package com.gmail.markushygedombrowski.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ChangeInvOnWarp {
    private InvManager invManager;

    public ChangeInvOnWarp(InvManager invManager) {
        this.invManager = invManager;
    }

    public void createInventory(Player player, String block) {
        UUID uuid = player.getUniqueId();
        ItemStack[] inventory = player.getInventory().getContents().clone();
        ItemStack[] gear = player.getInventory().getArmorContents().clone();
        if (!invManager.playerInventories.containsKey(uuid)) invManager.playerInventories.put(uuid, new InvHolder(uuid));
        if (invManager.playerInventories.get(uuid).inventories.containsKey(block)) {
            return;
        }
        invManager.playerInventories.get(uuid).inventories.put(block, new InvHolder(uuid, inventory,gear, block));
    }



    public void changeInventory(Player player, String block) {
        InvHolder invHolder = getInventory(player.getUniqueId(), block);
        if (invHolder == null) {
            createInventory(player, block);
            return;
        }
        ItemStack[] inventory = player.getInventory().getContents().clone();
        invHolder.setInventory(inventory);
        invHolder.setGear(player.getInventory().getArmorContents().clone());

    }

    public void clearInventory(UUID playerName, String block) {
        InvHolder invHolder = getInventory(playerName, block);
        if (invHolder != null) {
            invHolder.setInventory(new ItemStack[36]);
            invHolder.setGear(new ItemStack[4]);
        }
    }

    public InvHolder getInventory(UUID playerName, String block) {
        if (!invManager.playerInventories.containsKey(playerName)) {
            return null;
        }
        if (!invManager.playerInventories.get(playerName).inventories.containsKey(block)) {
            return null;
        }
        return invManager.playerInventories.get(playerName).inventories.get(block);

    }



}
