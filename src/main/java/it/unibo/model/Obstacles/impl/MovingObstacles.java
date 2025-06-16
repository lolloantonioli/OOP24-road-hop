package it.unibo.model.Obstacles.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.controller.Player.api.PlatformMovementObserver;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.impl.GameObjectImpl;
import it.unibo.model.Map.util.ObstacleType;

// metodi setter da mettere a posto ! 

public class MovingObstacles extends GameObjectImpl implements Obstacle{
    private final ObstacleType type;
    private boolean visible;
    private int updateCounter; // Per gestire movimento sub-cella
    private static final int BASE_MOVEMENT_THRESHOLD = 50; 
    private final List<PlatformMovementObserver> observers = new ArrayList<>();

    // Costanti per le dimensioni in celle
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
        addToAllCells();
    }

    private void addToAllCells() {
        getOccupiedCells2().forEach(c -> c.addObject(this));
    }

    private void removeFromAllCells() {
        getOccupiedCells2().forEach(c -> c.removeObject(this));
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
        int movementThreshold = Math.max(1,  BASE_MOVEMENT_THRESHOLD - Math.abs(getSpeed())); // Più veloce = movimento più frequente
        
        if (updateCounter >= movementThreshold) {
            removeFromAllCells();
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

            addToAllCells();
            notifyObservers(deltaX);
        }
    }
     
    public void reset() {
        this.updateCounter = 0;
    }

    public boolean collidesWith(int px, int py) {
        if (!visible || py != getY()) {
            return false;
        }
        
        // Controlla se il punto è nell'area occupata dall'ostacolo
        return px >= getX() && px < getX() + getWidthInCells();
    }
    
    /**
     * Ottiene la larghezza dell'ostacolo in celle.
     * 
     * @return Larghezza in celle
     */
    /*@Override
    public int getWidthInCells() {
        return switch (type.toString()) {
            case "TRAIN" -> TRAIN_WIDTH_CELLS;
            case "LOG" -> LOG_WIDTH_CELLS;
            default -> CAR_WIDTH_CELLS; // CAR e altri ostacoli di dimensione 1
        };
    }*/

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

    /**
     * Ottiene il livello di difficoltà dell'ostacolo in base al tipo e alla velocità.
     * Utilizzato per il punteggio o per regolare la difficoltà.
     * 
     * @return Valore difficoltà
     */
    public int getDifficultyLevel() {
        int baseLevel = switch (type.toString()) {
            case "TRAIN" -> 3;
            case "LOG" -> 2; // I tronchi hanno difficoltà media
            default -> 1; // CAR e altri
        };
        return baseLevel + Math.abs(getSpeed());
    }

    /**
     * Ottiene tutte le celle occupate dall'ostacolo.
     * 
     * @return Array delle posizioni X delle celle occupate
     */
    /*@Override
    public List<Integer> getOccupiedCells() {
        int width = getWidthInCells();
        List<Integer> cells = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            cells.add(getX() + i);
        }
        return cells;
    }

    @Override
    public boolean occupiesCell(int cellX) {
        int startX = getX();
        int endX = startX + getWidthInCells();
        return cellX >= startX && cellX < endX;
    }

    @Override
    public List<Cell> getOccupiedCells2() {
        List<Cell> list = new ArrayList<>();
        getOccupiedCells().forEach(x -> list.add(new CellImpl(x, getY())));
        return list;
    }*/

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

