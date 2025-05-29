package it.unibo.model.Player.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Map.impl.GameObjectImpl;
import it.unibo.model.Player.api.Player;
import it.unibo.model.Shop.api.Skin;

//da fare un po' di metodi e gestione dell'invincibilità

public class PlayerImpl extends GameObjectImpl implements Player{

    private int score;
    private int collectedCoins;
    private boolean isAlive;
    private boolean hasSecondLife;
    private Cell currentCell;
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
        this.isAlive = true;
        this.hasSecondLife = false;
        this.currentSkin = skin;
        this.currentCell = new CellImpl(x, y);
        setMovable(true);
    }

    @Override
    public void move(Cell newPos) {
        currentCell = newPos;
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
        return this.isAlive;
     }

    @Override
    public void die() {
        if (hasSecondLife) {
            hasSecondLife = false;
            // Il player "resuscita" e resta vivo
        } else {
            isAlive = false;
        }
    }

    @Override
    public void reset() {
        this.currentCell = new CellImpl(initialX, initialY);
        this.score = 0;
        this.collectedCoins = 0;
        this.isAlive = true;
        this.hasSecondLife = false;
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

    public void grantSecondLife() {
        this.hasSecondLife = true;
    }

    public boolean hasSecondLife() {
        return this.hasSecondLife;
    }

}
