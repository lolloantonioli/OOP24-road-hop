package it.unibo.model.map.util;

/**
 * Represents the type of a chunk in the game map.
 */
public final class ChunkType {

    /**
     * Type representing a road chunk.
     */
    public static final ChunkType ROAD = new ChunkType("ROAD");
    /**
     * Type representing a railway chunk.
     */
    public static final ChunkType RAILWAY = new ChunkType("RAILWAY");
    /**
     * Type representing a river chunk.
     */
    public static final ChunkType RIVER = new ChunkType("RIVER");
    /**
     * Type representing a grass chunk.
     */
    public static final ChunkType GRASS = new ChunkType("GRASS");

    private final String name;

    private ChunkType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
