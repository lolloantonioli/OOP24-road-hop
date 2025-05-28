package it.unibo.model.Obstacles.impl;

import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.util.ObstacleType;

public class MovingObstacles implements Obstacle{
    private int cellX; // Posizione X nella griglia (0-8 per chunk da 9 celle)
    private int chunkY = 0;
    private final ObstacleType type;
    private int speed;
    private boolean movable;
    private boolean visible;
    private int initialCellX; // per far ricomparire l'ostacolo nella posizione iniziale
    private final int initialSpeed;
    private int mapWidthInChunks;
    private int updateCounter; // Per gestire movimento sub-cella

    // Costanti per le dimensioni in celle
    public static final int CAR_WIDTH_CELLS = 1;
    public static final int TRAIN_WIDTH_CELLS = 4;
    public static final int CELLS_PER_CHUNK = 9;

    public MovingObstacles(int x, int y, ObstacleType type, int speed) {
        this.cellX = cellX;
        this.initialCellX = cellX;
        this.chunkY = chunkY;
        this.type = type;
        this.speed = speed;
        this.initialSpeed = speed;
        this.movable = true;
        this.visible = true;
        this.mapWidthInChunks = 0;
        this.updateCounter = 0;
    }
    
    @Override
    public int getX() {
        return cellX;
    }
    
    @Override
    public int getY() {
        return chunkY;
    }
    
    @Override
    public ObstacleType getType() {
        return type;
    }

    @Override
    public void update() {
        if (!movable) {
            return;
        }

        updateCounter++;
        
        // Movimento basato su velocità (ogni N update muove di una cella)
        int movementThreshold = Math.max(1, 4 - Math.abs(speed)); // Più veloce = movimento più frequente
        
        if (updateCounter >= movementThreshold) {
            updateCounter = 0;
            
            if (speed > 0) {
                cellX++;
                // Se esce dal lato destro, riappare a sinistra
                if (cellX >= CELLS_PER_CHUNK) {
                    cellX = -getWidthInCells();
                }
            } else if (speed < 0) {
                cellX--;
                // Se esce dal lato sinistro, riappare a destra
                if (cellX + getWidthInCells() <= 0) {
                    cellX = CELLS_PER_CHUNK;
                }
            }
        }
    }

    /**
     * Imposta la larghezza della mappa in chunks.
     * 
     * @param mapWidthInChunks Larghezza della mappa in chunks
     */
    public void setMapWidthInChunks(int mapWidthInChunks) {
        this.mapWidthInChunks = mapWidthInChunks;
    }

    /**
     * Reimposta gli ostacoli alla posizione iniziale.
     */
    public void reset() {
        this.cellX = initialCellX;
        this.speed = initialSpeed;
        this.updateCounter = 0;
    }

    /**
     * Imposta una nuova posizione iniziale.
     * 
     * @param newCellX Nuova posizione X iniziale in celle
     */
    public void setInitialCellX(int newCellX) {
        this.initialCellX = newCellX;
        this.cellX = newCellX;
    }

    /**
     * Controlla se questo ostacolo collide con un altro.
     * 
     * @param other Altro ostacolo
     * @return true se c'è collisione
     */
    public boolean collidesWith(Obstacle other) {
        if (!visible || other.getY() != chunkY) {
            return false;
        }
        
        // Verifica sovrapposizione nelle celle
        return cellX < other.getX() + getOtherWidthInCells(other) && 
               cellX + getWidthInCells() > other.getX();
    }
    
    /**
     * Ottiene la larghezza dell'ostacolo in celle.
     * 
     * @return Larghezza in celle
     */
    public int getWidthInCells() {
        return type == ObstacleType.TRAIN ? TRAIN_WIDTH_CELLS : CAR_WIDTH_CELLS;
    }
    
    /**
     * Ottiene la larghezza di un altro ostacolo in celle.
     * 
     * @param other Altro ostacolo
     * @return Larghezza in celle
     */
    private int getOtherWidthInCells(Obstacle other) {
        if (other instanceof MovingObstacles) {
            return ((MovingObstacles) other).getWidthInCells();
        }
        return 1; // Default per ostacoli statici
    }

    @Override
    public boolean isMovable() {
        return movable;
    }

    @Override
    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Aumenta la velocità dell'ostacolo mantenendo la direzione.
     * 
     * @param amount Quantità da aggiungere
     */
    public void increaseSpeed(int amount) {
        if (this.speed > 0) {
            setSpeed(this.speed + amount);
        } else if (this.speed < 0) {
            setSpeed(this.speed - amount);
        }
    }

    @Override
    public boolean isPlatform() {
        return false;
    }

    @Override
    public void setPlatform(boolean platform) {
        // non applicabile per CAR e TRAIN
        if (platform) {
            throw new UnsupportedOperationException("Gli ostacoli di tipo " + this.type + " non possono essere impostati come piattaforme");
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
        int baseLevel = type == ObstacleType.TRAIN ? 3 : 1;
        return baseLevel + Math.abs(speed);
    }

}

