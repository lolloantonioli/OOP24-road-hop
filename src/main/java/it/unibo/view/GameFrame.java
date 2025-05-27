package it.unibo.view;

import it.unibo.view.Shop.api.ShopView;

public interface GameFrame {

    public void show(final String name);

    public MenuPanel getMenuPanel();

    public ShopView getShopPanel();

}
