package it.unibo.model.Shop.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.model.Shop.api.ShopModel;
import it.unibo.model.Shop.api.Skin;

public class ShopModelImpl implements ShopModel {

    private final List<Skin> skins;
    private Skin selectedSkin;
    private int coins;

    public ShopModelImpl() {
        this.skins = new ArrayList<>();
        this.coins = 100;
        initializeSkins();
    }

    private void initializeSkins() {
        Skin defaultSkin = new SkinImpl("Default", "Frog", 0, true);
        defaultSkin.select(); 
        skins.add(defaultSkin);
        selectedSkin = defaultSkin;

        skins.add(new SkinImpl("red", "Red Frog", 50, false));
        skins.add(new SkinImpl("blue", "Blue Frog", 75, false));
        skins.add(new SkinImpl("gold", "Golden Frog", 150, false));
        skins.add(new SkinImpl("rainbow", "Rainbow Frog", 200, false));
    
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
    
}
