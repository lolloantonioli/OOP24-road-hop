package it.unibo.controller.Util;

public class StateName {

    public static final StateName ON_GAME = new StateName("ON_GAME");
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
