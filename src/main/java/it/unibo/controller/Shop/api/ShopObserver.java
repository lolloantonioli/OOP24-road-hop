package it.unibo.controller.Shop.api;

/**
 * ShopObserver interface that defines the methods for observing changes in the shop.
 * Observers can be activated to start receiving updates about the shop's state.
 */
public interface ShopObserver {
    /**
     * Activates the observer to start receiving updates.
     */
    void activate();
}
