package it.unibo.controller;

public interface GameState {

    void update(GameEngine context);

    void render(GameEngine context);

    String getName();

}
