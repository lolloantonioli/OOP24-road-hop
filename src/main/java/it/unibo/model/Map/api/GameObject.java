package it.unibo.model.Map.api;

public interface GameObject {

    void update(int mapWidth);

    boolean collidesWith(int px, int py);

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    int getSpeed();

    boolean isMovable();

    void setMovable(boolean movable);

    void setSpeed(int speed);

    boolean isPlatform();

    void setPlatform(boolean platform);

}
