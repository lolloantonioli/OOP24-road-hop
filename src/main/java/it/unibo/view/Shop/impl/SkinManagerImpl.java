package it.unibo.view.Shop.impl;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;

import it.unibo.model.Shop.api.Skin;
import it.unibo.model.Shop.impl.SkinImpl;
import it.unibo.view.Shop.api.SkinManager;

public class SkinManagerImpl implements SkinManager {
    private final List<Skin> availableSkins = new ArrayList<>();
    private final Map<String, Skin> skinMap = new HashMap<>();
    private Skin currentSkin;
    private final Preferences prefs;

    public SkinManagerImpl() {
        prefs = Preferences.userNodeForPackage(SkinManagerImpl.class);
        loadSkins();
        loadUnlockedStatus();
    }

    @Override
    public void loadSkins() {
        try{
            // Loads the default skin (always unlocked)
            BufferedImage defaultImage = ImageIO.read(new File("Immagine da mettere"));
            Skin defaultSkin = new SkinImpl("default", "Default", 0, defaultImage, true);
            availableSkins.add(defaultSkin);
            skinMap.put(defaultSkin.getId(), defaultSkin);
            currentSkin = defaultSkin;

            // Loads other skins
            BufferedImage frogImage = ImageIO.read(new File("Immagine da mettere"));
            Skin frogSkin = new SkinImpl("frog", "Frog", 100, frogImage, false);
            availableSkins.add(frogSkin);
            skinMap.put(frogSkin.getId(), frogSkin);

            // Add more skins here...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadUnlockedStatus() {
        for (Skin skin : availableSkins) {
            if (!skin.getId().equals("default")) { // The default skin is always unlocked
                boolean unlocked = prefs.getBoolean("skin_" + skin.getId(), false);
                skin.setUnlocked(unlocked);
            }
        }

        // Loads the current skin
        String currentSkinId = prefs.get("current_skin", "default");
        currentSkin = skinMap.getOrDefault(currentSkinId, skinMap.get("default"));
    }

    @Override
    public boolean buySkin(String skinId, int coins) {
        Skin skin = skinMap.get(skinId);
        if (skin == null || skin.isUnlocked()) {
            return false;
        }

        if (coins >= skin.getPrice()) {
            skin.setUnlocked(true);
            prefs.putBoolean("skin_" + skinId, true);
            return true;
        }

        return false;
    }

    @Override
    public void selectSkin(String skinId) {
       Skin skin = skinMap.get(skinId);
        if (skin != null && skin.isUnlocked()) {
            currentSkin = skin;
            prefs.put("current_skin", skinId);
        }
    }

    @Override
    public Skin getCurrentSkin() {
       return currentSkin;
    }
    @Override
    public List<Skin> getAvailableSkins() {
       return new ArrayList<>(availableSkins);
    }
}
