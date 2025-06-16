package it.unibo.controller.Player.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.controller.Player.api.PlatformMovementObserver;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Player.api.Player;

/**
 * Implementation of the PlatformMovementObserver interface.
 * This class defines how the player should move when a platform moves.
 */
public final class PlatformMovementObserverImpl implements PlatformMovementObserver {

    private final Player player;

    /**
     * Constructor for PlatformMovementObserverImpl.
     * @param player the player to move with the platform
     */
    public PlatformMovementObserverImpl(final Player player) {
        checkNotNull(player, "player cannot be null");
        this.player = player;
    }

    @Override
    public void moveWithPlatform(final int deltaX) {
        player.move(new CellImpl(player.getX() + deltaX, player.getY()));
    }

}
