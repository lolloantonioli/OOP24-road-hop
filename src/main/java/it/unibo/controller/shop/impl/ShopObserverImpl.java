package it.unibo.controller.shop.impl;

import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.controller.MainController;
import it.unibo.controller.shop.api.ShopObserver;
import it.unibo.view.ShopView;
import it.unibo.model.shop.api.ShopModel;
import it.unibo.model.shop.api.Skin;

/**
 * Suppresses FindBugs warning for exposing the ShopModel instance directly.
 * The ShopModel is not designed to be defensive copied, as it is a model component.
 */
@SuppressFBWarnings(
    value = "EI2",
    justification = "Necessary to keep a direct reference to the provided ShopModel to synchronize skin in real time."
)

/**
 * Implementation of the ShopObserver interface.
 * Observes shop-related events and updates the view and model accordingly.
 */
public class ShopObserverImpl implements ShopObserver {


    private final MainController mainController;
    private final ShopView shopPanel;
    private final ShopModel shopModel;

    /**
     * Constructor for ShopObserverImpl.
     * Initializes the observer with the main controller, shop panel, and shop model.
     * @param mainController
     * @param shopPanel
     * @param shopModel
     */
    public ShopObserverImpl(final MainController mainController, final ShopView shopPanel, final ShopModel shopModel) {
        this.mainController = mainController;
        this.shopPanel = shopPanel;
        this.shopModel = shopModel;
    }


    @Override
    public final void activate() {
        shopPanel.setOnBackToMainMenu(mainController::goToMenu);


        shopPanel.setOnSkinPurchase((skinId, price) -> {
            shopModel.purchaseSkin(skinId);
            updateView();
        });

        shopPanel.setOnSkinSelected(skinId -> {
            shopModel.selectSkin(skinId);
            updateView();
        });

        // Mostra subito le skin quando si entra nel negozio
        updateView();
    }


    private void updateView() {
        shopPanel.setCoins(shopModel.getCoins()); 
        final List<Skin> skins = shopModel.getAllSkins();
        shopPanel.setSkins(skins);
    }

}
