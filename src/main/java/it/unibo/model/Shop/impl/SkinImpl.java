package it.unibo.model.Shop.impl;

import java.awt.image.BufferedImage;

import it.unibo.model.Shop.api.Skin;

public class SkinImpl implements Skin{
    private final String id;
    private final String name;
    private final int price;
    private final BufferedImage image;
    private boolean unlocked;

    public SkinImpl(String id, String name, int price, BufferedImage image, boolean unlocked) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.unlocked = unlocked;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
