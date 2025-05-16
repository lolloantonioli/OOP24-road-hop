package it.unibo.controller.Obstacles.impl;

import java.util.List;

import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;

public class MovingObstacleControllerImpl implements MovingObstacleController{

    @Override
    public void startObstacleGeneration() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startObstacleGeneration'");
    }

    @Override
    public void stopObstacleGeneration() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stopObstacleGeneration'");
    }

    @Override
    public MovingObstacles createObstacle(ObstacleType type, int x, int y, int speed) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createObstacle'");
    }

    @Override
    public MovingObstacles[] createCarSet(int y, int count, boolean leftToRight) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCarSet'");
    }

    @Override
    public MovingObstacles[] createTrainSet(int y, int count, boolean leftToRight) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTrainSet'");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void increaseDifficulty(int factor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'increaseDifficulty'");
    }

    @Override
    public boolean checkCollision(int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkCollision'");
    }

    @Override
    public List<MovingObstacles> getObstaclesByType(ObstacleType type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getObstaclesByType'");
    }

}
