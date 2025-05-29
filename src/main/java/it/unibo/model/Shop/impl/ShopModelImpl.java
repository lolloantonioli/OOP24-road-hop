package it.unibo.model.Shop.impl;

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

    public ShopModelImpl() {
        this.skins = new ArrayList<>();
        loadShopData();
    }

    private void loadShopData() {
        ShopSaveData saveData = ShopDataManagerImpl.loadShopData();
        this.coins = saveData.coins;
        
        initializeSkins(saveData);

        selectedSkin(saveData.selectedSkin);
    }

    private void selectedSkin(String selectedSkinId) {
        selectedSkin = skins.stream()
                .filter(skin -> skin.getId().equals(selectedSkinId))
                .findFirst()
                .orElse(skins.get(0));
    }

    private void initializeSkins(ShopSaveData saveData) {
       createSkin("Default", "Frog", 0 ,saveData);
        createSkin("red", "Red Frog", 50, saveData);
        createSkin("blue", "Blue Frog", 75, saveData);
        createSkin("gold", "Golden Frog", 150, saveData);
        createSkin("rainbow", "Rainbow Frog", 200, saveData);
    }

    private void createSkin(String id, String name, int price, ShopSaveData saveData) {
        SkinSaveData skinData = saveData.skins.stream()
                .filter(data -> data.id.equals(id))
                .findFirst()
                .orElse(null);
        
        boolean unlocked = (skinData != null) ? skinData.unlocked : (id.equals("Default"));
        boolean selected = (skinData != null) ? skinData.selected : false;

        Skin skin = new SkinImpl(id, name, price, unlocked);
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
        if(canPurchaseSkin(id)) {
            Skin skin = getSkinById(id);
            spendCoins(skin.getPrice());
            skin.unlock();
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
