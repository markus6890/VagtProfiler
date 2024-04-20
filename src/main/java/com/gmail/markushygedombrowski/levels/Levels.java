package com.gmail.markushygedombrowski.levels;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Levels {
    private int level;
    private List<String> lore;
    private List<String> permissions;
    private List<String> regionsAccess;
    private boolean randomBlomst;
    private boolean randomGlass;

    private int buffLevel;
    private int shardsrate;
    private double expboost;
    private int moneyboost;

    public Levels(int level, List<String> lore, List<String> permissions, List<String> regionsAccess, boolean randomBlomst, boolean randomGlass, int buffLevel, int shardsrate, double expboost, int moneyboost) {
        this.level = level;
        this.lore = lore;
        this.permissions = permissions;
        this.regionsAccess = regionsAccess;
        this.randomBlomst = randomBlomst;
        this.randomGlass = randomGlass;

        this.buffLevel = buffLevel;
        this.shardsrate = shardsrate;
        this.expboost = expboost;
        this.moneyboost = moneyboost;
    }
    public boolean isRandomGlass() {
        return randomGlass;
    }
    public boolean isRandomBlomst() {
        return randomBlomst;
    }
    public int getLevel() {
        return level;
    }

    public List<String> getLore() {
        return lore;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public List<String> getRegionsAccess() {
        return regionsAccess;
    }

    public int getBuffLevel() {
        return buffLevel;
    }

    public int getShardsrate() {
        return shardsrate;
    }

    public double getExpboost() {
        return expboost;
    }

    public int getMoneyboost() {
        return moneyboost;
    }


}
