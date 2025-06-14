package it.unibo.controller.Player.impl;

import it.unibo.controller.Player.api.PlatformMovementObserver;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Player.api.Player;

public class PlatformMovementObserverImpl implements PlatformMovementObserver{

    private final Player player;

    public PlatformMovementObserverImpl(Player player) {
        this.player = player;
    }

    @Override
    public void moveWithPlatform(int deltaX) {
        System.out.println(deltaX);
        player.move(new CellImpl(player.getX() + deltaX, player.getY()));
    }

}
