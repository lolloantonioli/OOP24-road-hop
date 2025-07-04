package it.unibo.model.shop.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.model.shop.api.ShopModel;
import it.unibo.model.shop.api.Skin;
import it.unibo.model.shop.impl.ShopDataManagerImpl.ShopSaveData;
import it.unibo.model.shop.impl.ShopDataManagerImpl.SkinSaveData;

/**
 * Implementation of the ShopModel interface.
 * This class manages the shop's skins, coins, and selected skin.
 * It provides methods to load shop data, purchase skins, select skins, and manage coins.
 */
public class ShopModelImpl implements ShopModel {

    private static final int DEFAULT_SKIN_PRICE = 0;
    private static final int RED_SKIN_PRICE = 50;
    private static final int BLUE_SKIN_PRICE = 75;
    private static final int ORANGE_SKIN_PRICE = 100;
    private static final int GRAY_SKIN_PRICE = 140;
    private static final int WHITE_SKIN_PRICE = 160;
    private static final String DEFAULT_SKIN_ID = "Default";

    private final List<Skin> skins;
    private Skin selectedSkin;
    private int coins;
    private int maxScore;


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
        this.maxScore = saveData.getMaxScore();

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
        createSkin(DEFAULT_SKIN_ID, DEFAULT_SKIN_ID, DEFAULT_SKIN_PRICE, Color.GREEN, saveData);
        createSkin("red", "Red", RED_SKIN_PRICE, Color.RED, saveData); // Rosso
        createSkin("blue", "Blue", BLUE_SKIN_PRICE, Color.BLUE, saveData); // Blu
        createSkin("orange", "Orange", ORANGE_SKIN_PRICE, Color.ORANGE, saveData); // Arancione
        createSkin("gray", "Gray", GRAY_SKIN_PRICE, Color.LIGHT_GRAY, saveData); // Azzurro
        createSkin("white", "White", WHITE_SKIN_PRICE, Color.WHITE, saveData); // Bianco        ./gradlew check
    }

    private void createSkin(final String id, final String name, final int price, final Color color, final ShopSaveData saveData) {
        final SkinSaveData skinData = saveData.getSkins().stream()
                .filter(data -> data.getId().equals(id))
                .findFirst()
                .orElse(null);

        final boolean unlocked = (skinData != null) ? skinData.isUnlocked() : DEFAULT_SKIN_ID.equals(id);
        final boolean selected = skinData != null && skinData.isSelected();


        final Skin skin = new SkinImpl(id, name, price, unlocked, color);
        if (selected) {
            skin.select();
        } 
        skins.add(skin);
    }


    private void saveData() {
        final String selectedSkinId = (selectedSkin != null) ? selectedSkin.getId() : DEFAULT_SKIN_ID;
        ShopDataManagerImpl.saveShopData(skins, selectedSkinId, coins, maxScore);
    }

    @Override
    public final List<Skin> getAllSkins() {
        return Collections.unmodifiableList(skins);
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
            for (final Skin s : skins) {
                if (s.isSelected()) {
                    s.deselect(); // Deseleziona la skin attualmente selezionata
                }
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
    @Override
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

    /**
     * Returns the max score saved.
     * @return the max score
     */
    @Override
    public int getMaxScore() {
        return maxScore;
    }
    /**
     * Updates the max score if the new score is higher and saves the data.
     * @param score the new score to check
     */
    @Override
    public void updateMaxScore(final int score) {
        if (score > maxScore) {
            maxScore = score;
            saveData();
        }
    }

    /**
     * ONLY FOR TESTS: Sets the number of coins directly (does not save to file).
     * @param coins the number of coins to set
     */
    public void setCoinsForTest(final int coins) {
        this.coins = coins;
    }
    /**
    * ONLY FOR TESTS: Resetta coins e maxScore e salva su file, ignorando la logica di updateMaxScore.
    * Da usare solo nei test per ripulire la persistenza.
    * @param coins the number of coins to set
    * @param maxScore the max Score to set
    */
    public void resetPersistenceForTest(final int coins, final int maxScore) {
        this.coins = coins;
        this.maxScore = maxScore;
        saveData();
}
}
