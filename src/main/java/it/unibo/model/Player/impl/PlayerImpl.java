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

//si potrebbe cambiare move rendendolo un metodo boolean e passando il CollisionProMax in input così diventa meno stupido
//la gestione dei tronchi totalmente da capire
//se sono ferma e poi vengo investita come lo capisco?

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
        this.isInvincible = true;
        this.currentSkin = skin;
        setMovable(true);
    }

    private void updateScore() {
        if (super.getY() > score) {
            score = super.getY();
        }
    }

    private void move(Cell newPos) {
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
        //se il player non è in una cella visibile muore a prescindere
        if (isOutOfBounds || !isInvincible) {
            isAlive = false;
        } else if (hasSecondLife) {
            hasSecondLife = false;
            isInvincible = true;
            // Il player "resuscita" e resta vivo, non può morire fino a quando non si muoverà
        }  
    }

    @Override
    public void reset() {
        super.setX(initialX);
        super.setY(initialY);
        score = 0;
        collectedCoins = 0;
        isAlive = true;
        hasSecondLife = false;
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
