package it.unibo.view.Obstacles.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;
import it.unibo.view.Obstacles.api.MovingObstacleView;
import it.unibo.view.ScaleManager;

public class MovingObstacleViewImpl implements MovingObstacleView{

    @Override
    public void renderObstacles(Graphics g, List<MovingObstacles> obstacles, int viewportOffset) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renderObstacles'");
    }

    @Override
    public void updateScaleManager(ScaleManager newScaleManager) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateScaleManager'");
    }

    @Override
    public Rectangle getObstacleScreenBounds(MovingObstacles obstacle, int viewportOffset) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getObstacleScreenBounds'");
    }

    @Override
    public void setObstacleColor(ObstacleType type, Color color) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setObstacleColor'");
    }

}
