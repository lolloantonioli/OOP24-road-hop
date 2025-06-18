package it.unibo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.shop.impl.ShopModelImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;

class PersistenceTest {
    private static final int COINS_TO_ADD = 123;
    private static final int COINS_TO_START = 100;
    private ShopModelImpl shopModel;

    @BeforeEach
    void setUp() {
        shopModel = new ShopModelImpl();
        shopModel.setCoinsForTest(COINS_TO_START);
    }

    @AfterEach
    void resetMaxScore() {
        shopModel.resetPersistenceForTest(COINS_TO_START, 0);
    }

    @Test
    void testMaxScoreIsUpdatedAndSaved() {
        final int oldMax = shopModel.getMaxScore();
        final int newScore = oldMax + 1;
        shopModel.updateMaxScore(newScore);
        assertEquals(newScore, shopModel.getMaxScore());
    }

    @Test
    void testMaxScoreNotUpdatedIfLower() {
        final int oldMax = shopModel.getMaxScore();
        shopModel.updateMaxScore(oldMax - 1);
        assertEquals(oldMax, shopModel.getMaxScore());
    }

    @Test
    void testAddCoinsPersistsValue() {
        final int before = shopModel.getCoins();
        shopModel.addCoins(COINS_TO_ADD);
        final ShopModelImpl reloaded = new ShopModelImpl();
        assertTrue(reloaded.getCoins() >= before + COINS_TO_ADD);
    }

}
