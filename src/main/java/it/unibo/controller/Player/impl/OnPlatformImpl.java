package it.unibo.controller.Player.impl;

import java.util.Optional;

import it.unibo.controller.Player.api.OnPlatform;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Player.util.Pair;

public class OnPlatformImpl implements OnPlatform{

    private Optional<GameObject> currentPlatform;
    private Cell previousPosition;

    public OnPlatformImpl(GameObject platform) {
        this.currentPlatform = Optional.of(platform);
        UpdatePreviousPosition();
    }

    public OnPlatformImpl(){
        this(null);
    }

    @Override
    public boolean isOnPlatform() {
        return currentPlatform.isPresent();
    }

    @Override
    public void setCurrentPlatform(Optional<GameObject> platform) {
        currentPlatform = platform;
        UpdatePreviousPosition();
        
    }

    @Override
    public Pair<Integer, Integer> hasMoved() {
        Pair<Integer, Integer> movement;
        if(isOnPlatform()) {
            movement = new Pair(currentPlatform.get().getX()-previousPosition.getX(), currentPlatform.get().getY()-previousPosition.getY());
        }
        else {
            movement = new Pair(0,0);
        }
        UpdatePreviousPosition();
        return movement;
    }

    private void UpdatePreviousPosition() {
        if(currentPlatform.isPresent()) {
            previousPosition = new CellImpl(currentPlatform.get().getX(), currentPlatform.get().getY());
        }
        else {
            previousPosition = null;
        }
    }
}
