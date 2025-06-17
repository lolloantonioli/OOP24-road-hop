package it.unibo.model.shop.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.model.shop.api.Skin;

/**
 * Data Management for the Shop.
 * This class handles saving and loading shop data using Properties.
 * It provides methods to save the current state of the shop and load it from a file.
 */
public final class ShopDataManagerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopDataManagerImpl.class);
    private static final String SAVE_FILE_PATH = "src" + File.separator + "main" + File.separator + "resources"
                                                + File.separator + "ShopSave.properties";
    private static final String SKIN_PREFIX = "skin.";
    private static final String DEFAULT_SKIN_ID = "Default";

    private ShopDataManagerImpl() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Saves shop data to a file using Properties.
     * @param skins list of the available skins.
     * @param selectedSkinId the selected skin's ID.
     * @param coins the number of coins available.
     * @param maxScore the maximum score achieved.
     */
    static void saveShopData(final List<Skin> skins, final String selectedSkinId, final int coins, final int maxScore) {
        final Properties props = new Properties();
        props.setProperty("coins", String.valueOf(coins));
        props.setProperty("selectedSkin", selectedSkinId);
        props.setProperty("maxScore", String.valueOf(maxScore));

        for (final Skin skin : skins) {
            props.setProperty(SKIN_PREFIX + skin.getId() + ".unlocked", String.valueOf(skin.isUnlocked()));
            props.setProperty(SKIN_PREFIX + skin.getId() + ".selected", String.valueOf(skin.isSelected()));
        }

        // Ensure save directory exists
        final File file = new File(SAVE_FILE_PATH);
        final File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            LOGGER.error("Could not create directory: {}", parent);
            return;
        }

        // Write properties to file
        try (FileOutputStream out = new FileOutputStream(file)) {
            props.store(out, "Shop Save Data");
            LOGGER.info("Dati shop salvati con successo");
        } catch (final IOException e) {
            LOGGER.error("Errore nel salvare i dati dello shop", e);
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
                LOGGER.info("File di salvataggio non trovato, uso dati di default");
                return getDefaultSaveData();
            }

            try (FileInputStream in = new FileInputStream(file)) {
                props.load(in);
            }

            final ShopSaveData saveData = new ShopSaveData();
            saveData.setCoins(Integer.parseInt(props.getProperty("coins", "0")));
            saveData.setSelectedSkin(props.getProperty("selectedSkin", DEFAULT_SKIN_ID));
            saveData.setMaxScore(Integer.parseInt(props.getProperty("maxScore", "0")));

            final String[] skinIds = {DEFAULT_SKIN_ID, "red", "blue", "gold", "rainbow"};
            for (final String id : skinIds) {
                final SkinSaveData skinData = new SkinSaveData();
                skinData.id = id;
                skinData.unlocked = Boolean.parseBoolean(props.getProperty(SKIN_PREFIX + id + ".unlocked",
                                DEFAULT_SKIN_ID.equals(id) ? "true" : "false"));
                skinData.selected = Boolean.parseBoolean(props.getProperty(SKIN_PREFIX + id + ".selected", "false"));
                saveData.skins.add(skinData);
            }

            LOGGER.info("Dati shop caricati con successo");
            return saveData;

        } catch (final IOException ioe) {
            LOGGER.error("Errore nel caricare i dati dello shop, uso dati di default", ioe);
            return getDefaultSaveData();
        } catch (final NumberFormatException nfe) {
            LOGGER.error("Errore nel formato dei dati dello shop, uso dati di default", nfe);
            return getDefaultSaveData();
        }
    }

    private static ShopSaveData getDefaultSaveData() {
        final ShopSaveData defaultData = new ShopSaveData();
        defaultData.coins = 1000;
        defaultData.selectedSkin = DEFAULT_SKIN_ID;

        final String[] skinIds = {DEFAULT_SKIN_ID, "red", "blue", "gold", "rainbow"};
        for (final String id : skinIds) {
            final SkinSaveData skinData = new SkinSaveData();
            skinData.id = id;
            skinData.unlocked = DEFAULT_SKIN_ID.equals(id);
            skinData.selected = DEFAULT_SKIN_ID.equals(id);
            defaultData.skins.add(skinData);
        }

        return defaultData;
    }

    public static class ShopSaveData {
        private int coins;
        private String selectedSkin;
        private int maxScore;
        private final List<SkinSaveData> skins = new ArrayList<>();

        public final int getCoins() { return coins; }
        public final void setCoins(final int coins) { this.coins = coins; }
        public final String getSelectedSkin() { return selectedSkin; }
        public final void setSelectedSkin(final String selectedSkin) { this.selectedSkin = selectedSkin; }
        public final int getMaxScore() { return maxScore; }
        public final void setMaxScore(final int maxScore) { this.maxScore = maxScore; }
        public final List<SkinSaveData> getSkins() { return Collections.unmodifiableList(skins); }
    }

    public static class SkinSaveData {
        private String id;
        private boolean unlocked;
        private boolean selected;

        public final String getId() { return id; }
        public final boolean isUnlocked() { return unlocked; }
        public final boolean isSelected() { return selected; }
    }
}
