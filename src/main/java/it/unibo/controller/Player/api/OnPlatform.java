package it.unibo.controller.Player.api;

import java.util.Optional;

import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Player.util.Pair;

public interface OnPlatform {

    boolean isOnPlatform();

    void setCurrentPlatform(Optional<GameObject> platform);

    Pair hasMoved();

}
