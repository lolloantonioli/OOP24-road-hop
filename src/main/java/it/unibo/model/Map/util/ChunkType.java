package it.unibo.model.Map.util;

/**
 * Represents the type of a chunk in the game map.
 */
public final class ChunkType {

    // CHECKSTYLE: CostantNameCheck OFF
    // Chunk type names are esplictly
    public static final ChunkType ROAD = new ChunkType("ROAD");
    public static final ChunkType RAILWAY = new ChunkType("RAILWAY");
    public static final ChunkType RIVER = new ChunkType("RIVER");
    public static final ChunkType GRASS = new ChunkType("GRASS");
    // CHECKSTYLE: CostantNameCheck ON

    private final String name;

    private ChunkType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
