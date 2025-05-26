package it.unibo.model.Collision.impl;

import it.unibo.model.Collision.api.CollisionHandler;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Player.api.Player;

public class CollisionHandlerImpl implements CollisionHandler{

    @Override
    public boolean checkCollision(Player player, GameObject obj) {
        if (obj == null || player == null) {
            return false;
        }

        //se non lavoriamo con le celle fare algoritmo aabb con width e height degli oggetti e player
        return player.getCurrentCell().get().equals(new CellImpl(obj.getX(), obj.getY()));
    }

    @Override
    public boolean isFatalCollisions(Player player, GameMap map) {
        if (player == null || map == null) {
            return false;
        }

        // Controlla collisioni fatali con tutti gli ostacoli nei chunk visibili
        for (Chunk chunk : map.getVisibleChunks()) {
            for (GameObject obj : chunk.getObjects()) {
                if (obj instanceof Obstacle) {
                    Obstacle obstacle = (Obstacle) obj;
                    
                    if (checkCollision(player, obstacle) && 
                        !obstacle.getType().toString().equals("TREE") && 
                        !isCollectibleCollision(player, map)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean isCollectibleCollision(Player player, GameMap map) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isCollectibleCollision'");
    }

    @Override
    public boolean canMoveTo(Player player, GameMap map, int newX, int newY) {
        /**controllare se il player sta cercando di entrare in un albero o
         * se sta cercando di uscire lateralmente dalla mappa
         * se sta cercando di andare più avanti della parte visualizzata di mappa
        */ 
        throw new UnsupportedOperationException("Unimplemented method 'canMoveTo'");
    }

    @Override
    public boolean isOutOfBounds(Player player, GameMap map) {
        //controllare se il player è stato lasciato indietro dalla mappa
        throw new UnsupportedOperationException("Unimplemented method 'isOutOfBounds'");
    }

}
