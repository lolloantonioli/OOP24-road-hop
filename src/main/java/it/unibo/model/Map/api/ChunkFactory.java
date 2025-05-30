package it.unibo.model.Map.api;

public interface ChunkFactory {

    Chunk createRandomChunk(final int position);

    Chunk createGrassChunk(final int position);

    Chunk createFirstChunk(final int position);

}
