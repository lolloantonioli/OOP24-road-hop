package it.unibo.controller.player.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.controller.player.api.PlatformMovementObserver;
import it.unibo.model.map.impl.CellImpl;
import it.unibo.model.player.api.Player;

/**
 * Implementation of the PlatformMovementObserver interface.
 * This class defines how the player should move when a platform moves.
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", 
                   justification = "Player reference is intentionally stored to implement Observer pattern")
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
