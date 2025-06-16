package it.unibo.model.Shop.impl;

import it.unibo.model.Shop.api.Skin;
import java.awt.Color;

public class SkinImpl implements Skin{
    
    private final String id;
    private final String name;
    private final int price;
    private boolean unlocked;
    private boolean selected;
    private final Color color;

    public SkinImpl(String id, String name, int price, boolean unlocked, Color color) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unlocked = unlocked;
        this.selected = false;
        this.color = color;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public boolean isUnlocked() {
        return unlocked;
    }


    @Override
    public void unlock() {
       this.unlocked = true;
    }

    @Override
    public boolean isSelected() {
        return selected;    
    }

    @Override
    public void select() {
        this.selected = true;
    }

    @Override
    public void deselect() {
        this.selected = false;
    }
    
    @Override
    public Color getColor() {
        return color;
    }
}
