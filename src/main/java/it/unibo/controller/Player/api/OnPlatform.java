package it.unibo.controller.Player.api;

import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Player.util.Pair;

public interface OnPlatform {

    boolean isOnPlatform();

    void setCurrentPlatform(GameObject platform);

    Pair<Integer, Integer> hasMoved();

}
