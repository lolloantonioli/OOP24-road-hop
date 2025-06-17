package it.unibo.controller.Util;

/**
 * Represents the names of different game states.
 */
public final class StateName {

    /**
     * STate name which represents the game state.
     */
    public static final StateName ON_GAME = new StateName("ON_GAME");
    /**
     * State name which represents the pause state.
     */
    public static final StateName PAUSE = new StateName("PAUSE");

    private final String name;

    private StateName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
