package it.unibo.controller.Player.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.controller.Player.api.OnPlatform;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Player.util.Pair;

public class OnPlatformImpl implements OnPlatform{

    private GameObject currentPlatform;
    private Cell previousPosition;

    public OnPlatformImpl(GameObject platform) {
        this.currentPlatform = platform;
        this.previousPosition = new CellImpl(platform.getX(), platform.getY());
    }

    public OnPlatformImpl(){
        this(null);
    }

    @Override
    public boolean isOnPlatform() {
        return currentPlatform != null;
    }

    @Override
    public void setCurrentPlatform(GameObject platform) {
        checkNotNull(platform, "platform cannot be null");
        currentPlatform = platform;
        previousPosition = new CellImpl(platform.getX(), platform.getY());
    }

    @Override
    public Pair<Integer, Integer> hasMoved() {
        return new Pair(currentPlatform.getX()-previousPosition.getX(), currentPlatform.getY()-previousPosition.getY());
    }

}
