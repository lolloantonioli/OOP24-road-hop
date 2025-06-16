package it.unibo.model.Shop.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import it.unibo.model.Shop.api.Skin;

/**
 * Data Management for the Shop.
 * This class handles saving and loading shop data using Properties.
 * It provides methods to save the current state of the shop and load it from a file.
 */
public class ShopDataManagerImpl {

    private static final String SAVE_FILE_PATH = "src" + File.separator + "main" + File.separator + "resources"
                                                + File.separator + "ShopSave.properties";

    /**
     * Saves shop data to a file using Properties.
     * @param skins list of the available skins.
     * @param selectedSkinId the selected skin's ID.
     * @param coins the number of coins available.
     */
    static void saveShopData(final List<Skin> skins, final String selectedSkinId, final int coins) {
        final Properties props = new Properties();

        try {
            // Salva coins e selected skin
            props.setProperty("coins", String.valueOf(coins));
            props.setProperty("selectedSkin", selectedSkinId);

            // Salva ogni skin
            for (final Skin skin : skins) {
                props.setProperty("skin." + skin.getId() + ".unlocked", String.valueOf(skin.isUnlocked()));
                props.setProperty("skin." + skin.getId() + ".selected", String.valueOf(skin.isSelected()));
            }

            // Crea la directory se non esiste
            final File file = new File(SAVE_FILE_PATH);
            file.getParentFile().mkdirs();

            // Salva il file con try-with-resources
            try (FileOutputStream out = new FileOutputStream(file)) {
                props.store(out, "Shop Save Data");
            }

            System.out.println("Dati shop salvati con successo");

        } catch (final IOException e) {
            System.err.println("Errore nel salvare i dati dello shop: " + e.getMessage());
        }
    }

    /**
     * Loads shop data from a file.
     * if the file does not exist, it returns default data.
     * @return ShopSaveData containing the loaded data or default values if loading fails.
     */
    public static ShopSaveData loadShopData() {
        final Properties props = new Properties();

        try {
            final File file = new File(SAVE_FILE_PATH);
            if (!file.exists()) {
                System.out.println("File di salvataggio non trovato, uso dati di default");
                return getDefaultSaveData();
            }

            // Carica il file
            try (FileInputStream in = new FileInputStream(file)) {
                props.load(in);
            }

            final ShopSaveData saveData = new ShopSaveData();

            // Carica coins
            saveData.coins = Integer.parseInt(props.getProperty("coins", "100"));

            // Carica selected skin
            saveData.selectedSkin = props.getProperty("selectedSkin", "Default");

            // Carica le skin
            final String[] skinIds = {"Default", "red", "blue", "gold", "rainbow"};
            for (final String id : skinIds) {
                final SkinSaveData skinData = new SkinSaveData();
                skinData.id = id;
                skinData.unlocked = Boolean.parseBoolean(props.getProperty("skin." + id + ".unlocked",
                                                        id.equals("Default") ? "true" : "false"));
                skinData.selected = Boolean.parseBoolean(props.getProperty("skin." + id + ".selected", "false"));
                saveData.skins.add(skinData);
            }

            System.out.println("Dati shop caricati con successo!");
            return saveData;

        } catch (final Exception e) {
            System.err.println("Errore nel caricare i dati dello shop: " + e.getMessage());
            return getDefaultSaveData();
        }
    }

    /**
     * Returns default shop save data.
     * This method creates a default ShopSaveData object with initial values.
     * It includes a default amount of coins and a set of predefined skins.
     * @return ShopSaveData with default values.
     */
    private static ShopSaveData getDefaultSaveData() {
        final ShopSaveData defaultData = new ShopSaveData();
        defaultData.coins = 1000;
        defaultData.selectedSkin = "Default";

        // Skin di default
        final String[] skinIds = {"Default", "red", "blue", "gold", "rainbow"};
        for (final String id : skinIds) {
            final SkinSaveData skinData = new SkinSaveData();
            skinData.id = id;
            skinData.unlocked = id.equals("Default");
            skinData.selected = id.equals("Default");
            defaultData.skins.add(skinData);
        }

        return defaultData;
    }

    /**
     * Class to hold the shop save data.
     */
    public static class ShopSaveData {
        public int coins;
        public String selectedSkin;
        private List<SkinSaveData> skins = new ArrayList<>();
    }

    /**
     * Class to hold individual skin save data.
     */
    public static class SkinSaveData {
        private String id;
        private boolean unlocked;
        private boolean selected;
    }
}
