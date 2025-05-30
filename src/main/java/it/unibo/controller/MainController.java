package it.unibo.controller;

import it.unibo.controller.Map.api.MapController;
import it.unibo.model.Shop.api.ShopModel;

public interface MainController {

    void goToMenu();

    void goToGame();

    void goToInstructions();
    
    void goToShop();

    ShopModel getShopModel();

    MapController getMapController();

}
