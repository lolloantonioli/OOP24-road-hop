package it.unibo.model.Map.api;

public interface ChunkFactory {

    Chunk createRandomChunk(final int position);

    Chunk createGrassChunk(final int position);

    Chunk createRoadChunk(final int position);

    Chunk createRailwayChunk(final int position);

    Chunk createRiverChunk(final int position);

}
