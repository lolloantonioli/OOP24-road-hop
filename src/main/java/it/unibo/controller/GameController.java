package it.unibo.controller;

import it.unibo.controller.Map.api.MapFormatter;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.controller.Player.api.PlayerController;
import it.unibo.model.Map.api.GameMap;

public interface GameController {

    void updateMap();

    void updateObstacles();

    void updatePlayer();

    GameMap getGameMap();

    MovingObstacleController getObstacleController();

    PlayerController getPlayerController();

    MapFormatter getMapFormatter();

    int getMapWidth();

    int getMapHeight();

}
