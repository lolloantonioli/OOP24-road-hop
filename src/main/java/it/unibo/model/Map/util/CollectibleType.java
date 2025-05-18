package it.unibo.model.Map.util;

public final class CollectibleType {
    
    public static final CollectibleType COIN = new CollectibleType("COIN");
    public static final CollectibleType INVINCIBILITY = new CollectibleType("INVINCIBILITY");

    private final String name;

    private CollectibleType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
