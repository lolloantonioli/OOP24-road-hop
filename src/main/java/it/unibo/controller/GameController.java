package it.unibo.controller;

import it.unibo.controller.Map.api.MapFormatter;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.model.Map.api.GameMap;

public interface GameController {

    void updateMap();

    void updateObstacles();

    GameMap getGameMap();

    MovingObstacleController getObstacleController();

    MapFormatter getMapFormatter();

    int getMapWidth();

    int getMapHeight();

}
