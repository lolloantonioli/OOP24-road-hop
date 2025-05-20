package it.unibo.model.Map.util;

public final class ChunkType {
    
    public static final ChunkType ROAD = new ChunkType("ROAD");
    public static final ChunkType RAILWAY = new ChunkType("RAILWAY");
    public static final ChunkType RIVER = new ChunkType("RIVER");
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
