package com.gmail.markushygedombrowski.buff;

import org.bukkit.entity.Player;

public class BuffLevels {
    private final int level;
    private final int speed;
    private final int strength;
    private final int absorption;
    private final int extraHearts;
    private final int maxHealth;
    private final int length = 200000000;

    public BuffLevels(int level, int speed, int strength, int absorption, int extraHearts, int maxHealth) {
        this.level = level;
        this.speed = speed;
        this.strength = strength;
        this.absorption = absorption;
        this.extraHearts = extraHearts;
        this.maxHealth = maxHealth;
    }

    public int getLevel() {
        return level;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStrength() {
        return strength;
    }

    public int getAbsorption() {
        return absorption;
    }
    public int getExtraHearts() {
        return extraHearts;
    }
    public int getMaxHealth() {
        return maxHealth;
    }

    public void giveBuff(Player p) {
        p.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SPEED, length,getSpeed()));
        p.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.INCREASE_DAMAGE, length,getStrength()));
        p.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.ABSORPTION, length, getAbsorption()));
        p.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.HEALTH_BOOST, length, getExtraHearts()));
        p.setMaxHealth(20 + getMaxHealth());

    }
}
