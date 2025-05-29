package it.unibo.model.Shop.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import it.unibo.model.Shop.api.Skin;

public class ShopDataManagerImpl {
    
    private static final String SAVE_FILE_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "ShopSave.properties";
    
    /**
     * Salva i dati dello shop usando Properties
     */
    public static void saveShopData(List<Skin> skins, String selectedSkinId, int coins) {
        Properties props = new Properties();
        
        try {
            // Salva coins e selected skin
            props.setProperty("coins", String.valueOf(coins));
            props.setProperty("selectedSkin", selectedSkinId);
            
            // Salva ogni skin
            for (Skin skin : skins) {
                props.setProperty("skin." + skin.getId() + ".unlocked", String.valueOf(skin.isUnlocked()));
                props.setProperty("skin." + skin.getId() + ".selected", String.valueOf(skin.isSelected()));
            }
            
            // Crea la directory se non esiste
            File file = new File(SAVE_FILE_PATH);
            file.getParentFile().mkdirs();
            
            // Salva il file
            try (FileOutputStream out = new FileOutputStream(file)) {
                props.store(out, "Shop Save Data");
            }
            
            System.out.println("Dati shop salvati con successo!");
            
        } catch (IOException e) {
            System.err.println("Errore nel salvare i dati dello shop: " + e.getMessage());
        }
    }
    
    /**
     * Carica i dati dello shop usando Properties
     */
    public static ShopSaveData loadShopData() {
        Properties props = new Properties();
        
        try {
            File file = new File(SAVE_FILE_PATH);
            if (!file.exists()) {
                System.out.println("File di salvataggio non trovato, uso dati di default");
                return getDefaultSaveData();
            }
            
            // Carica il file
            try (FileInputStream in = new FileInputStream(file)) {
                props.load(in);
            }
            
            ShopSaveData saveData = new ShopSaveData();
            
            // Carica coins
            saveData.coins = Integer.parseInt(props.getProperty("coins", "100"));
            
            // Carica selected skin
            saveData.selectedSkin = props.getProperty("selectedSkin", "Default");
            
            // Carica le skin
            String[] skinIds = {"Default", "red", "blue", "gold", "rainbow"};
            for (String id : skinIds) {
                SkinSaveData skinData = new SkinSaveData();
                skinData.id = id;
                skinData.unlocked = Boolean.parseBoolean(props.getProperty("skin." + id + ".unlocked", 
                                                        id.equals("Default") ? "true" : "false"));
                skinData.selected = Boolean.parseBoolean(props.getProperty("skin." + id + ".selected", "false"));
                saveData.skins.add(skinData);
            }
            
            System.out.println("Dati shop caricati con successo!");
            return saveData;
            
        } catch (Exception e) {
            System.err.println("Errore nel caricare i dati dello shop: " + e.getMessage());
            return getDefaultSaveData();
        }
    }
    
    /**
     * Restituisce i dati di default
     */
    private static ShopSaveData getDefaultSaveData() {
        ShopSaveData defaultData = new ShopSaveData();
        defaultData.coins = 1000;;
        defaultData.selectedSkin = "Default";
        
        // Skin di default
        String[] skinIds = {"Default", "red", "blue", "gold", "rainbow"};
        for (String id : skinIds) {
            SkinSaveData skinData = new SkinSaveData();
            skinData.id = id;
            skinData.unlocked = id.equals("Default");
            skinData.selected = id.equals("Default");
            defaultData.skins.add(skinData);
        }
        
        return defaultData;
    }
    
    /**
     * Classe per contenere i dati di salvataggio dello shop
     */
    public static class ShopSaveData {
        public int coins;
        public String selectedSkin;
        public List<SkinSaveData> skins = new ArrayList<>();
    }
    
    /**
     * Classe per contenere i dati di salvataggio di una singola skin
     */
    public static class SkinSaveData {
        public String id;
        public boolean unlocked;
        public boolean selected;
    }
}