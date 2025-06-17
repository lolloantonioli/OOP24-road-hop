package it.unibo.model.Shop.impl;

import it.unibo.model.Shop.api.Skin;
import java.awt.Color;

/**
 * Implementation of the Skin interface.
 * This class represents a skin in the shop with properties such as id, name, price, unlocked status, selected status, and color.
 */
public class SkinImpl implements Skin {

    private final String id;
    private final String name;
    private final int price;
    private boolean unlocked;
    private boolean selected;
    private final Color color;

    /**
     * Constructs a new SkinImpl with the specified properties.
     *
     * @param id the unique identifier of the skin
     * @param name the name of the skin
     * @param price the price of the skin
     * @param unlocked whether the skin is unlocked
     * @param color the color associated with the skin
     */
    public SkinImpl(final String id, final String name, final int price, final boolean unlocked, final Color color) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unlocked = unlocked;
        this.selected = false;
        this.color = color;
    }

    @Override
    public final String getId() {
        return id;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final int getPrice() {
        return price;
    }

    @Override
    public final boolean isUnlocked() {
        return unlocked;
    }


    @Override
    public final void unlock() {
       this.unlocked = true;
    }

    @Override
    public final boolean isSelected() {
        return selected;
    }

    @Override
    public final void select() {
        this.selected = true;
    }

    @Override
    public final void deselect() {
        this.selected = false;
    }

    @Override
    public final Color getColor() {
        return color;
    }
}
