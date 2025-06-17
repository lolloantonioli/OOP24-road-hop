package it.unibo.model.Player.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Player.api.MovementValidator;

/**
 * Implementation of the MovementValidator interface.
 * This class provides methods to validate player movements in the game,
 * checking if a player can move to a specified position and if the position is out of bounds.
 */
public final class MovementValidatorImpl implements MovementValidator {

    @Override
    public boolean canMoveTo(final GameMap map, final Cell newPosition) {
        checkNotNull(newPosition, "not valid position");
        checkNotNull(map, "not valid map");

        return map.getVisibleChunks().stream()
            .flatMap(chunk -> chunk.getObjects().stream())
            .filter(obj -> obj.getOccupiedCells2().contains(newPosition))
            .filter(Obstacle.class::isInstance)
            .map(Obstacle.class::cast)
            .noneMatch(obstacle -> obstacle.getType().equals(ObstacleType.TREE))
            && !isOutOfBounds(newPosition, map);
    }

    @Override
    public boolean isOutOfBounds(final Cell position, final GameMap map) {
        checkNotNull(position, "not valid position");
        checkNotNull(map, "not valid map");

        return !map.getVisibleChunks().stream()
            .anyMatch(c -> c.getCells().getFirst().getY() == position.getY()
                            && position.getX() >= 0
                            && position.getX() < c.getCells().size());
    }

}
