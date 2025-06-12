package it.unibo.controller;

public class OnGameState implements GameState {

    @Override
    public void update(GameEngine context) {
        context.doGameUpdate();
    }

    @Override
    public void render(GameEngine context) {
        context.doGameRender();
    }

    @Override
    public String getName() {
        return "ON_GAME";
    }

}
