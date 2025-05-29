package it.unibo.model.Map.api;

public interface GameObject {

    void update();

    int getX();

    int getY();

    void setX(int x);

    void setY(int y);

    int getSpeed();

    boolean isMovable();

    void setMovable(boolean movable);

    void setSpeed(int speed);

    boolean isPlatform();

    void setPlatform(boolean platform);

    int getWidthInCells();

    int[] getOccupiedCells();

    boolean occupiesCell(int cellX);

}
