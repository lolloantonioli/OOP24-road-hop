package it.unibo.model.Shop.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import it.unibo.model.Shop.api.ShopModel;
import it.unibo.model.Shop.api.Skin;
import it.unibo.model.Shop.impl.ShopDataManagerImpl.ShopSaveData;
import it.unibo.model.Shop.impl.ShopDataManagerImpl.SkinSaveData;

public class ShopModelImpl implements ShopModel {

    private final List<Skin> skins;
    private Skin selectedSkin;
    private int coins;

    private static final int RED_SKIN_PRICE = 50;
    private static final int DEFAULT_SKIN_PRICE = 0;
    private static final int BLUE_SKIN_PRICE = 75;
    private static final int ORANGE_SKIN_PRICE = 100;
    private static final int CYAN_SKIN_PRICE = 140;
    private static final int WHITE_SKIN_PRICE = 160;

    public ShopModelImpl() {
        this.skins = new ArrayList<>();
        loadShopData();
    }

    private void loadShopData() {
        final ShopSaveData saveData = ShopDataManagerImpl.loadShopData();
        this.coins = saveData.coins;

        initializeSkins(saveData);

        selectedSkin(saveData.selectedSkin);
    }

    private final void selectedSkin(final String selectedSkinId) {
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
        createSkin("white", "White", WHITE_SKIN_PRICE, Color.WHITE, saveData); // Bianco
    }

    private void createSkin(String id, String name, int price, Color color, ShopSaveData saveData) {
        final SkinSaveData skinData = saveData.skins.stream()
                .filter(data -> data.id.equals(id))
                .findFirst()
                .orElse(null);
        
        boolean unlocked = (skinData != null) ? skinData.unlocked : (id.equals("Default"));
        boolean selected = (skinData != null) ? skinData.selected : false;

        Skin skin = new SkinImpl(id, name, price, unlocked, color);
        if (selected) {
            skin.select();
        } 
        skins.add(skin);
    }


    private void saveData() {
        String selectedSkinId = (selectedSkin != null) ? selectedSkin.getId() : "Default";
        ShopDataManagerImpl.saveShopData(skins, selectedSkinId, coins);
    }

    @Override
    public List<Skin> getAllSkins() {
        return new ArrayList<>(skins);  
    }

    @Override
    public Skin getSkinById(String id) {
       return skins.stream()
                .filter(skin -> skin.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean canPurchaseSkin(String id) {
       Skin skin = getSkinById(id);
        return skin != null && !skin.isUnlocked() && coins >= skin.getPrice();
    }

    @Override
    public void purchaseSkin(String id) {
        if (canPurchaseSkin(id)) {
            Skin skin = getSkinById(id);
            spendCoins(skin.getPrice());
            skin.unlock();
            saveData(); // Salva dopo l'acquisto
        } 
    }

    private void spendCoins(int price) {
        if ( price > 0 && coins >= price) {
            this.coins -= price;
        }
    }

    @Override
    public void selectSkin(String id) {
        Skin skin = getSkinById(id);
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
    public Skin getSelectedSkin() {
        return selectedSkin;
    }

    @Override
    public int getCoins() {
        return coins;
    }

    public void addCoins(int amount) {
        if (amount > 0) {
            this.coins += amount;
            saveData();
        }
    }
    
    public void saveDataToFile(){
        saveData();
    }
}
