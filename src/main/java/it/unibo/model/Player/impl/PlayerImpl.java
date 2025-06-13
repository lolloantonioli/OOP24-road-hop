package it.unibo.model.Player.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Map.impl.GameObjectImpl;
import it.unibo.model.Player.api.MovementValidator;
import it.unibo.model.Player.api.Player;
import it.unibo.model.Player.util.Direction;
import it.unibo.model.Shop.api.Skin;


public class PlayerImpl extends GameObjectImpl implements Player{

    private int score;
    private int collectedCoins;
    private boolean isOutOfBounds;
    private boolean isAlive;
    private boolean hasSecondLife;
    private boolean isInvincible;
    private Skin currentSkin;

    //keep track of the starting coordinates for the reset
    private final int initialX;
    private final int initialY;

    public PlayerImpl (final int x, final int y, final Skin skin){
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

    private void updateScore() {
        if (super.getY() > score) {
            score = super.getY();
        }
    }

    public void move(Cell newPos) {
        super.setX(newPos.getX());
        super.setY(newPos.getY());
        this.updateScore();
    }

    @Override
    public boolean tryMove(Direction direction, GameMap map, MovementValidator movementValidator) {
        Cell newPos = new CellImpl(super.getX() + direction.getDeltaX(), super.getY() + direction.getDeltaY());
        if(movementValidator.canMoveTo(map, newPos)) {
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
        /*if (isOutOfBounds) {
            isAlive = false;
            return;
        }*/
        
        if (isInvincible) {
            return; // Non può morire se invincibile
        }
        
        if (hasSecondLife) {
            hasSecondLife = false;
            isInvincible = true;
            //quando viene usata la seconda vita il player diventa invincibile fino a quando non si sarà mosso
        } else {
            isAlive = false;
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
    public void setSkin(Skin skin) {
        checkNotNull(skin, "skin cannot be null");
        currentSkin = skin;
    }

    public void grantSecondLife() {
        hasSecondLife = true;
    }

    public boolean hasSecondLife() {
        return hasSecondLife;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    @Override
    public void setOutOfBounds(boolean isOutOfBounds) {
        this.isOutOfBounds = isOutOfBounds;
    }

    @Override
    public boolean isOutOfBounds() {
        return isOutOfBounds;
    }

}
