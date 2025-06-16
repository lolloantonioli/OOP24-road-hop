package it.unibo.controller.Util;

/**
 * Represents the names of different game states.
 */
public final class StateName {

    // CHECKSTYLE: CostantNameCheck OFF
    // State names are esplictly
    public static final StateName ON_GAME = new StateName("ON_GAME");
    public static final StateName PAUSE = new StateName("PAUSE");
    // CHECKSTYLE: CostantNameCheck ON

    private final String name;

    private StateName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
