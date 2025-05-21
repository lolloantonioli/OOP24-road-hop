package it.unibo.model.Player.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Map.impl.GameObjectImpl;
import it.unibo.model.Player.api.Player;
import it.unibo.model.Player.util.Direction;
import it.unibo.model.Shop.api.Skin;

public class PlayerImpl extends GameObjectImpl implements Player{

    private static final int DEFAULT_STARTING_X = 0;
    private static final int DEFAULT_STARTING_Y = 0;

    private int score;
    private int collectedCoins;
    private boolean isAlive;
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
        this.currentSkin = skin;

        setMovable(true);
    }

    public PlayerImpl(Skin skin){
        this(DEFAULT_STARTING_X, DEFAULT_STARTING_Y, skin);
    }

    @Override
    public boolean move(Direction direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
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
