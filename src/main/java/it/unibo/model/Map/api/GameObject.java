package it.unibo.model.Map.api;

public interface GameObject {

    void update();

    boolean collidesWith(int px, int py);

    int getX();

    int getY();

    int getSpeed();

    boolean isMovable();

    void setMovable(boolean movable);

    void setSpeed(int speed);

    boolean isPlatform();

    void setPlatform(boolean platform);

}
