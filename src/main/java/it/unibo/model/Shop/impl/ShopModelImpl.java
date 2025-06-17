package it.unibo.model.Shop.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import it.unibo.model.Shop.api.ShopModel;
import it.unibo.model.Shop.api.Skin;
import it.unibo.model.Shop.impl.ShopDataManagerImpl.ShopSaveData;
import it.unibo.model.Shop.impl.ShopDataManagerImpl.SkinSaveData;

/**
 * Implementation of the ShopModel interface.
 * This class manages the shop's skins, coins, and selected skin.
 * It provides methods to load shop data, purchase skins, select skins, and manage coins.
 */
public class ShopModelImpl implements ShopModel {

    private final List<Skin> skins;
    private Skin selectedSkin;
    private int coins;

    private final static int RED_SKIN_PRICE = 50;
    private final static int DEFAULT_SKIN_PRICE = 0;
    private final static int BLUE_SKIN_PRICE = 75;
    private final static int ORANGE_SKIN_PRICE = 100;
    private final static int CYAN_SKIN_PRICE = 140;
    private final static int WHITE_SKIN_PRICE = 160;

    /**
     * Constructs a ShopModelImpl instance.
     * Initializes the skins list and loads the shop data from the save file.
     */
    public ShopModelImpl() {
        this.skins = new ArrayList<>();
        loadShopData();
    }

    private void loadShopData() {
        final ShopSaveData saveData = ShopDataManagerImpl.loadShopData();
        this.coins = saveData.getCoins();

        initializeSkins(saveData);

        selectedSkin(saveData.getSelectedSkin());
    }

    private void selectedSkin(final String selectedSkinId) {
        selectedSkin = skins.stream()
                .filter(skin -> skin.getId().equals(selectedSkinId))
                .findFirst()
                .orElse(skins.get(0));
    }

    private void initializeSkins(final ShopSaveData saveData) {
        createSkin("Default", "Default", DEFAULT_SKIN_PRICE, Color.GREEN, saveData);
        createSkin("red", "Red", RED_SKIN_PRICE, Color.RED, saveData); // Rosso
        createSkin("blue", "Blue", BLUE_SKIN_PRICE, Color.BLUE, saveData); // Blu
        createSkin("orange", "Orange", ORANGE_SKIN_PRICE, Color.ORANGE, saveData); // Arancione
        createSkin("cyan", "Cyan", CYAN_SKIN_PRICE, Color.CYAN, saveData); // Azzurro
        createSkin("white", "White", WHITE_SKIN_PRICE, Color.WHITE, saveData); // Bianco        ./gradlew check
    }

    private void createSkin(final String id, final String name, final int price, final Color color, final ShopSaveData saveData) {
        final SkinSaveData skinData = saveData.getSkins().stream()
                .filter(data -> data.getId().equals(id))
                .findFirst()
                .orElse(null);

        final boolean unlocked = (skinData != null) ? skinData.isUnlocked() : ("Default".equals(id));
        final boolean selected = (skinData != null) ? skinData.isSelected() : false;

        final Skin skin = new SkinImpl(id, name, price, unlocked, color);
        if (selected) {
            skin.select();
        } 
        skins.add(skin);
    }


    private void saveData() {
        final String selectedSkinId = (selectedSkin != null) ? selectedSkin.getId() : "Default";
        ShopDataManagerImpl.saveShopData(skins, selectedSkinId, coins);
    }

    @Override
    public final List<Skin> getAllSkins() {
        return new ArrayList<>(skins);
    }

    @Override
    public final Skin getSkinById(final String id) {
       return skins.stream()
                .filter(skin -> skin.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public final boolean canPurchaseSkin(final String id) {
       final Skin skin = getSkinById(id);
        return skin != null && !skin.isUnlocked() && coins >= skin.getPrice();
    }

    @Override
    public final void purchaseSkin(final String id) {
        if (canPurchaseSkin(id)) {
            final Skin skin = getSkinById(id);
            spendCoins(skin.getPrice());
            skin.unlock();
            saveData(); // Salva dopo l'acquisto
        } 
    }

    private void spendCoins(final int price) {
        if (price > 0 && coins >= price) {
            this.coins -= price;
        }
    }

    @Override
    public final void selectSkin(final String id) {
        final Skin skin = getSkinById(id);
        if (skin != null && skin.isUnlocked()) {
            if (selectedSkin != null) {
                selectedSkin.deselect();
            }

            skin.select();
            selectedSkin = skin;
            saveData(); // Salva dopo la selezione
        }
    }

    @Override
    public final Skin getSelectedSkin() {
        return selectedSkin;
    }

    @Override
    public final int getCoins() {
        return coins;
    }


    /**
     * Adds coins to the shop's coin balance.
     * @param amount the amount of coins to add
     */
    public final void addCoins(final int amount) {
        if (amount > 0) {
            this.coins += amount;
            saveData();
        }
    }

    /**
     * Saves the current shop data to the file.
     * This method is called to persist the current state of the shop,
     */
    public final void saveDataToFile() {
        saveData();
    }
}
