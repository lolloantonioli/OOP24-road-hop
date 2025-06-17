package it.unibo;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;
import it.unibo.model.Obstacles.impl.MovingObstacleManagerImpl;
import it.unibo.model.Obstacles.impl.MovingObstacleFactoryImpl;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MovingObstaclesTest {

    @Test
    void testCarCreation() {
        MovingObstacles car = new MovingObstacles(2, 5, ObstacleType.CAR, 3);
        assertEquals(2, car.getX());
        assertEquals(5, car.getY());
        assertEquals(ObstacleType.CAR, car.getType());
        assertEquals(1, car.getWidthInCells());
        assertTrue(car.isVisible());
    }

    @Test
    void testTrainCreation() {
        MovingObstacles train = new MovingObstacles(0, 1, ObstacleType.TRAIN, -2);
        assertEquals(0, train.getX());
        assertEquals(1, train.getY());
        assertEquals(ObstacleType.TRAIN, train.getType());
        assertEquals(4, train.getWidthInCells());
        assertTrue(train.isVisible());
    }

    @Test
    void testLogCreation() {
        MovingObstacles log = new MovingObstacles(5, 6, ObstacleType.LOG, 2);
        assertEquals(5, log.getX());
        assertEquals(6, log.getY());
        assertEquals(ObstacleType.LOG, log.getType());
        assertEquals(3, log.getWidthInCells());
        assertTrue(log.isVisible());
    }

    @Test
    void testObstacleMovementAndVisibility() {
        MovingObstacles car = new MovingObstacles(MovingObstacles.CELLS - 1, 0, ObstacleType.CAR, 50);
        assertTrue(car.isVisible());
        car.update();
        assertFalse(car.isVisible());
    }

    @Test
    void testObstacleMovementAndVisibilityGradual() {
        MovingObstacles car = new MovingObstacles(0, 0, ObstacleType.CAR, 49);
        assertTrue(car.isVisible());
        for (int i = 0; i < MovingObstacles.CELLS; i++) {
            car.update(); 
            if (i < MovingObstacles.CELLS - 1) {
                assertTrue(car.isVisible(), "Dovrebbe essere visibile alla posizione " + car.getX());
            }
        }
        assertFalse(car.isVisible());
    }

    @Test
    void testSetVisibility() {
        MovingObstacles car = new MovingObstacles(0, 0, ObstacleType.CAR, 1);
        
        assertTrue(car.isVisible());
        
        car.setVisible(false);
        assertFalse(car.isVisible());
        
        car.setVisible(true);
        assertTrue(car.isVisible());
    }

    @Test
    void testIncreaseSpeed() {
        MovingObstacles log = new MovingObstacles(0, 0, ObstacleType.LOG, 2);
        log.increaseSpeed(3);
        assertEquals(5, log.getSpeed());
    }

    @Test
    void testUpdateAllAndCleanup() {
        MovingObstacleManagerImpl manager = new MovingObstacleManagerImpl();
        MovingObstacles car = new MovingObstacles(MovingObstacles.CELLS - 1, 0, ObstacleType.CAR, 1);
        manager.addObstacle(car);
        for (int i = 0; i < 100; i++) {
            manager.updateAll();
        }
        manager.cleanupOffscreenObstacles();
        assertEquals(0, manager.getObstacleCount());
    }

    @Test
    void testGetObstaclesByType() {
        MovingObstacleManagerImpl manager = new MovingObstacleManagerImpl();
        manager.addObstacle(new MovingObstacles(0, 0, ObstacleType.CAR, 1));
        manager.addObstacle(new MovingObstacles(0, 1, ObstacleType.TRAIN, 1));
        List<MovingObstacles> cars = manager.getObstaclesByType("CAR");
        assertEquals(1, cars.size());
        assertEquals(ObstacleType.CAR, cars.get(0).getType());
    }

    @Test
    void testCreateCar() {
        MovingObstacleFactoryImpl factory = new MovingObstacleFactoryImpl();
        MovingObstacles car = factory.createCar(1, 2, 3);
        assertEquals(ObstacleType.CAR, car.getType());
        assertEquals(1, car.getX());
        assertEquals(2, car.getY());
        assertEquals(3, car.getSpeed());
    }

    @Test
    void testCreateTrain() {
        MovingObstacleFactoryImpl factory = new MovingObstacleFactoryImpl();
        MovingObstacles train = factory.createTrain(0, 1, -2);
        assertEquals(ObstacleType.TRAIN, train.getType());
        assertEquals(0, train.getX());
        assertEquals(1, train.getY());
        assertEquals(-2, train.getSpeed());
    }

    @Test
    void testCreateLog() {
        MovingObstacleFactoryImpl factory = new MovingObstacleFactoryImpl();
        MovingObstacles log = factory.createLog(5, 6, 2);
        assertEquals(ObstacleType.LOG, log.getType());
        assertEquals(5, log.getX());
        assertEquals(6, log.getY());
        assertEquals(2, log.getSpeed());
    }

    @Test
    void testCreateObstacleByType() {
        MovingObstacleFactoryImpl factory = new MovingObstacleFactoryImpl();
        MovingObstacles log = factory.createObstacleByType(ObstacleType.LOG, 5, 6, 2);
        assertEquals(ObstacleType.LOG, log.getType());
        assertEquals(5, log.getX());
        assertEquals(6, log.getY());
        assertEquals(2, log.getSpeed());
    }
}