package it.unibo.model.player.impl;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.controller.player.api.DeathObserver;
import it.unibo.model.map.api.Cell;
import it.unibo.model.map.api.GameMap;
import it.unibo.model.map.impl.CellImpl;
import it.unibo.model.map.impl.GameObjectImpl;
import it.unibo.model.player.api.MovementValidator;
import it.unibo.model.player.api.Player;
import it.unibo.model.player.util.Direction;
import it.unibo.model.shop.api.Skin;

/**
 * Implementation of the Player interface.
 * Represents a player in the game with attributes such as score, collected coins,
 * and methods to move, die, reset, and manage skins.
 */
public final class PlayerImpl extends GameObjectImpl implements Player  {

    private int score;
    private int collectedCoins;
    private boolean isOutOfBounds;
    private boolean isAlive;
    private boolean hasSecondLife;
    private boolean isInvincible;
    private Skin currentSkin;
    private final List<DeathObserver> observers = new ArrayList<>();

    //keep track of the starting coordinates for the reset
    private final int initialX;
    private final int initialY;

    /**
     * Constructor for PlayerImpl.
     * @param x the initial x-coordinate of the player
     * @param y the initial y-coordinate of the player
     * @param skin the initial skin of the player
     */
    public PlayerImpl(final int x, final int y, final Skin skin) {
        super(x, y);
        checkNotNull(skin, "skin cannot be null");
        this.initialX = x;
        this.initialY = y;
        this.score = 0;
        this.isOutOfBounds = false;
        this.isAlive = true;
        this.hasSecondLife = false;
        //when the player spawns is invincible until he makes is first valid move
        this.isInvincible = true;
        this.currentSkin = skin;
        setMovable(true);
    }

    /**
     * Constructor for PlayerImpl that creates a player from an existing Player instance.
     * @param player the Player instance to copy attributes from
     */
    public PlayerImpl(final Player player) {
        super(player.getX(), player.getY());
        checkNotNull(player, "player cannot be null");
        this.initialX = player.getInitialX();
        this.initialY = player.getInitialY();
        this.score = player.getScore();
        this.collectedCoins = player.getCollectedCoins();
        this.isOutOfBounds = player.isOutOfBounds();
        this.isAlive = player.isAlive();
        this.hasSecondLife = player.hasSecondLife();
        this.isInvincible = player.isInvincible();
        this.currentSkin = player.getCurrentSkin();
        setMovable(true);
    }

    /**
     * Updates the player's score based on their current Y position.
     * The score is calculated as the difference between the current Y position
     * and the initial Y position when the player was created.
     */
    private void updateScore() {
        if (super.getY() > score + initialY) {
            score = super.getY() - initialY;
        }
    }

    /**
     * Forces the player to move to a new position and updates the score.
     * @param newPos the new position to move the player to
     */
    @Override
    public void move(final Cell newPos) {
        checkNotNull(newPos, "new position cannot be null");
        super.setX(newPos.getX());
        super.setY(newPos.getY());
        this.updateScore();
    }

    @Override
    public boolean tryMove(final Direction direction, final GameMap map, final MovementValidator movementValidator) {
        checkNotNull(direction, "direction cannot be null");
        checkNotNull(map, "map cannot be null");
        checkNotNull(movementValidator, "movementValidator cannot be null");
        final Cell newPos = new CellImpl(super.getX() + direction.getDeltaX(), super.getY() + direction.getDeltaY());
        if (movementValidator.canMoveTo(map, newPos)) {
            isInvincible = false;
            move(newPos);
            return true;
        }
        return false;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public Cell getCurrentCell() {
        return new CellImpl(super.getX(), super.getY());
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void die() {
        // if the player is out of bounds, he dies
        if (isOutOfBounds) {
            isAlive = false;
            notifyObservers();
            return;
        }

        if (isInvincible) {
            return;
        }

        if (hasSecondLife) {
            // if the player has a second life, he can use it and become invincible until he moves
            hasSecondLife = false;
            isInvincible = true;
        } else {
            isAlive = false;
            notifyObservers();
        }
    }

    @Override
    public void reset() {
        super.setX(initialX);
        super.setY(initialY);
        score = 0;
        collectedCoins = 0;
        isOutOfBounds = false;
        isAlive = true;
        hasSecondLife = false;
        isInvincible = true;
    }

    @Override
    public void collectACoin() {
         collectedCoins = collectedCoins + 1;
    }

    @Override
    public int getCollectedCoins() {
        return collectedCoins;
    }

    @Override
    public Skin getCurrentSkin() {
        return currentSkin;
    }

    @Override
    public void setSkin(final Skin skin) {
        checkNotNull(skin, "skin cannot be null");
        currentSkin = skin;
    }

    @Override
    public void grantSecondLife() {
        hasSecondLife = true;
    }

    @Override
    public boolean hasSecondLife() {
        return hasSecondLife;
    }

    @Override
    public boolean isInvincible() {
        return isInvincible;
    }

    @Override
    public void setOutOfBounds(final boolean isOutOfBounds) {
        this.isOutOfBounds = isOutOfBounds;
    }

    @Override
    public boolean isOutOfBounds() {
        return isOutOfBounds;
    }

    @Override
    public void addObserver(final DeathObserver observer) {
        checkNotNull(observer, "observer cannot be null");
        observers.add(observer);
    }

    @Override
    public void removeObserver(final DeathObserver observer) {
        checkNotNull(observer, "observer cannot be null");
        observers.remove(observer);
    }

    /**
     * Notifies all observers that the game has ended.
     */
    private void notifyObservers() {
        observers.forEach(DeathObserver::endGame);
    }

    @Override
    public int getInitialX() {
        return initialX;
    }

    @Override
    public int getInitialY() {
        return initialY;
    }

}
