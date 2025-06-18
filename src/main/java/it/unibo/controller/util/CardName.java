package it.unibo.controller.util;

/**
 * Represents the names of different .
 * Each card name is a constant that can be used to identify specific game states or screens.
 */
public final class CardName {

    /**
     * Card name which represents the main menu.
     */
    public static final CardName MENU = new CardName("MENU");
    /**
     * Card name which represents the game screen.
     */
    public static final CardName GAME = new CardName("GAME");
    /**
     * Card name which represents the instructions screen.
     */
    public static final CardName INSTRUCTIONS = new CardName("INSTRUCTIONS");
    /**
     * Card name which represents the shop screen.
     */
    public static final CardName SHOP = new CardName("SHOP");
    /**
     * Card name which represents the pause screen.
     */
    public static final CardName PAUSE = new CardName("PAUSE");
    /**
     * Card name which represents the game over screen.
     */
    public static final CardName GAME_OVER = new CardName("GAME_OVER");

    private final String name;

    private CardName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
