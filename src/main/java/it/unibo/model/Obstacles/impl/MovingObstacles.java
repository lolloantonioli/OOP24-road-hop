package it.unibo.model.Obstacles.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.controller.Player.api.PlatformMovementObserver;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.impl.GameObjectImpl;
import it.unibo.model.Map.util.ObstacleType;

public class MovingObstacles extends GameObjectImpl implements Obstacle{
    private final ObstacleType type;
    private boolean visible;
    private int updateCounter; // Per gestire movimento sub-cella
    private static final int BASE_MOVEMENT_THRESHOLD = 50; 
    private final List<PlatformMovementObserver> observers = new ArrayList<>();

    public static final int CAR_WIDTH_CELLS = 1;
    public static final int TRAIN_WIDTH_CELLS = 4;
    public static final int LOG_WIDTH_CELLS = 3;
    public static final int CELLS_PER_CHUNK = 9;

    public MovingObstacles(int cellX, int chunkY, ObstacleType type, int speed) {
        super(cellX, chunkY, getWidthForType(type));
        this.type = type;
        super.setSpeed(speed);
        super.setMovable(true);
        this.visible = true;
        this.updateCounter = 0;
    }

    private static int getWidthForType(ObstacleType type) {
        return switch (type.toString()) {
            case "TRAIN" -> TRAIN_WIDTH_CELLS;
            case "LOG" -> LOG_WIDTH_CELLS;
            default -> CAR_WIDTH_CELLS;
        };
    }
    
    @Override
    public ObstacleType getType() {
        return type;
    }

    @Override
    public void update() {
        if (!isMovable()) {
            return;
        }

        updateCounter++;
        
        // Movimento basato su velocità (ogni N update muove di una cella)
        int movementThreshold = Math.max(1,  BASE_MOVEMENT_THRESHOLD - Math.abs(getSpeed())); 
        
        if (updateCounter >= movementThreshold) {
            updateCounter = 0;

            int deltaX = 0;
            
            if (getSpeed() > 0) {
                setX(getX() + 1);
                if (getX() >= CELLS_PER_CHUNK) {
                    this.visible = false;
                }
                deltaX = 1;
            } else if (getSpeed() < 0) {
                setX(getX() - 1);;
                if (getX() + getWidthInCells() - 1 < 0) {
                    this.visible = false;
                }
                deltaX = -1;
            }

            notifyObservers(deltaX);
        }
    }
     
    public void reset() {
        this.updateCounter = 0;
    }
    
    /**
     * Aumenta la velocità dell'ostacolo mantenendo la direzione.
     * 
     * @param amount Quantità da aggiungere
     */
    public void increaseSpeed(int amount) {
        if (getSpeed() > 0) {
            setSpeed(getSpeed() + amount);
        } else if (getSpeed() < 0) {
            setSpeed(getSpeed() - amount);
        }
    }

    @Override
    public boolean isPlatform() {
        return type == ObstacleType.LOG;
    }

    @Override
    public void setPlatform(boolean platform) {
        // Solo i tronchi possono essere impostati come piattaforme
        if (platform && type != ObstacleType.LOG) {
            throw new UnsupportedOperationException("Solo gli ostacoli di tipo LOG possono essere impostati come piattaforme");
        }

    }

     /**
     * Controlla se l'ostacolo è visibile.
     * 
     * @return Vero se è visibile.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Setta la visibilità dell'ostacolo.
     * 
     * @param visible Vero per segnare l'ostacolo visibile
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void addObserver(PlatformMovementObserver obs) {
        if(isPlatform()) {
            observers.add(obs);
        }
    }

    public void removeObserver(PlatformMovementObserver obs) {
        observers.remove(obs);
    }

    private void notifyObservers(int deltaX) {
        observers.forEach(o -> o.moveWithPlatform(deltaX));
    }

}

