package it.unibo.model.Map.api;

/**
 * Interface for placing objects in a game map chunk.
 * This interface defines methods for placing static obstacles and collectibles in a chunk.
 */
public interface ObjectPlacer {

    /**
     * Places static obstacles in the specified chunk.
     *
     * @param chunk the chunk where obstacles will be placed.
     */
    void placeObstacles(Chunk chunk);

    /**
     * Places collectibles in the specified chunk.
     *
     * @param chunk the chunk where collectibles will be placed.
     */
    void placeCollectibles(Chunk chunk);

}
