package it.unibo.controller;

import it.unibo.model.Shop.api.ShopModel;

public interface MainController {

    public void goToMenu();

    public void goToGame();

    public void goToInstructions();
    
    public void goToShop();

    public ShopModel getShopModel();

}
