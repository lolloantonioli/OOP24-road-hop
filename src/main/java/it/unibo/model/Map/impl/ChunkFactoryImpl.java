package it.unibo.model.Map.impl;

import java.util.Random;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.ChunkFactory;
import it.unibo.model.Map.util.ChunkType;
import it.unibo.model.Map.util.CollectibleType;
import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.impl.ObstacleImpl;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.impl.CollectibleImpl;

public class ChunkFactoryImpl implements ChunkFactory {

    private final Random random;

    public ChunkFactoryImpl() {
        this.random = new Random();
    }

    @Override
    public Chunk createRandomChunk(final int position) {
        final int type = random.nextInt(4);
        return switch (type) {
            case 0 -> createRoadChunk(position);
            case 1 -> createRailwayChunk(position);
            case 2 -> createRiverChunk(position);
            default -> createGrassChunk(position);
        };
    }

    @Override
    public Chunk createGrassChunk(final int position) {
        final Chunk chunk = new ChunkImpl(position, ChunkType.GRASS);
        
        // Add 0-3 trees as obstacles
        final int treeCount = random.nextInt(4);
        
        for (int i = 0; i < treeCount; i++) {
            int treeX = random.nextInt(ChunkImpl.CELLS_PER_ROW);
            Obstacle tree = new ObstacleImpl(treeX, position, ObstacleType.TREE, false );
            chunk.addObject(tree);
        }
        
        // Higher chance for coins in grass (40%)
        if (random.nextInt(10) < 4) {
            Collectible coin = new CollectibleImpl(
                random.nextInt(ChunkImpl.CELLS_PER_ROW), 
                position, 
                CollectibleType.COIN
            );
            chunk.addObject(coin);
        }
        
        return chunk;
    }

    private Chunk createRoadChunk(int position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRoadChunk'");
    }

    private Chunk createRailwayChunk(int position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRailwayChunk'");
    }

    private Chunk createRiverChunk(int position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRiverChunk'");
    }

}
