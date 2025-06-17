package it.unibo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import it.unibo.model.map.util.ObstacleType;
import it.unibo.model.obstacles.impl.MovingObstacleFactoryImpl;
import it.unibo.model.obstacles.impl.MovingObstacleManagerImpl;
import it.unibo.model.obstacles.impl.MovingObstacles;

class MovingObstaclesTest {

    private static final int CELLS = MovingObstacles.CELLS;
    private static final int CAR_WIDTH = 1; 
    private static final int TRAIN_WIDTH = 4; 
    private static final int LOG_WIDTH = 3; 

    @Test
    void testCarCreation() {
        final int x = 2, y = 5, speed = 3;
        final MovingObstacles car = new MovingObstacles(x, y, ObstacleType.CAR, speed);
        assertEquals(x, car.getX());
        assertEquals(y, car.getY());
        assertEquals(ObstacleType.CAR, car.getType());
        assertEquals(CAR_WIDTH, car.getWidthInCells());
        assertTrue(car.isVisible());
    }

    @Test
    void testTrainCreation() {
        final int x = 0, y = 1, speed = -2;
        final MovingObstacles train = new MovingObstacles(x, y, ObstacleType.TRAIN, speed);
        assertEquals(x, train.getX());
        assertEquals(y, train.getY());
        assertEquals(ObstacleType.TRAIN, train.getType());
        assertEquals(TRAIN_WIDTH, train.getWidthInCells());
        assertTrue(train.isVisible());
    }

    @Test
    void testLogCreation() {
        final int x = 5, y = 6, speed = 2;
        final MovingObstacles log = new MovingObstacles(x, y, ObstacleType.LOG, speed);
        assertEquals(x, log.getX());
        assertEquals(y, log.getY());
        assertEquals(ObstacleType.LOG, log.getType());
        assertEquals(LOG_WIDTH, log.getWidthInCells());
        assertTrue(log.isVisible());
    }

    @Test
    void testObstacleMovementAndVisibility() {
        final MovingObstacles car = new MovingObstacles(CELLS - 1, 0, ObstacleType.CAR, 50);
        assertTrue(car.isVisible());
        car.update();
        assertFalse(car.isVisible());
    }

    @Test
    void testObstacleMovementAndVisibilityGradual() {
        final MovingObstacles car = new MovingObstacles(0, 0, ObstacleType.CAR, 49);
        assertTrue(car.isVisible());
        for (int i = 0; i < CELLS; i++) {
            car.update(); 
            if (i < CELLS - 1) {
                assertTrue(car.isVisible(), "Dovrebbe essere visibile alla posizione " + car.getX());
            }
        }
        assertFalse(car.isVisible());
    }

    @Test
    void testIncreaseSpeed() {
        final int initialSpeed = 2, increment = 3;
        final MovingObstacles log = new MovingObstacles(0, 0, ObstacleType.LOG, initialSpeed);
        log.increaseSpeed(increment);
        assertEquals(initialSpeed + increment, log.getSpeed());
    }

    @Test
    void testUpdateAllAndCleanup() {
        final MovingObstacleManagerImpl manager = new MovingObstacleManagerImpl();
        final MovingObstacles car = new MovingObstacles(CELLS - 1, 0, ObstacleType.CAR, 1);
        manager.addObstacle(car);
        final int baseThreshold = 50;
        final int movementThreshold = Math.max(1, baseThreshold - Math.abs(car.getSpeed()));
        for (int i = 0; i < movementThreshold; i++) {
            manager.updateAll();
        }
        manager.cleanupOffscreenObstacles();
        assertEquals(0, manager.getObstacleCount());
    }

    @Test
    void testGetObstaclesByType() {
        final MovingObstacleManagerImpl manager = new MovingObstacleManagerImpl();
        manager.addObstacle(new MovingObstacles(0, 0, ObstacleType.CAR, 1));
        manager.addObstacle(new MovingObstacles(0, 1, ObstacleType.TRAIN, 1));
        final List<MovingObstacles> cars = manager.getObstaclesByType("CAR");
        assertEquals(1, cars.size());
        assertEquals(ObstacleType.CAR, cars.get(0).getType());
    }

    @Test
    void testCreateCar() {
        final int x = 1, y = 2, speed = 3;
        final MovingObstacleFactoryImpl factory = new MovingObstacleFactoryImpl();
        final MovingObstacles car = factory.createCar(x, y, speed);
        assertEquals(ObstacleType.CAR, car.getType());
        assertEquals(x, car.getX());
        assertEquals(y, car.getY());
        assertEquals(speed, car.getSpeed());
    }

    @Test
    void testCreateTrain() {
        final int x = 0, y = 1, speed = -2;
        final MovingObstacleFactoryImpl factory = new MovingObstacleFactoryImpl();
        final MovingObstacles train = factory.createTrain(x, y, speed);
        assertEquals(ObstacleType.TRAIN, train.getType());
        assertEquals(x, train.getX());
        assertEquals(y, train.getY());
        assertEquals(speed, train.getSpeed());
    }

    @Test
    void testCreateLog() {
        final int x = 5, y = 6, speed = 2;
        final MovingObstacleFactoryImpl factory = new MovingObstacleFactoryImpl();
        final MovingObstacles log = factory.createLog(x, y, speed);
        assertEquals(ObstacleType.LOG, log.getType());
        assertEquals(x, log.getX());
        assertEquals(y, log.getY());
        assertEquals(speed, log.getSpeed());
    }

    @Test
    void testCreateObstacleByType() {
        final int x = 5, y = 6, speed = 2;
        final MovingObstacleFactoryImpl factory = new MovingObstacleFactoryImpl();
        final MovingObstacles log = factory.createObstacleByType(ObstacleType.LOG, x, y, speed);
        assertEquals(ObstacleType.LOG, log.getType());
        assertEquals(x, log.getX());
        assertEquals(y, log.getY());
        assertEquals(speed, log.getSpeed());
    }

}
