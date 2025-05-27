package it.unibo.controller.Util;

public final class CardName {

    public static final CardName MENU = new CardName("MENU");
    public static final CardName GAME = new CardName("GAME");
    public static final CardName SETTINGS = new CardName("SETTINGS");
    public static final CardName SHOP = new CardName("SHOP");

    private final String name;

    private CardName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
