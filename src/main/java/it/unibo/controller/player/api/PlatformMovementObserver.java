package it.unibo.controller.player.api;

/**
 * Observer interface for detecting when a platform moves.
 */
public interface PlatformMovementObserver {

    /**
     * Moves the player along with a moving platform.
     * @param deltaX the change in the platform's position along the X-axis
     */
    void moveWithPlatform(int deltaX);
}
