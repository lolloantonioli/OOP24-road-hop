package it.unibo.model.Player.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import it.unibo.model.Collision.api.CollisionHandler;
import it.unibo.model.Collision.impl.CollisionHandlerImpl;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Map.impl.GameObjectImpl;
import it.unibo.model.Player.api.Player;
import it.unibo.model.Player.util.Direction;
import it.unibo.model.Shop.api.Skin;

//da fare un po' di metodi e gestione dell'invincibilità

public class PlayerImpl extends GameObjectImpl implements Player{

    private int score;
    private int collectedCoins;
    private boolean isAlive;
    private boolean isInvincible;
    private Cell currentCell;
    private Skin currentSkin;
    private GameMap map;
    private CollisionHandler collisionHandler;

    //keep track of the starting coordinates for the reset
    private final int initialX;
    private final int initialY;

    public PlayerImpl (final int x, final int y, final Skin skin, final GameMap map){
        super(x, y);
        checkNotNull(skin, "skin cannot be null");

        this.initialX = x;
        this.initialY = y;
        this.score = 0;
        this.isAlive = true;
        this.isInvincible = false;
        this.currentSkin = skin;
        this.map = map;
        collisionHandler = new CollisionHandlerImpl();
        this.currentCell = new CellImpl(x, y);

        setMovable(true);
    }

    @Override
    public boolean move(Direction direction) {
        Cell newpos = new CellImpl(currentCell.getX() + direction.getDeltaX(), currentCell.getY() + direction.getDeltaY()); 
        if (collisionHandler.canMoveTo(map, newpos)) {
            currentCell = newpos;
            if (currentCell.getY() > score) {
                score = currentCell.getY();
            }
            if (collisionHandler.happenedCollision(newpos, map)) {
                if (collisionHandler.isFatalCollisions(newpos, map) && !isInvincible) {
                    die();
                }
                if (collisionHandler.isCollectibleCollision(newpos, map)) {
                    //bisogna collectare le cose
                    //metodo di player o di collisionhandler?
                }
            }
            
            return true;
        }
        
        return false;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public Optional<Cell> getCurrentCell() {
        return Optional.of(this.currentCell);
    }

    @Override
    public boolean isAlive() {
        return this.isAlive && !collisionHandler.isOutOfBounds(currentCell, map);
     }

    @Override
    public void die() {
        isAlive = false;
    }

    @Override
    public void reset() {
        this.currentCell = new CellImpl(initialX, initialY);
        this.score = 0;
        this.collectedCoins = 0;
        this.isAlive = true;
    }

    @Override
    public int getCollectedCoins() {
        return this.collectedCoins;
    }

    @Override
    public Skin getCurrentSkin() {
        return this.currentSkin;
    }

    @Override
    public void setSkin(Skin skin) {
        checkNotNull(skin, "skin cannot be null");
        currentSkin = skin;
    }

}
