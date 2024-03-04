package com.gmail.markushygedombrowski.deliveredItems;
public class ItemProfile {
    private int exp;
    private String name;

    public ItemProfile(int exp, String name) {
        this.exp = exp;
        this.name = name;
    }

    public int getExp() {
        return exp;
    }

    public String getName() {
        return name;
    }
}
