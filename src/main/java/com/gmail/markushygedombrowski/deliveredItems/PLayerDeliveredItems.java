package com.gmail.markushygedombrowski.deliveredItems;

import org.bukkit.Material;

import java.util.UUID;

public class PLayerDeliveredItems implements DeliveredItems {
    private UUID uuid;
    private int shards;
    private int bread;
    private int ironHelmet;
    private int ironChestplate;
    private int ironLeggings;
    private int ironBoots;
    private int ironSword;
    private int diamondHelmet;
    private int diamondChestplate;
    private int diamondLeggings;
    private int diamondBoots;
    private int diamondSword;
    private int heads;
    private int blazeRods;



    public PLayerDeliveredItems(UUID uuid, int seed, int bread, int ironHelmet, int ironChestplate, int ironLeggings, int ironBoots, int ironSword, int diamondHelmet, int diamondChestplate, int diamondLeggings, int diamondBoots, int diamondSword, int heads, int blazeRods) {
        this.uuid = uuid;
        this.shards = seed;
        this.bread = bread;
        this.ironHelmet = ironHelmet;
        this.ironChestplate = ironChestplate;
        this.ironLeggings = ironLeggings;
        this.ironBoots = ironBoots;
        this.ironSword = ironSword;
        this.diamondHelmet = diamondHelmet;
        this.diamondChestplate = diamondChestplate;
        this.diamondLeggings = diamondLeggings;
        this.diamondBoots = diamondBoots;
        this.diamondSword = diamondSword;
        this.heads = heads;
        this.blazeRods = blazeRods;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getShards() {
        return shards;
    }

    @Override
    public int getBread() {
        return bread;
    }

    @Override
    public int getIronHelmet() {
        return ironHelmet;
    }

    @Override
    public int getIronChestplate() {
        return ironChestplate;
    }

    @Override
    public int getIronLeggings() {
        return ironLeggings;
    }

    @Override
    public int getIronBoots() {
        return ironBoots;
    }

    @Override
    public int getIronSword() {
        return ironSword;
    }

    @Override
    public int getDiamondHelmet() {
        return diamondHelmet;
    }

    @Override
    public int getDiamondChestplate() {
        return diamondChestplate;
    }

    @Override
    public int getDiamondLeggings() {
        return diamondLeggings;
    }

    @Override
    public int getDiamondBoots() {
        return diamondBoots;
    }

    @Override
    public int getDiamondSword() {
        return diamondSword;
    }

    @Override
    public int getHeads() {
        return heads;
    }

    @Override
    public int getBlazeRods() {
        return blazeRods;
    }

    @Override
    public void setShards(int shards) {
        this.shards = shards;
    }

    @Override
    public void setBread(int bread) {
        this.bread = bread;
    }

    @Override
    public void setIronHelmet(int ironHelmet) {
        this.ironHelmet = ironHelmet;
    }

    @Override
    public void setIronChestplate(int ironChestplate) {
        this.ironChestplate = ironChestplate;
    }

    @Override
    public void setIronLeggings(int ironLeggings) {
        this.ironLeggings = ironLeggings;
    }

    @Override
    public void setIronBoots(int ironBoots) {
        this.ironBoots = ironBoots;
    }

    @Override
    public void setIronSword(int ironSword) {
        this.ironSword = ironSword;
    }

    @Override
    public void setDiamondHelmet(int diamondHelmet) {
        this.diamondHelmet = diamondHelmet;
    }

    @Override
    public void setDiamondChestplate(int diamondChestplate) {
        this.diamondChestplate = diamondChestplate;
    }

    @Override
    public void setDiamondLeggings(int diamondLeggings) {
        this.diamondLeggings = diamondLeggings;
    }

    @Override
    public void setDiamondBoots(int diamondBoots) {
        this.diamondBoots = diamondBoots;
    }

    @Override
    public void setDiamondSword(int diamondSword) {
        this.diamondSword = diamondSword;
    }

    @Override
    public void setHeads(int heads) {
        this.heads = heads;
    }

    @Override
    public void setBlazeRods(int blazeRods) {
        this.blazeRods = blazeRods;
    }

    public int getTotalItems() {
        return shards + bread + ironHelmet + ironChestplate + ironLeggings + ironBoots + ironSword +
               diamondHelmet + diamondChestplate + diamondLeggings + diamondBoots + diamondSword +
               heads + blazeRods;
    }
    public int getTotalGear() {
        return ironHelmet + ironChestplate + ironLeggings + ironBoots + ironSword +
               diamondHelmet + diamondChestplate + diamondLeggings + diamondBoots + diamondSword;
    }
    public int getTotalIronGear() {
        return ironHelmet + ironChestplate + ironLeggings + ironBoots + ironSword;
    }
    public int getTotalDiamondGear() {
        return diamondHelmet + diamondChestplate + diamondLeggings + diamondBoots + diamondSword;
    }


    @Override
    public void debug() {
        System.out.println("uuid: " + uuid);
        System.out.println("seed: " + shards);
        System.out.println("bread: " + bread);
        System.out.println("ironHelmet: " + ironHelmet);
        System.out.println("ironChestplate: " + ironChestplate);
        System.out.println("ironLeggings: " + ironLeggings);
        System.out.println("ironBoots: " + ironBoots);
        System.out.println("ironSword: " + ironSword);
        System.out.println("diamondHelmet: " + diamondHelmet);
        System.out.println("diamondChestplate: " + diamondChestplate);
        System.out.println("diamondLeggings: " + diamondLeggings);
        System.out.println("diamondBoots: " + diamondBoots);
        System.out.println("diamondSword: " + diamondSword);
        System.out.println("heads: " + heads);
    }

    @Override
    public void incrementItem(Material item, int amount) {
        switch (item) {
            case PRISMARINE_SHARD:
                shards += amount;
                break;
            case BREAD:
                bread += amount;
                break;
            case IRON_HELMET:
                ironHelmet+= amount;
                break;
            case IRON_CHESTPLATE:
                ironChestplate+= amount;
                break;
            case IRON_LEGGINGS:
                ironLeggings+= amount;
                break;
            case IRON_BOOTS:
                ironBoots+= amount;
                break;
            case IRON_SWORD:
                ironSword+= amount;
                break;
            case DIAMOND_HELMET:
                diamondHelmet+= amount;
                break;
            case DIAMOND_CHESTPLATE:
                diamondChestplate+= amount;
                break;
            case DIAMOND_LEGGINGS:
                diamondLeggings+= amount;
                break;
            case DIAMOND_BOOTS:
                diamondBoots+= amount;
                break;
            case DIAMOND_SWORD:
                diamondSword+= amount;
                break;
        }
    }

    @Override
    public int get(Material material) {
        switch (material) {
            case PRISMARINE_CRYSTALS:
                return shards;
            case BREAD:
                return bread;
            case IRON_HELMET:
                return ironHelmet;
            case IRON_CHESTPLATE:
                return ironChestplate;
            case IRON_LEGGINGS:
                return ironLeggings;
            case IRON_BOOTS:
                return ironBoots;
            case IRON_SWORD:
                return ironSword;
            case DIAMOND_HELMET:
                return diamondHelmet;
            case DIAMOND_CHESTPLATE:
                return diamondChestplate;
            case DIAMOND_LEGGINGS:
                return diamondLeggings;
            case DIAMOND_BOOTS:
                return diamondBoots;
            case DIAMOND_SWORD:
                return diamondSword;
        }
        return 0;
    }
}
