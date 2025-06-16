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

    public OnPlatformImpl(Optional<GameObject> platform) {
        this.currentPlatform = platform;
        updatePreviousPosition();
    }

    public OnPlatformImpl(){
        this(Optional.empty());
    }

    @Override
    public boolean isOnPlatform() {
        return currentPlatform.isPresent();
    }

    @Override
    public void setCurrentPlatform(Optional<GameObject> platform) {
        currentPlatform = platform;
        updatePreviousPosition();
        
    }

    @Override
    public Pair hasMoved() {
        Pair movement;
        if(isOnPlatform()) {
            movement = new Pair(currentPlatform.get().getX()-previousPosition.getX(), currentPlatform.get().getY()-previousPosition.getY());
        }
        else {
            movement = new Pair(0,0);
        }
        updatePreviousPosition();
        return movement;
    }

    private void updatePreviousPosition() {
        if(currentPlatform.isPresent()) {
            previousPosition = new CellImpl(currentPlatform.get().getX(), currentPlatform.get().getY());
        }
        else {
            previousPosition = null;
        }
    }
}
