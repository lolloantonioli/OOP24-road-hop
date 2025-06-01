package it.unibo.model.Obstacles.impl;

import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.util.ObstacleType;

// metodi setter da mettere a posto ! + controller

public class MovingObstacles implements Obstacle{
    private int cellX; // Posizione X nella griglia (0-8 per chunk da 9 celle)
    private final int chunkY;
    private final ObstacleType type;
    private int speed;
    private boolean movable;
    private boolean visible;
    private int initialCellX; // per far ricomparire l'ostacolo nella posizione iniziale
    private final int initialSpeed;
    private int updateCounter; // Per gestire movimento sub-cella

    // Costanti per le dimensioni in celle
    public static final int CAR_WIDTH_CELLS = 1;
    public static final int TRAIN_WIDTH_CELLS = 4;
    public static final int LOG_WIDTH_CELLS = 3;
    public static final int CELLS_PER_CHUNK = 9;

    public MovingObstacles(int cellX, int chunkY, ObstacleType type, int speed) {
        this.cellX = cellX;
        this.initialCellX = cellX;
        this.chunkY = chunkY;
        this.type = type;
        this.speed = speed;
        this.initialSpeed = speed;
        this.movable = true;
        this.visible = true;
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

    public boolean collidesWith(int px, int py) {
        if (!visible || py != chunkY) {
            return false;
        }
        
        // Controlla se il punto è nell'area occupata dall'ostacolo
        return px >= cellX && px < cellX + getWidthInCells();
    }
    
    /**
     * Ottiene la larghezza dell'ostacolo in celle.
     * 
     * @return Larghezza in celle
     */
    @Override
    public int getWidthInCells() {
        return switch (type.toString()) {
            case "TRAIN" -> TRAIN_WIDTH_CELLS;
            case "LOG" -> LOG_WIDTH_CELLS;
            default -> CAR_WIDTH_CELLS; // CAR e altri ostacoli di dimensione 1
        };
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
        return type == ObstacleType.LOG;
    }

    @Override
    public void setPlatform(boolean platform) {
        // Solo i tronchi possono essere impostati come piattaforme
        if (platform && type != ObstacleType.LOG) {
            throw new UnsupportedOperationException("Solo gli ostacoli di tipo LOG possono essere impostati come piattaforme");
        }
        // Per i tronchi, isPlatform() ritorna sempre true, quindi non serve memorizzare lo stato
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
        return baseLevel + Math.abs(speed);
    }

    /**
     * Controlla se l'ostacolo può essere posizionato in una specifica posizione
     * senza sovrapporsi con altri ostacoli.
     * 
     * @param targetCellX Posizione X target
     * @param otherObstacles Lista di altri ostacoli da controllare
     * @return true se la posizione è valida
     */
    public boolean canBePlacedAt(int targetCellX, java.util.List<MovingObstacles> otherObstacles) {
        for (MovingObstacles other : otherObstacles) {
            if (other != this && other.getY() == this.chunkY) {
                // Controlla sovrapposizione
                if (targetCellX < other.getX() + other.getWidthInCells() && 
                    targetCellX + this.getWidthInCells() > other.getX()) {
                    return false;
                }
            }
        }
        return targetCellX >= 0 && targetCellX + getWidthInCells() <= CELLS_PER_CHUNK;
    }

    /**
     * Ottiene tutte le celle occupate dall'ostacolo.
     * 
     * @return Array delle posizioni X delle celle occupate
     */
    @Override
    public int[] getOccupiedCells() {
        int width = getWidthInCells();
        int[] cells = new int[width];
        for (int i = 0; i < width; i++) {
            cells[i] = cellX + i;
        }
        return cells;
    }

    @Override
    public void setX(int x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setX'");
    }

    @Override
    public void setY(int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setY'");
    }

    @Override
    public boolean occupiesCell(int cellX) {
        int startX = getX();
        int endX = startX + getWidthInCells();
        return cellX >= startX && cellX < endX;
    }

    /**
     * Controlla se il giocatore può stare in piedi su questo ostacolo.
     * Utilizzato per i tronchi che fungono da piattaforme galleggianti.
     * 
     * @param playerCellX Posizione X del giocatore
     * @return true se il giocatore può stare sul tronco
     */
    public boolean canPlayerStandOn(int playerCellX) {
        return isPlatform() && occupiesCell(playerCellX);
    }

}

