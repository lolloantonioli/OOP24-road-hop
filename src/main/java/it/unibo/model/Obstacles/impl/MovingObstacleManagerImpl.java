package it.unibo.model.Obstacles.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.model.Obstacles.api.MovingObstacleManager;

// controlla reset in moving obstacles

/**
 * Handles the management of moving obstacles in the game.
 * This includes adding, removing, updating, and checking the safety of positions for obstacles.
 */
public final class MovingObstacleManagerImpl implements MovingObstacleManager {

    private final List<MovingObstacles> obstacles;

    /**
     * Constructs a new MovingObstacleManagerImpl with an empty list of obstacles.
     */
    public MovingObstacleManagerImpl() {
        this.obstacles = new ArrayList<>();
    }

    @Override
    public void addObstacle(final MovingObstacles obstacle) {
        if (obstacle == null) {
            return;
        }
        if (isSafePosition(obstacle.getX(), obstacle.getY(), obstacle.getWidthInCells())) {
            obstacles.add(obstacle);
        }
    }

    @Override
    public void addObstacles(final List<MovingObstacles> newObstacles) {
        for (final MovingObstacles obstacle : newObstacles) {
            addObstacle(obstacle);
        }
    }

    @Override
    public void removeObstacle(final MovingObstacles obstacle) {
        obstacles.remove(obstacle);
    }

    @Override
    public void updateAll() {
       obstacles.stream()
        .filter(MovingObstacles::isMovable)
        .forEach(MovingObstacles::update);
    }

    @Override
    public List<MovingObstacles> getActiveObstacles() {
        return new ArrayList<>(obstacles);
    }

    @Override
    public List<MovingObstacles> getObstaclesByType(final String typeStr) {
        final List<MovingObstacles> result = new ArrayList<>();
        if (typeStr == null) {
            return result;
        }
        return obstacles.stream()
                .filter(obstacle -> obstacle.getType().toString().equalsIgnoreCase(typeStr))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovingObstacles> getObstaclesInChunk(final int chunkY) {
        final List<MovingObstacles> result = new ArrayList<>();
        for (final MovingObstacles obstacle : obstacles) {
            if (obstacle.getY() == chunkY) {
                result.add(obstacle);
            }
        }
        return result;
    }

    @Override
    public void increaseSpeed(final int factor) {
        for (final MovingObstacles obstacle : obstacles) {
            obstacle.increaseSpeed(factor);
        }
    }

    @Override
    public void cleanupOffscreenObstacles() {
        final Iterator<MovingObstacles> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            final MovingObstacles obstacle = iterator.next();
            if (!obstacle.isVisible()) {
                iterator.remove();
            }
        }
    }

    @Override
    public int getObstacleCount() {
        return obstacles.size();
    }

    @Override
    public void resetAll() {
        for (final MovingObstacles obstacle : obstacles) {
            removeObstacle(obstacle);
        }
    }
    
    @Override
    public boolean isSafePosition(final int cellX, final int chunkY, final int widthInCells) {
        for (final MovingObstacles obstacle : obstacles) {
            if (obstacle.getY() != chunkY) {
                continue; 
            }
            // Controlla sovrapposizione tra aree
            final int obstacleStart = obstacle.getX();
            final int obstacleEnd = obstacleStart + obstacle.getWidthInCells();
            final int newObstacleEnd = cellX + widthInCells;
            if (cellX < obstacleEnd && newObstacleEnd > obstacleStart) {
                return false;
            }
        }
        return true;
    }
}
