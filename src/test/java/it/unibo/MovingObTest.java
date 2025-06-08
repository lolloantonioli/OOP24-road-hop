package it.unibo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.controller.Map.impl.MapControllerImpl;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.controller.Obstacles.impl.MovingObstacleControllerImpl;

class MovingObTest {

    private MovingObstacleController obstacleController;
    private MapControllerImpl mapController;

    @BeforeEach
    void setUp() {
        mapController = new MapControllerImpl(null);
        obstacleController = new MovingObstacleControllerImpl(mapController);
    }

    @Test
    void testObstaclesHaveValidCoordinates() {
        obstacleController.generateObstacles(2);
        var obstacles = obstacleController.getAllObstacles();

        // Ottieni le posizioni Y dei chunk visibili
        var visibleYs = mapController.getVisibleChunks().stream()
            .map(chunk -> chunk.getPosition())
            .toList();

        // Verifica che ogni ostacolo sia in una riga visibile
        boolean tuttiValidi = obstacles.stream()
            .allMatch(obs -> visibleYs.contains(obs.getY()));

        assertTrue(tuttiValidi, "Tutti gli ostacoli mobili devono essere in chunk visibili");
    }
}