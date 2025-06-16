package it.unibo.controller.Util;

/**
 * Represents the names of different .
 * Each card name is a constant that can be used to identify specific game states or screens.
 */
public final class CardName {

    // CHECKSTYLE: CostantNameCheck OFF
    // Card names are esplictly
    public static final CardName MENU = new CardName("MENU");
    public static final CardName GAME = new CardName("GAME");
    public static final CardName INSTRUCTIONS = new CardName("INSTRUCTIONS");
    public static final CardName SHOP = new CardName("SHOP");
    public static final CardName PAUSE = new CardName("PAUSE");
    public static final CardName GAME_OVER = new CardName("GAME_OVER");
    // CHECKSTYLE: CostantNameCheck ON

    private final String name;

    private CardName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
