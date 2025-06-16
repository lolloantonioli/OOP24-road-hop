package it.unibo.model.Map.api;

/**
 * Factory interface for creating {@code Chunk} instances.
 * This interface defines methods to create chunks of different types, including random chunks,
 * grass chunks, and the firsts chunk of the game map.
 * Each chunk is associated with a specific x-coordinate position.
 */
public interface ChunkFactory {

    /**
     * Creates a chunk of a random type at the given position.
     *
     * @param position the x-coordinate of the chunk.
     * @return a new {@code Chunk} of a random type at the given position.
     */
    Chunk createRandomChunk(int position);

    /**
     * Creates a GRASS {@code Chunk} at the specified position.
     * 
     * @param position the x-coordinate of the chunk.
     * @return a new {@code Chunk} of the specified type at the given position.
     */
    Chunk createGrassChunk(int position);

    /**
     * Creates the first chunk of the game map at the specified position.
     * The first chunk is always a GRASS chunk without {@code Obstacle}s and {@code Collectible}s.
     * 
     * @param position the x-coordinate of the chunk.
     * @return the first {@code Chunk} of the game map at the specified position.
     */
    Chunk createFirstsChunk(int position);

}
